package br.com.intelliapps.jointedtrust.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.intelliapps.jointedtrust.core.models.Risk;

@Controller
@RequestMapping("/risk")
public class RiskController {

	@RequestMapping("")
	public String riskForm() {
		return "risk";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String createRisk(Risk risk) {
		return "newrisk";
	}
	
}
