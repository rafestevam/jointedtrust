package br.com.intelliapps.jointedtrust.authentication.models;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import br.com.intelliapps.jointedtrust.core.models.Profile;

@Entity
@Table(name="apl_user_tbl")
public class UserEntity {
	
	@Id
	@Column(name="guid", length=100)
	private String guid;
	
	private String username;
	
	private String name;
	
	private String lastname;
	
	@Email
	private String mail;
	
	private String password;
	
	private String confpass;
	
	private Boolean locked;
	
	private Boolean activated;
	
	@ManyToMany(cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(name="jt_user_roles_tbl", joinColumns=@JoinColumn(name="user_guid", referencedColumnName="guid"), inverseJoinColumns=@JoinColumn(name="role_guid",referencedColumnName="guid"))
	private Set<Role> roles;
	
	@OneToOne(mappedBy="user", cascade={CascadeType.MERGE, CascadeType.PERSIST}, optional=false, fetch=FetchType.LAZY)
	private Profile profile;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfpass() {
		return confpass;
	}

	public void setConfpass(String confpass) {
		this.confpass = confpass;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}
	
	public void createProfile(String guid) {
		Calendar calendar = Calendar.getInstance();
		this.profile = new Profile(this.name, this.lastname, this.mail, calendar.getTime());
		this.profile.setGuid(guid);
		this.profile.setUser(this);
	}
	
	public Profile getProfile() {
		return profile;
	}
	
}
