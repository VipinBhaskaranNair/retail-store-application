package com.store.invoice.service;



import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.store.invoice.constants.CustomerTypes;
import com.store.invoice.constants.ProductTypes;
import com.store.invoice.exception.InvalidCustomerTypeException;
import com.store.invoice.model.CartItem;
import com.store.invoice.model.CustomerDetails;
import com.store.invoice.model.DiscountedShoppingCart;
import com.store.invoice.model.Product;
import com.store.invoice.model.ShoppingCart;

@SpringBootTest
class DiscounterShoppingCartServiceTest {
	
	@Autowired
	DiscountedShoppingCartService discountedShoppingCartService;
	
	CustomerDetails customerDetails;
	List<CartItem> cartItems = new ArrayList<>();
	ShoppingCart shoppingCart;
	@BeforeEach
	void setup() {
		customerDetails = new CustomerDetails("CU001", "Tom", CustomerTypes.AFFILIATE, LocalDateTime.of(2024, 1, 22, 3, 15));
		Product product1 = new Product("P001","Shirt",ProductTypes.CLOTHING,120.00d);
		Product product2 = new Product("P002","Onion",ProductTypes.GROCERY,2.00d);
		CartItem cartItem1 = new CartItem(product1, 1);
		CartItem cartItem2 = new CartItem(product2, 2);
		cartItems = new ArrayList<>();
		cartItems.add(cartItem1);
		cartItems.add(cartItem2);
		
		
	}
	
	@Test
	void testAffiliateCustomer() throws InvalidCustomerTypeException {
		shoppingCart = new ShoppingCart("C001", customerDetails, LocalDateTime.of(2024, 06, 16, 3, 15), cartItems);
		DiscountedShoppingCart discountedShoppingCart = discountedShoppingCartService.calculateDiscount(shoppingCart);
		assertEquals(107,discountedShoppingCart.getDiscountedCartAmount());
	}
	
	@Test
	void testAffiliateCustomerInvalidCustomerType() {
		String message = "";
		customerDetails = new CustomerDetails("CU001", "Tom", null, LocalDateTime.of(2024, 1, 22, 3, 15));
		shoppingCart = new ShoppingCart("C001", customerDetails, LocalDateTime.of(2024, 06, 16, 3, 15), cartItems);
		try {
			discountedShoppingCartService.calculateDiscount(shoppingCart);
		} catch (InvalidCustomerTypeException e) {
			message = e.getMessage();
		}
		
		assertEquals("Customer type is not configured", message);
	}
	
	@Test
	void testAffiliateCustomerLongTermCustomerType() throws InvalidCustomerTypeException {
		customerDetails = new CustomerDetails("CU001", "Tom", CustomerTypes.LONG_TERM_CUSTOMER, LocalDateTime.of(2024, 1, 22, 3, 15));
		shoppingCart = new ShoppingCart("C001", customerDetails, LocalDateTime.of(2024, 06, 16, 3, 15), cartItems);
		DiscountedShoppingCart discountedShoppingCart = discountedShoppingCartService.calculateDiscount(shoppingCart);
		assertEquals(119,discountedShoppingCart.getDiscountedCartAmount());
	}
	

}
