package br.com.intelliapps.jointedtrust.authentication.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="apl_role_tbl")
public class Role {

	@Id
	@Column(name="guid", length=100)
	private String guid;
	
	private String name;
	
	@ManyToMany(mappedBy="roles")
	private Set<UserEntity> users;
	
	public Role() {}
	
	public Role(String guid, String name) {
		this.guid = guid;
		this.name = name;
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

	@ManyToMany(mappedBy="users")
	public Set<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(Set<UserEntity> users) {
		this.users = users;
	}
	
}
