package br.com.intelliapps.jointedtrust.core.controllers;

import java.io.File;
import java.io.FileInputStream;
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
	
	@RequestMapping(value="/show", method=RequestMethod.GET)
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
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String riskForm(Risk risk, Model model) {
		return "newrisk";
	}
	
	@RequestMapping(value="/rest/create", method=RequestMethod.POST)
	public ResponseEntity<?> createRiskByRest(@Valid Risk risk, BindingResult binding, RedirectAttributes rAttr, @RequestParam MultipartFile[] file, Model model) {
		System.out.println(file.length);
		
		MultiValueMap<String, Object> responseMap = new LinkedMultiValueMap<>();
		
		if(binding.hasErrors()) {
			binding.getFieldErrors()
				.stream()
				.forEach(
					e -> {
						String errorMessage = messageSource.getMessage(e.getCode(), new String[] {}, locale);
						responseMap.add(e.getField(), errorMessage);
					}
				);
			return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
			//return this.riskForm(risk);
		}
		
		if(riskService.existsByRiskId(risk.getRisk_id())) {
			String errorMessage = messageSource.getMessage("valid.risk.error.riskid.exists", new String[] {}, locale);
			responseMap.add("risk_id", errorMessage);
			return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
			//return this.riskForm(risk);
		}
		
		risk.setGuid(this.getGuid());
		
		//Adding Files related to Risk
		List<DocFile> fileList = new ArrayList<DocFile>();
		Map<String, String> filesMap = fileSaver.write("risk-docs", file, risk.getName());
		filesMap.entrySet()
			.forEach( entry -> {
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
		//rAttr.addFlashAttribute("successMessage", successMessage);
		
		responseMap.add("successTitle", successMessageTitle);
		responseMap.add("successMessage", (String) successMessage);
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
		//return "redirect:/risk";
		//return this.riskList(model);
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String createRisk(@Valid Risk risk, BindingResult binding, RedirectAttributes rAttr, @RequestParam MultipartFile[] file, Model model) {
		System.out.println(file.length);
		
		if(binding.hasErrors()) {
			return this.riskForm(risk, model);
		}
		
		if(riskService.existsByRiskId(risk.getRisk_id())) {
			binding.rejectValue("risk_id", "valid.risk.error.riskid.exists");
			return this.riskForm(risk, model);
		}
		
		risk.setGuid(this.getGuid());
		//riskService.save(risk);
		String successMessage = messageSource.getMessage("notification.risk.create.success", new String[] { risk.getName() }, locale);
		String successMessageTitle = messageSource.getMessage("notification.risk.create.success.title", new String[] {}, locale);
		rAttr.addFlashAttribute("successMessage", successMessage);
		rAttr.addFlashAttribute("successMessageTitle", successMessageTitle);
		
		return "redirect:/risk/list";
	}
	
	private String getGuid() {
		return UUID.randomUUID().toString();
	}
	
	@RequestMapping(value="/download", method=RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadFile(@RequestParam("filePath") String filePath){
		
		try {
			Path path = Paths.get(filePath);
			MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(servletContext, path.getFileName().toString());
			File file = new File(filePath);
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
					.contentType(mediaType)
					.body(resource);
			
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@RequestMapping(value="/batch", method=RequestMethod.GET)
	public String batchLoad() {
		return "riskbatch";
	}
	
}
