package com.qrbased.cafe.dto;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "admin",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "email" })
		})
@JsonIgnoreProperties({"resetToken","resetTokenExpiration"})//
public class Admin {

	@Column(name = "name", nullable = false)
	private String name;
	
	@Id
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	private String resetToken;

	private LocalDateTime resetTokenExpiration;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public LocalDateTime getResetTokenExpiration() {
		return resetTokenExpiration;
	}

	public void setResetTokenExpiration(LocalDateTime resetTokenExpiration) {
		this.resetTokenExpiration = resetTokenExpiration;
	}
	
	//role and User mapping
//	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinTable(name = "admin_roles", joinColumns = @JoinColumn(name = "admin_email", referencedColumnName = "email"), 
//	inverseJoinColumns = @JoinColumn(name = "role_name", referencedColumnName = "roleName"))
//	private Set<Role> roles;
	 @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	    @JoinColumn(name = "role_name", referencedColumnName = "roleName")
	    private Role role;

	public Role getRoles() {
		return role;
	}

	public void setRoles(Role role) {
		this.role = role;
	}

	@PreRemove
	private void preRemove() {
		if(role!=null) {
			role=null;
		}
	}

	
	/**/	
}

