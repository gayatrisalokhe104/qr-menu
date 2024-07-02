package com.qrbased.cafe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qrbased.cafe.dto.Food;

public interface FoodRepo extends JpaRepository<Food, Long>{

}
