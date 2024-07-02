package com.qrbased.cafe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.qrbased.cafe.dao.CategoryRepo;
import com.qrbased.cafe.dao.FoodRepo;
import com.qrbased.cafe.dto.Category;
import com.qrbased.cafe.dto.Food;
import com.qrbased.cafe.exception.DuplicateEntryException;
import com.qrbased.cafe.exception.ResourceNotFoundException;
import com.qrbased.cafe.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService{
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private FoodRepo foodRepo;
	@Override
	public Food createFood(Food food, long categoryId) {
		try {
			Category dbCategory=categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with id "+categoryId));
			food.setCategory(dbCategory);
			food.setAvailable(true);
			return foodRepo.save(food);
		}
		catch (DataIntegrityViolationException e) {
			throw new DuplicateEntryException("Food already exist with name "+food.getName());
		}
	}

	@Override
	public Food updateFood(long id, Food food) {
		try {
			Food dbFood=foodRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Food not found with id "+id));
			dbFood.setName(food.getName());
			dbFood.setPrice(food.getPrice());
			dbFood.setDescription(food.getDescription());
			return foodRepo.save(dbFood);
		}
		catch (DataIntegrityViolationException e) {
			throw new DuplicateEntryException("Food already exist with name "+food.getName());
		}
		
	}

	@Override
	public Food deleteFood(long id) {
		Food dbFood=foodRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Food not found with id "+id));
		foodRepo.delete(dbFood);
		return dbFood;
	}

}
