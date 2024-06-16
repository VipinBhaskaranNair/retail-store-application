package com.store.invoice.controller;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.invoice.constants.CustomerTypes;
import com.store.invoice.constants.ProductTypes;
import com.store.invoice.exception.InvalidCustomerTypeException;
import com.store.invoice.model.CartItem;
import com.store.invoice.model.CustomerDetails;
import com.store.invoice.model.DiscountedShoppingCart;
import com.store.invoice.model.Product;
import com.store.invoice.model.ShoppingCart;
import com.store.invoice.service.DiscountedShoppingCartService;

@WebMvcTest(controllers = DiscountedShoppingCartController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class DiscountedShoppingCartControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DiscountedShoppingCartService discountedShoppingCartService;
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	DiscountedShoppingCartController discountedShoppingCartController;
	
	CustomerDetails customerDetails;
	List<CartItem> cartItems = new ArrayList<>();
	ShoppingCart shoppingCart;
	
	@BeforeEach
	void setup() {
		customerDetails = new CustomerDetails("CU001", "Tom", CustomerTypes.EMPLOYEE, LocalDateTime.of(2018, 1, 22, 3, 15));
		Product product1 = new Product("P001","Shirt",ProductTypes.ELECTRONICS,1200.00d);
		Product product2 = new Product("P002","Onion",ProductTypes.GROCERY,2.00d);
		CartItem cartItem1 = new CartItem(product1, 1);
		CartItem cartItem2 = new CartItem(product2, 2);
		cartItems = new ArrayList<>();
		cartItems.add(cartItem1);
		cartItems.add(cartItem2);
		
		
	}
	
	@Test
	void testCalculteDiscountedInvoiceSuccess() throws InvalidCustomerTypeException {
		shoppingCart = new ShoppingCart("C001", customerDetails, LocalDateTime.of(2024, 06, 16, 3, 15), cartItems);
		DiscountedShoppingCart discountedShoppingCart = new DiscountedShoppingCart(shoppingCart, 12.0);
		Mockito.when(discountedShoppingCartService.calculateDiscount(Mockito.any())).thenReturn(discountedShoppingCart);
		assertEquals(12.0,discountedShoppingCartController.calculteDiscountedInvoice(shoppingCart).getDiscountedCartAmount());
	}
	
	
	
	@Test
	void testCalculteDiscountedInvoice() throws Exception{
		shoppingCart = new ShoppingCart("C001", customerDetails, LocalDateTime.of(2024, 06, 16, 3, 15), cartItems);
		System.out.println(mapper.writeValueAsString(shoppingCart));
		ResultActions response = mockMvc.perform(post("/api/discountedInvoice").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(shoppingCart)));
		response.andExpect(MockMvcResultMatchers.status().isOk());
	}	
	
	@Test
	void testCalculteDiscountedInvoiceInvalidCustomerType() throws Exception{
		customerDetails = new CustomerDetails("CU001", "Tom", null, LocalDateTime.of(2018, 1, 22, 3, 15));
		shoppingCart = new ShoppingCart("C001", customerDetails, LocalDateTime.of(2024, 06, 16, 3, 15), cartItems);
		System.out.println(mapper.writeValueAsString(shoppingCart));
		ResultActions response = mockMvc.perform(post("/api/discountedInvoice").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(shoppingCart)));
		response.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	

}
