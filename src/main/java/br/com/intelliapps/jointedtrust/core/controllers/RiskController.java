package br.com.intelliapps.jointedtrust.core.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.intelliapps.jointedtrust.authentication.services.LoggedUserService;
import br.com.intelliapps.jointedtrust.core.models.Risk;
import br.com.intelliapps.jointedtrust.core.services.RiskService;
import br.com.intelliapps.jointedtrust.core.validators.RiskValidator;
import br.com.intelliapps.jointedtrust.main.components.DocFile;
import br.com.intelliapps.jointedtrust.main.components.FileSaverComponent;
import br.com.intelliapps.jointedtrust.main.utils.MediaTypeUtils;

@Controller
@RequestMapping("/risk")
public class RiskController {

	@Autowired
	private RiskService riskService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private Locale locale;

	@Autowired
	private FileSaverComponent fileSaver;

	@Autowired
	private LoggedUserService loggedUserService;

	@Autowired
	private ServletContext servletContext;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new RiskValidator());
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String showRisk(@RequestParam("guid") String guid, Model model) {

		Risk risk = riskService.findByGuid(guid);
		model.addAttribute("risk", risk);

		return "risk";
	}

	@RequestMapping("/list")
	public String riskList(Model model) {
		List<Risk> risks = riskService.findTopRisks(PageRequest.of(0, 9));
		model.addAttribute("risks", risks);
		return "risks";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String riskForm(Risk risk, Model model) {
		return "newrisk";
	}

	@RequestMapping(value = "/rest/create", method = RequestMethod.POST)
	public ResponseEntity<?> createRiskByRest(@Valid Risk risk, BindingResult binding, RedirectAttributes rAttr, @RequestParam MultipartFile[] file, Model model) {
		System.out.println(file.length);

		MultiValueMap<String, Object> responseMap = new LinkedMultiValueMap<>();

		if (binding.hasErrors()) {
			binding.getFieldErrors().stream().forEach(e -> {
				String errorMessage = messageSource.getMessage(e.getCode(), new String[] {}, locale);
				responseMap.add(e.getField(), errorMessage);
			});
			return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
			// return this.riskForm(risk);
		}

		if (riskService.existsByRiskId(risk.getRisk_id())) {
			String errorMessage = messageSource.getMessage("valid.risk.error.riskid.exists", new String[] {}, locale);
			responseMap.add("risk_id", errorMessage);
			return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
			// return this.riskForm(risk);
		}

		risk.setGuid(this.getGuid());

		// Adding Files related to Risk
		List<DocFile> fileList = new ArrayList<DocFile>();
		Map<String, String> filesMap = fileSaver.write("risk-docs", file, risk.getName());
		filesMap.entrySet().forEach(entry -> {
			DocFile newFile = new DocFile(entry.getKey(), entry.getValue());
			int offset = entry.getKey().length() - 3;
			int total = entry.getKey().length();
			String fileType = entry.getKey().substring(offset, total);
			newFile.setFileType(fileType.toUpperCase());
			newFile.setUploadedBy(loggedUserService.loggedUser().getName().toUpperCase());
			fileList.add(newFile);
		});
		risk.setFiles(fileList);

		riskService.save(risk);
		String successMessage = messageSource.getMessage("notification.risk.create.success", new String[] { risk.getName() }, locale);
		String successMessageTitle = messageSource.getMessage("notification.risk.create.success.title", new String[] {}, locale);
		// rAttr.addFlashAttribute("successMessage", successMessage);

		responseMap.add("successTitle", successMessageTitle);
		responseMap.add("successMessage", (String) successMessage);
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
		// return "redirect:/risk";
		// return this.riskList(model);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createRisk(@Valid Risk risk, BindingResult binding, RedirectAttributes rAttr, @RequestParam MultipartFile[] file, Model model) {
		System.out.println(file.length);

		if (binding.hasErrors()) {
			return this.riskForm(risk, model);
		}

		if (riskService.existsByRiskId(risk.getRisk_id())) {
			binding.rejectValue("risk_id", "valid.risk.error.riskid.exists");
			return this.riskForm(risk, model);
		}

		risk.setGuid(this.getGuid());
		// riskService.save(risk);
		String successMessage = messageSource.getMessage("notification.risk.create.success", new String[] { risk.getName() }, locale);
		String successMessageTitle = messageSource.getMessage("notification.risk.create.success.title", new String[] {}, locale);
		rAttr.addFlashAttribute("successMessage", successMessage);
		rAttr.addFlashAttribute("successMessageTitle", successMessageTitle);

		return "redirect:/risk/list";
	}

	private String getGuid() {
		return UUID.randomUUID().toString();
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadFile(@RequestParam("filePath") String filePath) {

		try {
			Path path = Paths.get(filePath);
			MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(servletContext, path.getFileName().toString());
			File file = new File(filePath);
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString()).contentType(mediaType).body(resource);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@RequestMapping(value = "/batch", method = RequestMethod.GET)
	public String batchLoad() {
		return "riskbatch";
	}

	@RequestMapping(value = "/rest/batch", method = RequestMethod.POST)
	public ResponseEntity<?> batchByRest(@RequestParam("file") MultipartFile file) {

		MultiValueMap<String, Object> responseMap = new LinkedMultiValueMap<>();
		try {
			List<Risk> riskList = extractRisks(file);
			riskList.forEach(risk -> {
				risk.setGuid(getGuid());
				riskService.save(risk);
			});

			String successMessage = messageSource.getMessage("notification.risk.batch.create.success", new String[] {}, locale);
			String successMessageTitle = messageSource.getMessage("notification.risk.create.success.title", new String[] {}, locale);
			responseMap.add("successTitle", successMessageTitle);
			responseMap.add("successMessage", (String) successMessage);
			return new ResponseEntity<>(responseMap, HttpStatus.OK);
		} catch (Exception ex) {
			String errorMessage = messageSource.getMessage("notification.risk.batch.create.error", new String[] {}, locale);
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private List<Risk> extractRisks(MultipartFile file) throws Exception {
		List<Risk> riskList = new ArrayList<Risk>();
		BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
		String line;
		int count = 0;
		while ((line = br.readLine()) != null) {
			String[] riskData = line.split(";");
			if (count == 0) {
				count += 1;
				continue; // Escaping header line
			}
			Risk risk = new Risk();
			risk.setRisk_id(riskData[0]);
			risk.setName(riskData[1]);
			risk.setDescription(riskData[2]);
			risk.setCause(riskData[3]);
			risk.setConsequence(riskData[4]);
			riskList.add(risk);
		}
		return riskList;
	}

	@RequestMapping(value = "/rest/uploadfiles", method = RequestMethod.POST)
	public ResponseEntity<?> loadFiles(@RequestParam("file") MultipartFile[] file, @RequestParam("guid") String guid) {

		System.out.println("LOAD MULTIPLE FILES IN RISK");
		
		MultiValueMap<String, Object> responseMap = new LinkedMultiValueMap<>();

		Risk risk = riskService.findByGuid(guid);
		
		// Adding Files related to Risk
		List<DocFile> fileList = risk.getFiles();
		Map<String, String> filesMap = fileSaver.write("risk-docs", file, risk.getName());
		filesMap.entrySet().forEach(entry -> {
			DocFile newFile = new DocFile(entry.getKey(), entry.getValue());
			int offset = entry.getKey().length() - 3;
			int total = entry.getKey().length();
			String fileType = entry.getKey().substring(offset, total);
			newFile.setFileType(fileType.toUpperCase());
			newFile.setUploadedBy(loggedUserService.loggedUser().getName().toUpperCase());
			fileList.add(newFile);
		});
		risk.setFiles(fileList);
		
		riskService.save(risk);
		String successMessage = messageSource.getMessage("notification.risk.upload.success", new String[] { risk.getName() }, locale);
		String successMessageTitle = messageSource.getMessage("notification.risk.create.success.title", new String[] {}, locale);
		responseMap.add("successTitle", successMessageTitle);
		responseMap.add("successMessage", (String) successMessage);
		return new ResponseEntity<>(responseMap, HttpStatus.OK);

	}

}
