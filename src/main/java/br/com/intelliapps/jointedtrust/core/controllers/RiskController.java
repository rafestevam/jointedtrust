package br.com.intelliapps.jointedtrust.core.controllers;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.intelliapps.jointedtrust.core.models.Risk;
import br.com.intelliapps.jointedtrust.core.services.RiskService;
import br.com.intelliapps.jointedtrust.core.validators.RiskValidator;

@Controller
@RequestMapping("/risk")
public class RiskController {
	
	@Autowired
	private RiskService riskService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private Locale locale;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new RiskValidator());
	}

	@RequestMapping("")
	public String riskList(Model model) {
		List<Risk> risks = riskService.findTopRisks(PageRequest.of(0, 9));
		model.addAttribute("risks", risks);
		return "risks";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String riskForm(@ModelAttribute("risk") Risk risk) {
		return "newrisk";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ResponseEntity<?> createRisk(@Valid Risk risk, BindingResult binding, RedirectAttributes rAttr, @RequestParam MultipartFile[] file, Model model) {
		System.out.println(file.length);
		
		MultiValueMap<String, Object> responseMap = new LinkedMultiValueMap<>();
		
		if(binding.hasErrors()) {
//			binding.getModel().entrySet()
//				.stream()
//				.forEach(entry -> responseMap.add(entry.getKey(), entry.getValue()));
			return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
			//return this.riskForm(risk);
		}
		
		if(riskService.existsByRiskId(risk.getRisk_id())) {
			binding.rejectValue("risk_id", "valid.risk.error.riskid.exists");
			binding.getModel().entrySet()
				.stream()
				.forEach(entry -> responseMap.add(entry.getKey(), entry.getValue()));
			return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
			//return this.riskForm(risk);
		}
		
		risk.setGuid(this.getGuid());
		//riskService.save(risk);
		String successMessage = messageSource.getMessage("notification.risk.create.success", new String[] { risk.getName() }, locale);
		rAttr.addFlashAttribute("successMessage", successMessage);
		
		responseMap.add("successTitle", "SUCESSO!");
		responseMap.add("successMessage", (String) successMessage);
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
		//return "redirect:/risk";
		//return this.riskList(model);
	}
	
	private String getGuid() {
		return UUID.randomUUID().toString();
	}
	
}
