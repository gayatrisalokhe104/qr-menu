package com.qrbased.cafe.service;

import java.util.List;
import com.qrbased.cafe.dto.Category;
import com.qrbased.cafe.dto.Order;
import com.qrbased.cafe.exception.ApiResponse;

public interface OrderService {
	List<Category> getCategories();
	Order createOrder(Order order);
	Order getOrder(long orderId);
	List<Order> getAllOrders();
	ApiResponse deleteAllOrders();
	Order deleteOrder(long id);
}
