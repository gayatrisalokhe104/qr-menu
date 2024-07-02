package com.qrbased.cafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qrbased.cafe.dto.Food;
import com.qrbased.cafe.exception.ForbiddenException;
import com.qrbased.cafe.security.CustomUserDetailsService;
import com.qrbased.cafe.service.impl.FoodServiceImpl;

@RestController
@RequestMapping("/api/v1/admin/food")
public class FoodController {
	@Autowired
	private FoodServiceImpl foodServiceImpl;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@PostMapping("/category/{categoryId}/create-food")
	public ResponseEntity<Food> createFood(@PathVariable int categoryId,@RequestBody Food food) {
		boolean hasPermission = customUserDetailsService.hasAnyRole("ADMIN");
		
		 if (!hasPermission) {
	            throw new ForbiddenException("You do not have permission to access this resource", HttpStatus.FORBIDDEN.value());
	        }
		
		return ResponseEntity.status(HttpStatus.CREATED.value()).body(foodServiceImpl.createFood(food, categoryId));
	}

	@PutMapping("/{foodId}/update")
	public ResponseEntity<Food> updateFood(@PathVariable long foodId, @RequestBody Food food) {
		boolean hasPermission = customUserDetailsService.hasAnyRole("ADMIN");
		
		 if (!hasPermission) {
	            throw new ForbiddenException("You do not have permission to access this resource", HttpStatus.FORBIDDEN.value());
	        }
		
		return ResponseEntity.status(HttpStatus.OK.value()).body(foodServiceImpl.updateFood(foodId, food));
	}

	@DeleteMapping("/{foodId}/delete")
	public ResponseEntity<Food> deleteFood(@PathVariable long foodId) {
		boolean hasPermission = customUserDetailsService.hasAnyRole("ADMIN");
		
		 if (!hasPermission) {
	            throw new ForbiddenException("You do not have permission to access this resource", HttpStatus.FORBIDDEN.value());
	        }
		
		return ResponseEntity.status(HttpStatus.OK.value()).body(foodServiceImpl.deleteFood(foodId));
	}

}
