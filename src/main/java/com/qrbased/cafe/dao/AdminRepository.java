package com.qrbased.cafe.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.qrbased.cafe.dto.Admin;

public interface AdminRepository extends JpaRepository<Admin, String>{
	
	Optional<Admin> findByEmail(String email);
	Boolean existsByEmail(String email);
	Admin findByResetToken(String resetToken);
	void deleteAdminByEmail(String userEmail);

}