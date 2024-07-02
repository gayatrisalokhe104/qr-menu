package com.qrbased.cafe.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.qrbased.cafe.dto.Category;
import com.qrbased.cafe.dto.Order;
import com.qrbased.cafe.service.impl.OrderServiceImpl;

@RestController
@RequestMapping("/api/v1/user")
public class OrderController {
	@Autowired
	private OrderServiceImpl orderServiceImpl;

	@GetMapping("/menu")
	public ResponseEntity<List<Category>> getMenu() {
		return ResponseEntity.status(HttpStatus.OK.value()).body(orderServiceImpl.getCategories());
	}

	@PostMapping("/order/create")
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {
		return ResponseEntity.status(HttpStatus.CREATED.value()).body(orderServiceImpl.createOrder(order));
	}

	@GetMapping("/order/{orderId}")
	public ResponseEntity<Order> getOrder(@PathVariable long orderId) {
		return ResponseEntity.status(HttpStatus.CREATED.value()).body(orderServiceImpl.getOrder(orderId));
	}
}
