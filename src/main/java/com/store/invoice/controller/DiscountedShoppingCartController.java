package com.store.invoice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.invoice.exception.InvalidCustomerTypeException;
import com.store.invoice.model.DiscountedShoppingCart;
import com.store.invoice.model.ShoppingCart;
import com.store.invoice.service.DiscountedShoppingCartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class DiscountedShoppingCartController {
	
	Logger logger = LoggerFactory.getLogger(DiscountedShoppingCartController.class);

	@Autowired
	private DiscountedShoppingCartService discountedShoppingCartService;
	

	@PostMapping("/discountedInvoice")
	public DiscountedShoppingCart calculteDiscountedInvoice(@RequestBody @Valid ShoppingCart shoppingCart) throws InvalidCustomerTypeException{
		logger.info("New invoice discount calculation request received with cart id: {}" , shoppingCart.getCartId());
		return discountedShoppingCartService.calculateDiscount(shoppingCart);
	}

}
