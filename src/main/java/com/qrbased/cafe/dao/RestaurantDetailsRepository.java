package com.qrbased.cafe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qrbased.cafe.dto.RestaurantDetails;

public interface RestaurantDetailsRepository extends JpaRepository<RestaurantDetails, Integer>{
	
	boolean existsByRestaurantName(String name);
	boolean existsByRestaurantLogo(String RestaurantLogo);
	boolean existsByRestaurantLogoAndRestaurantIdNot(String originalFilename, int restaurantId);
	

}
