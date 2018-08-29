package br.com.intelliapps.jointedtrust.test.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DropzoneTestController {

	@RequestMapping(value="/testdr", method=RequestMethod.GET)
	public String dropzoneTest() {
		return "testdropzone";
	}
	
	@RequestMapping(value="/testdr", method=RequestMethod.POST)
	public String receiveFile(@RequestParam MultipartFile[] file) {
		System.out.println(file.length);
		return "testdropzone";
	}
	
}
