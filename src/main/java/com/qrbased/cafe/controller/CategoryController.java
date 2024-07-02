package com.qrbased.cafe.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qrbased.cafe.dto.Category;
import com.qrbased.cafe.exception.ForbiddenException;
import com.qrbased.cafe.security.CustomUserDetailsService;
import com.qrbased.cafe.service.impl.CategoryServiceImpl;

@RestController
@RequestMapping("/api/v1/admin/category")
public class CategoryController {
	
   @Autowired
   private CategoryServiceImpl categoryServiceImpl;
   @Autowired
	private CustomUserDetailsService customUserDetailsService;

   
   @PostMapping("/create-category")
   public ResponseEntity<Category> createCategory(@RequestBody Category category){
	   boolean hasPermission = customUserDetailsService.hasAnyRole("ADMIN");
		
		 if (!hasPermission) {
	            throw new ForbiddenException("You do not have permission to access this resource", HttpStatus.FORBIDDEN.value());
	        }
	   
	   return ResponseEntity.status(HttpStatus.CREATED.value()).body(categoryServiceImpl.createCategory(category));
   }
   
   @PutMapping("/{categoryId}/update")
   public ResponseEntity<Category> updateCategory(@PathVariable long categoryId,@RequestBody Category category){
	   boolean hasPermission = customUserDetailsService.hasAnyRole("ADMIN");
		
		 if (!hasPermission) {
	            throw new ForbiddenException("You do not have permission to access this resource", HttpStatus.FORBIDDEN.value());
	        }
	   
	   return ResponseEntity.status(HttpStatus.OK.value()).body(categoryServiceImpl.updateCategory(categoryId, category));
   }
   
   @DeleteMapping("/{categoryId}/delete")
   public ResponseEntity<Category> deleteCategory(@PathVariable long categoryId){
	   boolean hasPermission = customUserDetailsService.hasAnyRole("ADMIN");
		
		 if (!hasPermission) {
	            throw new ForbiddenException("You do not have permission to access this resource", HttpStatus.FORBIDDEN.value());
	        }
	   
	   return ResponseEntity.status(HttpStatus.OK.value()).body(categoryServiceImpl.deleteCategory(categoryId));
   }
   
   @GetMapping("/menu")
   public ResponseEntity<List<Category>> getMenu(){
	   boolean hasPermission = customUserDetailsService.hasAnyRole("ADMIN");
		
		 if (!hasPermission) {
	            throw new ForbiddenException("You do not have permission to access this resource", HttpStatus.FORBIDDEN.value());
	        }
	   
	   return ResponseEntity.status(HttpStatus.OK.value()).body(categoryServiceImpl.getCategories());
   }
}
