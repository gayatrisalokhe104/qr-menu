package com.qrbased.cafe.service.impl;

import com.qrbased.cafe.dao.CategoryRepo;
import com.qrbased.cafe.dto.Category;
import com.qrbased.cafe.exception.DuplicateEntryException;
import com.qrbased.cafe.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

	@Mock
	private CategoryRepo categoryRepo;

	@InjectMocks
	private CategoryServiceImpl categoryService;

	@Test
	void testCreateCategory_Success() {
		Category category = new Category();
		when(categoryRepo.save(any(Category.class))).thenReturn(category);

		Category result = categoryService.createCategory(category);

		assertNotNull(result);
		assertEquals(category, result);
		verify(categoryRepo, times(1)).save(eq(category));
	}

	@Test
	void testCreateCategory_DuplicateEntryException() {
		Category category = new Category();
		when(categoryRepo.save(any(Category.class))).thenThrow(new DataIntegrityViolationException("Duplicate entry"));

		assertThrows(DuplicateEntryException.class, () -> categoryService.createCategory(category));
		verify(categoryRepo, times(1)).save(eq(category));
	}

	@Test
	void testUpdateCategory_Success() {
		long categoryId = 1L;
		Category existingCategory = new Category();
		existingCategory.setId(categoryId);
		existingCategory.setName("OldName");

		Category updatedCategory = new Category();
		updatedCategory.setId(categoryId);
		updatedCategory.setName("NewName");

		when(categoryRepo.findById(eq(categoryId))).thenReturn(Optional.of(existingCategory));
		when(categoryRepo.save(any(Category.class))).thenReturn(updatedCategory);

		Category result = categoryService.updateCategory(categoryId, updatedCategory);

		assertNotNull(result);
		assertEquals(updatedCategory, result);
		verify(categoryRepo, times(1)).findById(eq(categoryId));
		verify(categoryRepo, times(1)).save(eq(updatedCategory));
	}

	@Test
	void testUpdateCategory_ResourceNotFoundException() {
		long categoryId = 1L;
		Category updatedCategory = new Category();
		updatedCategory.setId(categoryId);

		when(categoryRepo.findById(eq(categoryId))).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class,
				() -> categoryService.updateCategory(categoryId, updatedCategory));
		verify(categoryRepo, times(1)).findById(eq(categoryId));
	}

	@Test
	void testUpdateCategory_DuplicateEntryException() {
		long categoryId = 1L;
		Category existingCategory = new Category();
		existingCategory.setId(categoryId);
		existingCategory.setName("OldName");

		Category updatedCategory = new Category();
		updatedCategory.setId(categoryId);
		updatedCategory.setName("NewName");

		when(categoryRepo.findById(eq(categoryId))).thenReturn(Optional.of(existingCategory));
		when(categoryRepo.save(any(Category.class))).thenThrow(new DataIntegrityViolationException("Duplicate entry"));

		assertThrows(DuplicateEntryException.class, () -> categoryService.updateCategory(categoryId, updatedCategory));
		verify(categoryRepo, times(1)).findById(eq(categoryId));
		verify(categoryRepo, times(1)).save(eq(updatedCategory));
	}

	@Test
	void testDeleteCategory_Success() {
		long categoryId = 1L;
		Category existingCategory = new Category();
		existingCategory.setId(categoryId);

		when(categoryRepo.findById(eq(categoryId))).thenReturn(Optional.of(existingCategory));

		Category result = categoryService.deleteCategory(categoryId);

		assertNotNull(result);
		assertEquals(existingCategory, result);
		verify(categoryRepo, times(1)).findById(eq(categoryId));
		verify(categoryRepo, times(1)).delete(eq(existingCategory));
	}

	@Test
	void testDeleteCategory_ResourceNotFoundException() {
		long categoryId = 1L;
		when(categoryRepo.findById(eq(categoryId))).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(categoryId));
		verify(categoryRepo, times(1)).findById(eq(categoryId));
	}

	@Test
	void getCategories_Success() {
		List<Category> categories = new ArrayList<>();
		when(categoryRepo.findAll()).thenReturn(categories);

		List<Category> result = categoryService.getCategories();

		assertNotNull(result);
		assertEquals(categories, result);
		verify(categoryRepo, times(1)).findAll();
	}
}
