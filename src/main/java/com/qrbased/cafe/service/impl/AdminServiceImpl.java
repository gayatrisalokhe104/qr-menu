package com.qrbased.cafe.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.qrbased.cafe.dao.AdminRepository;
import com.qrbased.cafe.dao.RoleRepository;
import com.qrbased.cafe.dto.Admin;
import com.qrbased.cafe.dto.Role;
import com.qrbased.cafe.exception.AdminRegistrationException;
import com.qrbased.cafe.exception.ResourceNotFoundException;
import com.qrbased.cafe.service.AdminService;
import com.qrbased.cafe.util.SignUpRequest;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	

	private ModelMapper mapper;
	public AdminServiceImpl(ModelMapper mapper) {
		this.mapper = mapper;
	}

	
	public void initRoleAndUser() {
        Role adminRole = new Role();
        adminRole.setRoleName("ADMIN");
        adminRole.setRoleDescription("Default admin Role");
        roleRepository.save(adminRole);

        Admin adminUser1 = new Admin();
        adminUser1.setName("cafe admin");
        adminUser1.setEmail("admin@gmail.com");
        adminUser1.setPassword(getEncodedPassword("Admin@123"));

        Role adminUserRole = roleRepository.findById("ADMIN")
                .orElseThrow(() -> new ResourceNotFoundException("ADMIN role not found"));

        adminUser1.setRoles(adminUserRole);
        adminRepository.save(adminUser1);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public SignUpRequest registerAdmin(SignUpRequest signUpRequest) {
        Admin user = new Admin();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Role userRole = roleRepository.findById("ADMIN")
                .orElseThrow(() -> new ResourceNotFoundException("ADMIN role not found"));

        user.setRoles(userRole);

        try {
            Admin saved = adminRepository.save(user);
            return mapToDto(saved);
        } catch (Exception e) {
            throw new AdminRegistrationException("Error registering admin: " + e.getMessage());
        }
    }
    
//    @Override
//    public SignUpRequest registerAdmin(SignUpRequest signUpRequest) {
//        Admin user = new Admin();
//        user.setName(signUpRequest.getName());
//        user.setEmail(signUpRequest.getEmail());
//        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
//
//        Role userRole = roleRepository.findById("ADMIN")
//                .orElseThrow(() -> new ResourceNotFoundException("ADMIN role not found"));
//
//        user.setRoles(userRole);
//        Admin saved = adminRepository.save(user);
//        SignUpRequest dto = mapToDto(saved);
//
//        return dto;
//    }

    private SignUpRequest mapToDto(Admin admin) {
        SignUpRequest signUpRequest = mapper.map(admin, SignUpRequest.class);
        return signUpRequest;
    }

}
