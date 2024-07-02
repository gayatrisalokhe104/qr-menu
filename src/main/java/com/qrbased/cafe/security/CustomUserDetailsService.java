package com.qrbased.cafe.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.qrbased.cafe.dao.AdminRepository;
import com.qrbased.cafe.dto.Admin;
import com.qrbased.cafe.dto.Role;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	 private AdminRepository adminRepository;

	    public CustomUserDetailsService(AdminRepository adminRepository) {
	        this.adminRepository = adminRepository;
	    }

	    @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        Admin user = adminRepository.findByEmail(email)
	                .orElseThrow(() -> new UsernameNotFoundException("Admin not found with email:" + email));
	        Role role = user.getRoles();
	        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
	                mapRoleToAuthority(role));
	    }

	    private Collection<? extends GrantedAuthority> mapRoleToAuthority(Role role) {
	        if (role != null) {
	            return Collections.singletonList(new SimpleGrantedAuthority(role.getRoleName()));
	        } else {
	            return Collections.emptyList();
	        }
	    }

	    public boolean hasAnyRole(String... roleNames) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	            for (String roleName : roleNames) {
	                if (userDetails.getAuthorities().stream()
	                        .anyMatch(authority -> authority.getAuthority().equals(roleName))) {
	                    return true;
	                }
	            }
	        }
	        return false;
	    }
}

