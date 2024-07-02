package com.qrbased.cafe.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.qrbased.cafe.dao.CategoryRepo;
import com.qrbased.cafe.dto.Category;
import com.qrbased.cafe.exception.DuplicateEntryException;
import com.qrbased.cafe.exception.ResourceNotFoundException;
import com.qrbased.cafe.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepo categoryRepo;
	@Override
	public Category createCategory(Category category) {
		try {
			return categoryRepo.save(category);
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateEntryException("Category already exist with "+category.getName());
		}
	}

	@Override
	public Category updateCategory(long id, Category category) {
		try {
			Category dbCategory=categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found with id "+id));
			dbCategory.setName(category.getName());
			dbCategory.setFoodList(category.getFoodList());
			return categoryRepo.save(dbCategory);
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateEntryException("Category already exist with "+category.getName());
		}
	}

	@Override
	public Category deleteCategory(long id) {
		Category dbCategory=categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found with id "+id));
		categoryRepo.delete(dbCategory);
		return dbCategory;
	}

	@Override
	public List<Category> getCategories() {
		return categoryRepo.findAll();
	}

}
