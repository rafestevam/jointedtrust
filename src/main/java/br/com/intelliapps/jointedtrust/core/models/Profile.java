package br.com.intelliapps.jointedtrust.core.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.intelliapps.jointedtrust.authentication.models.UserEntity;

@Entity
@Table(name="apl_profile_tbl")
public class Profile {

	@Id
	@Column(name="guid", length=100)
	private String guid;
	
	private String name;
	
	private String lastname;
	
	private String email;
	
	private String phonenumber;
	
	private Date birthday;
	
	private Date createdate;
	
	private String jobposition;
	
	private String photoaddress;
	
	@OneToOne(fetch=FetchType.LAZY)
	private UserEntity user;
	
	public Profile() {}
	
	public Profile(String name, String lastname, String mail, Date createdate) {
		this.name = name;
		this.lastname = lastname;
		this.email = mail;
		this.createdate = createdate;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getJobposition() {
		return jobposition;
	}

	public void setJobposition(String jobposition) {
		this.jobposition = jobposition;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhotoaddress() {
		return photoaddress;
	}

	public void setPhotoaddress(String photoaddress) {
		this.photoaddress = photoaddress;
	}
}
