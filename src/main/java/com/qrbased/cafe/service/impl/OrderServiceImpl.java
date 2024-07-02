package com.qrbased.cafe.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.qrbased.cafe.dao.CategoryRepo;
import com.qrbased.cafe.dao.OrderRepo;
import com.qrbased.cafe.dto.Category;
import com.qrbased.cafe.dto.Food;
import com.qrbased.cafe.dto.Order;
import com.qrbased.cafe.exception.ApiResponse;
import com.qrbased.cafe.exception.PaymentException;
import com.qrbased.cafe.exception.ResourceNotFoundException;
import com.qrbased.cafe.service.OrderService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Value("${stripe.api.secretKey}")
    private String stripeSecretKey;
	@Override
	public List<Category> getCategories() {
		List<Category> menu=new ArrayList<Category>();
		List<Category> dbMenu=categoryRepo.findAll();
		for (Category dbCategory : dbMenu) {
			Category category=new Category();
			category.setId(dbCategory.getId());
			category.setName(dbCategory.getName());
			List<Food> foods=new ArrayList<Food>();
			for (Food food : dbCategory.getFoodList()) {
				if(food.isAvailable()) {
					foods.add(food);
				}
			}
			category.setFoodList(foods);
			if(category.getFoodList().size() > 0) {
				menu.add(category);
			}
		}
		return menu;
	}

	@Override
	public Order createOrder(Order order) {
		 try {
	            Stripe.apiKey = stripeSecretKey;
	            PaymentIntent paymentIntent = createPaymentIntent(order);
//	            if (!"succeeded".equals(paymentIntent.getStatus())) {
	            order.setPaymentIntentId(paymentIntent.getId());
	            order.setPaymentStatus(true);
	            order.setClientSecret(paymentIntent.getClientSecret());
	            order.setLocalDateTime(LocalDateTime.now());
	            return orderRepo.save(order);
//	            }
	        } catch (StripeException e) {
	            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Payment Failed");
	        }
		
	}
	
	private PaymentIntent createPaymentIntent(Order order) throws StripeException {
        return PaymentIntent.create(
                new PaymentIntentCreateParams.Builder()
                        .setCurrency("inr")
                        .setAmount((long) (order.getTotalAmount()))  // Stripe amounts are in cents
                        .setPaymentMethod(order.getTokenId())
                        .build()
        );
    }

	@Override
	public Order getOrder(long orderId) {
		Order dbOrder=orderRepo.findByOrderId(orderId);
		if (dbOrder != null) {
		    return dbOrder;
		} else {
			throw new ResourceNotFoundException("Order not found with orderId "+orderId);
		}
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepo.findAll();
	}

	@Override
	public ApiResponse deleteAllOrders() {
		List<Order> dbOrders=getAllOrders();
		if(dbOrders.size() > 0) {
		orderRepo.deleteAll();
		return new ApiResponse(HttpStatus.OK.value(), dbOrders.size()+" Orders deleted");
		}
		return new ApiResponse(HttpStatus.OK.value(), "Orders are empty");
	}

	@Override
	public Order deleteOrder(long orderId) {
		Order dbOrder=orderRepo.findByOrderId(orderId);
		if (dbOrder != null) {
		   orderRepo.delete(dbOrder);
		   return dbOrder;
		} else {
			throw new ResourceNotFoundException("Order not found with orderId "+orderId);
		}
	}

}
