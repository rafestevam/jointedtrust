package br.com.intelliapps.jointedtrust.authentication.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="apl_user_tbl")
public class User {
	
	@Id
	@Column(name="guid", length=100)
	private String guid;
	
	@NotNull
	private String username;
	
	@NotNull @Size(max=500)
	private String name;
	
	@Size(max=500)
	private String lastname;
	
	@NotNull 
	@Email
	private String mail;
	
	@NotNull
	private String password;
	
	@NotNull
	private String confPass;
	
	private Boolean locked;
	
	private Boolean activated;
	
	@ManyToMany(cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(name="jt_user_roles_tbl", joinColumns=@JoinColumn(name="user_guid", referencedColumnName="guid"), inverseJoinColumns=@JoinColumn(name="role_guid",referencedColumnName="guid"))
	private Set<Role> roles;

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

	public String getConfPass() {
		return confPass;
	}

	public void setConfPass(String confPass) {
		this.confPass = confPass;
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
	
}
