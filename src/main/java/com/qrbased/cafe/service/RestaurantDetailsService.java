package com.qrbased.cafe.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.qrbased.cafe.dto.RestaurantDetails;

public interface RestaurantDetailsService {
	RestaurantDetails createRestaurantDetails(RestaurantDetails restaurantDetail, MultipartFile file);
	RestaurantDetails updateRestaurantDetails(int restaurantId,RestaurantDetails restaurantDetail);
	Resource updateLogo(int restaurantId, MultipartFile file);
	RestaurantDetails getRestaurantDetailsById(int restaurantId);
	
}
