package br.com.intelliapps.jointedtrust.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/risk")
public class RiskController {

	@RequestMapping("")
	public String riskForm() {
		return "risk";
	}
	
}
