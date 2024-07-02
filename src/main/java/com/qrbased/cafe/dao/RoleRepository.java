package com.qrbased.cafe.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qrbased.cafe.dto.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
	Optional<Role> findByRoleName(String name);
}