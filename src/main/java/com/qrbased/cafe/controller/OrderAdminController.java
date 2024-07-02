package com.qrbased.cafe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qrbased.cafe.dto.Food;
import com.qrbased.cafe.dto.Order;
import com.qrbased.cafe.exception.ApiResponse;
import com.qrbased.cafe.exception.ForbiddenException;
import com.qrbased.cafe.security.CustomUserDetailsService;
import com.qrbased.cafe.service.impl.OrderServiceImpl;

@RestController
@RequestMapping("/api/v1/admin/order")
public class OrderAdminController {
	@Autowired
	private OrderServiceImpl orderServiceImpl;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@GetMapping("/getAll")
	public ResponseEntity<List<Order>> getAllOrders() {
		boolean hasPermission = customUserDetailsService.hasAnyRole("ADMIN");
		
		 if (!hasPermission) {
	            throw new ForbiddenException("You do not have permission to access this resource", HttpStatus.FORBIDDEN.value());
	        }
		
		return ResponseEntity.status(HttpStatus.OK.value()).body(orderServiceImpl.getAllOrders());
	}

	@DeleteMapping("/deleteAll")
	public ResponseEntity<ApiResponse> deleteAll() {
		boolean hasPermission = customUserDetailsService.hasAnyRole("ADMIN");
		
		 if (!hasPermission) {
	            throw new ForbiddenException("You do not have permission to access this resource", HttpStatus.FORBIDDEN.value());
	        }
		
		return ResponseEntity.status(HttpStatus.OK.value()).body(orderServiceImpl.deleteAllOrders());
	}

	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<Order> deleteFood(@PathVariable long orderId) {
		boolean hasPermission = customUserDetailsService.hasAnyRole("ADMIN");
		
		 if (!hasPermission) {
	            throw new ForbiddenException("You do not have permission to access this resource", HttpStatus.FORBIDDEN.value());
	        }
		
		return ResponseEntity.status(HttpStatus.OK.value()).body(orderServiceImpl.deleteOrder(orderId));
	}
}
