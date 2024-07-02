package com.qrbased.cafe.service;

import com.qrbased.cafe.dto.Food;

public interface FoodService {
    Food createFood(Food food,long categoryId);
    Food updateFood(long id,Food food);
    Food deleteFood(long orderId);
}
