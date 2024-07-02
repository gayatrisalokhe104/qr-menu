package com.qrbased.cafe.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import com.qrbased.cafe.dao.CategoryRepo;
import com.qrbased.cafe.dao.FoodRepo;
import com.qrbased.cafe.dto.Category;
import com.qrbased.cafe.dto.Food;
import com.qrbased.cafe.exception.DuplicateEntryException;
import com.qrbased.cafe.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class FoodServiceImplTest {

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private FoodRepo foodRepo;

    @InjectMocks
    private FoodServiceImpl foodService;

    @Test
    void testCreateFood_Success() {
        // Arrange
        long categoryId = 1L;
        Category dbCategory = new Category();
        dbCategory.setId(categoryId);

        Food food = new Food();
        food.setName("Test Food");
        food.setPrice(10.99);
        food.setDescription("Description");

        when(categoryRepo.findById(eq(categoryId))).thenReturn(Optional.of(dbCategory));
        when(foodRepo.save(any(Food.class))).thenReturn(food);

        // Act
        Food result = foodService.createFood(food, categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(food, result);
        assertEquals(dbCategory, result.getCategory());
        assertTrue(result.isAvailable());
        verify(categoryRepo, times(1)).findById(eq(categoryId));
        verify(foodRepo, times(1)).save(any(Food.class));
    }

    @Test
    void testCreateFood_DuplicateEntryException() {
        // Arrange
        long categoryId = 1L;
        Food food = new Food();
        food.setName("Test Food");

        when(categoryRepo.findById(eq(categoryId))).thenReturn(Optional.of(new Category()));
        when(foodRepo.save(any(Food.class))).thenThrow(new DataIntegrityViolationException("Duplicate entry"));

        // Act & Assert
        assertThrows(DuplicateEntryException.class, () -> foodService.createFood(food, categoryId));
        verify(categoryRepo, times(1)).findById(eq(categoryId));
        verify(foodRepo, times(1)).save(any(Food.class));
    }

    @Test
    void testUpdateFood_Success() {
        // Arrange
        long foodId = 1L;
        Food dbFood = new Food();
        dbFood.setId(foodId);
        dbFood.setName("OldName");

        Food updatedFood = new Food();
        updatedFood.setId(foodId);
        updatedFood.setName("NewName");
        updatedFood.setPrice(15.99);
        updatedFood.setDescription("New Description");

        when(foodRepo.findById(eq(foodId))).thenReturn(Optional.of(dbFood));
        when(foodRepo.save(any(Food.class))).thenReturn(updatedFood);

        // Act
        Food result = foodService.updateFood(foodId, updatedFood);

        // Assert
        assertNotNull(result);
        assertEquals(updatedFood, result);
        verify(foodRepo, times(1)).findById(eq(foodId));
        verify(foodRepo, times(1)).save(any(Food.class));
    }

    @Test
    void testUpdateFood_DuplicateEntryException() {
        // Arrange
        long foodId = 1L;
        Food updatedFood = new Food();
        updatedFood.setName("Test Food");

        when(foodRepo.findById(eq(foodId))).thenReturn(Optional.of(new Food()));
        when(foodRepo.save(any(Food.class))).thenThrow(new DataIntegrityViolationException("Duplicate entry"));

        // Act & Assert
        assertThrows(DuplicateEntryException.class, () -> foodService.updateFood(foodId, updatedFood));
        verify(foodRepo, times(1)).findById(eq(foodId));
        verify(foodRepo, times(1)).save(any(Food.class));
    }

    @Test
    void testDeleteFood_Success() {
        // Arrange
        long foodId = 1L;
        Food dbFood = new Food();
        dbFood.setId(foodId);

        when(foodRepo.findById(eq(foodId))).thenReturn(Optional.of(dbFood));

        // Act
        Food result = foodService.deleteFood(foodId);

        // Assert
        assertNotNull(result);
        assertEquals(dbFood, result);
        verify(foodRepo, times(1)).findById(eq(foodId));
        verify(foodRepo, times(1)).delete(eq(dbFood));
    }

    @Test
    void testDeleteFood_ResourceNotFoundException() {
        // Arrange
        long foodId = 1L;
        when(foodRepo.findById(eq(foodId))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> foodService.deleteFood(foodId));
        verify(foodRepo, times(1)).findById(eq(foodId));
    }
}

