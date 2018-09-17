package br.com.intelliapps.jointedtrust.main.components;

import javax.persistence.Embeddable;

@Embeddable
public class DocFile {
	
	private String name;
	private String address;
	private String fileType;
	private String uploadedBy;
	
	public DocFile() {
	}

	public DocFile(String name, String address) {
		this.name = name;
		this.address = address;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

}
