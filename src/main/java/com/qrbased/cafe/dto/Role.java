package com.qrbased.cafe.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles") 
public class Role {

private String roleDescription;

@Id
@Column(length = 60) 
private String roleName;

public String getRoleDescription() {
	return roleDescription;
}

public void setRoleDescription(String roleDescription) {
	this.roleDescription = roleDescription;
}

public String getRoleName() {
	return roleName;
}

public void setRoleName(String roleName) {
	this.roleName = roleName;
}
}

