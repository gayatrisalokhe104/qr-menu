package com.qrbased.cafe.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.qrbased.cafe.dao.CategoryRepo;
import com.qrbased.cafe.dao.OrderRepo;
import com.qrbased.cafe.dto.Category;
import com.qrbased.cafe.dto.Food;
import com.qrbased.cafe.dto.Order;
import com.qrbased.cafe.exception.ApiResponse;
import com.qrbased.cafe.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private OrderRepo orderRepo;

//    @Value("${stripe.api.secretKey}")
//    private String stripeSecretKey;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void getCategories() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category1");

        Food food1 = new Food();
        food1.setName("Food1");
        food1.setAvailable(true);

        Food food2 = new Food();
        food2.setName("Food2");
        food2.setAvailable(false);

        category1.setFoodList(Arrays.asList(food1, food2));

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category2");

        Food food3 = new Food();
        food3.setName("Food3");
        food3.setAvailable(true);

        category2.setFoodList(Arrays.asList(food3));

        when(categoryRepo.findAll()).thenReturn(Arrays.asList(category1, category2));

        // Act
        List<Category> result = orderService.getCategories();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Category1", result.get(0).getName());
        assertEquals(1, result.get(0).getFoodList().size());
        assertEquals("Category2", result.get(1).getName());
        assertEquals(1, result.get(1).getFoodList().size());
    }

//    @Test
//    void createOrder_Success(Order order) throws StripeException {
//        // Arrange
//        order.setTokenId("tokenId");
//        order.setTotalAmount(100.0);
//
//        PaymentIntent paymentIntent = new PaymentIntent();
//        paymentIntent.setId("pi_12345");
//        paymentIntent.setClientSecret("clientSecret");
//        paymentIntent.setStatus("succeeded");
//
//        Stripe.apiKey = stripeSecretKey;
//        when(orderRepo.save(any(Order.class))).thenReturn(order);
//        when(PaymentIntent.create(any(PaymentIntentCreateParams.class))).thenReturn(paymentIntent);
//
//        // Act
//        Order result = orderService.createOrder(order);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(order, result);
//        assertEquals("pi_12345", result.getPaymentIntentId());
//        assertEquals("clientSecret", result.getClientSecret());
//        assertTrue(result.isPaymentStatus());
//        assertNotNull(result.getLocalDateTime());
//        verify(orderRepo, times(1)).save(any(Order.class));
//        verify(PaymentIntent.class, times(1));
//    }
//
//    @Test
//    void createOrder_PaymentException(Order order) throws StripeException {
//        // Arrange
//        order.setTokenId("tokenId");
//        order.setTotalAmount(100.0);
//
//        Stripe.apiKey = stripeSecretKey;
//        when(PaymentIntent.create(any(PaymentIntentCreateParams.class))).thenThrow(new PaymentException( HttpStatus.INTERNAL_SERVER_ERROR.value(),"Payment Failed"));
//
//        // Act & Assert
//        assertThrows(PaymentException.class, () -> orderService.createOrder(order));
//        verify(orderRepo, never()).save(any(Order.class));
//    }

    @Test
    void getOrder_Success() {
        // Arrange
        long orderId = 1L;
        Order dbOrder = new Order();
        dbOrder.setOrderId(orderId);

        when(orderRepo.findByOrderId(eq(orderId))).thenReturn(dbOrder);

        // Act
        Order result = orderService.getOrder(orderId);

        // Assert
        assertNotNull(result);
        assertEquals(dbOrder, result);
        verify(orderRepo, times(1)).findByOrderId(eq(orderId));
    }

    @Test
    void getOrder_ResourceNotFoundException() {
        // Arrange
        long orderId = 1L;
        when(orderRepo.findByOrderId(eq(orderId))).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrder(orderId));
        verify(orderRepo, times(1)).findByOrderId(eq(orderId));
    }

    @Test
    void getAllOrders() {
        // Arrange
        Order order1 = new Order();
        order1.setOrderId(1L);

        Order order2 = new Order();
        order2.setOrderId(2L);

        when(orderRepo.findAll()).thenReturn(Arrays.asList(order1, order2));

        // Act
        List<Order> result = orderService.getAllOrders();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getOrderId());
        assertEquals(2L, result.get(1).getOrderId());
        verify(orderRepo, times(1)).findAll();
    }

    @Test
    void deleteAllOrders_Success() {
        // Arrange
        when(orderRepo.findAll()).thenReturn(Arrays.asList(new Order(), new Order()));

        // Act
        ApiResponse result = orderService.deleteAllOrders();

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getCode());
        assertEquals("2 Orders deleted", result.getMessage());
        verify(orderRepo, times(1)).deleteAll();
    }

    @Test
    void deleteAllOrders_EmptyOrders() {
        // Arrange
        when(orderRepo.findAll()).thenReturn(new ArrayList<>());

        // Act
        ApiResponse result = orderService.deleteAllOrders();

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getCode());
        assertEquals("Orders are empty", result.getMessage());
        verify(orderRepo, never()).deleteAll();
    }

    @Test
    void deleteOrder_Success() {
        // Arrange
        long orderId = 1L;
        Order dbOrder = new Order();
        dbOrder.setOrderId(orderId);

        when(orderRepo.findByOrderId(eq(orderId))).thenReturn(dbOrder);

        // Act
        Order result = orderService.deleteOrder(orderId);

        // Assert
        assertNotNull(result);
        assertEquals(dbOrder, result);
        verify(orderRepo, times(1)).findByOrderId(eq(orderId));
        verify(orderRepo, times(1)).delete(eq(dbOrder));
    }

    @Test
    void deleteOrder_ResourceNotFoundException() {
        // Arrange
        long orderId = 1L;
        when(orderRepo.findByOrderId(eq(orderId))).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> orderService.deleteOrder(orderId));
        verify(orderRepo, times(1)).findByOrderId(eq(orderId));
        verify(orderRepo, never()).delete(any(Order.class));
    }
}

