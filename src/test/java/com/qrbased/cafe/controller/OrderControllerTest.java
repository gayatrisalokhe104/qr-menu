package com.qrbased.cafe.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.qrbased.cafe.dto.Category;
import com.qrbased.cafe.dto.Order;
import com.qrbased.cafe.service.impl.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderServiceImpl orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void testGetMenu() {
        Category category1 = new Category(/* construct your Category object */);
        Category category2 = new Category(/* construct your Category object */);
        List<Category> categories = Arrays.asList(category1, category2);
        when(orderService.getCategories()).thenReturn(categories);

        ResponseEntity<List<Category>> responseEntity = orderController.getMenu();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(categories, responseEntity.getBody());

        verify(orderService).getCategories();
    }

    @Test
    void testCreateOrder() {
        Order order = new Order(/* construct your Order object */);
        when(orderService.createOrder(order)).thenReturn(order);

        ResponseEntity<Order> responseEntity = orderController.createOrder(order);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(order, responseEntity.getBody());

        verify(orderService).createOrder(order);
    }

    @Test
    void testGetOrder() {
        long orderId = 123L;
        Order order = new Order(/* construct your Order object */);
        when(orderService.getOrder(orderId)).thenReturn(order);

        ResponseEntity<Order> responseEntity = orderController.getOrder(orderId);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(order, responseEntity.getBody());

        verify(orderService).getOrder(orderId);
    }

    // Add more test cases as needed based on your actual implementations
}

