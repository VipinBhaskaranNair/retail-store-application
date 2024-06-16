package com.store.invoice.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.store.invoice.constants.Constants;
import com.store.invoice.constants.CustomerTypes;
import com.store.invoice.constants.ProductTypes;
import com.store.invoice.exception.InvalidCustomerTypeException;
import com.store.invoice.model.CartItem;
import com.store.invoice.model.CustomerDetails;
import com.store.invoice.model.DiscountedShoppingCart;
import com.store.invoice.model.ShoppingCart;
import com.store.invoice.service.DiscountedShoppingCartService;

@Service
public class DiscountedShoppingCartServiceImpl implements DiscountedShoppingCartService {
	
	Logger logger = LoggerFactory.getLogger(DiscountedShoppingCartServiceImpl.class);

	@Value("${employee.discount.percentage}")
	private double employeeDiscount;

	@Value("${affiliate.discount.percentage}")
	private double affiliateDiscount;

	@Value("${longterm.discount.percentage}")
	private double longTermDiscount;

	@Value("${cash.discount.value}")
	private double cashDiscount;

	@Override
	public DiscountedShoppingCart calculateDiscount(ShoppingCart shoppingCart) throws InvalidCustomerTypeException {
		double finalItemPrice = 0;
		double cartItemPrice = 0;
		double cartAmount = 0;
		CustomerDetails customerDetails = shoppingCart.getCustomerDetails();
		// Get the discount percentage for customer type
		double discountPercentage = getUserDiscount(customerDetails.getCustomerType(),
				customerDetails.getCustomerSince());

		for (CartItem item : shoppingCart.getCartItems()) {
			cartItemPrice = item.getProduct().getUnitPrice() * item.getQuantity();
			if (item.getProduct().getProductType() != ProductTypes.GROCERY) {
				finalItemPrice = cartItemPrice - (cartItemPrice * discountPercentage);
			} else {
				finalItemPrice = cartItemPrice;
			}
			cartAmount += finalItemPrice;
		}
		// Deduct the $5 cash discount for bill amount for every $100
		cartAmount -= (Math.floorDiv((int) cartAmount, Constants.CASH_DISCOUNT_PER_AMOUNT)) * cashDiscount;
		logger.info("Discounted cart amount for customer is {}" , cartAmount);
		return new DiscountedShoppingCart(shoppingCart, cartAmount);
	}

	private double getUserDiscount(CustomerTypes customerType, LocalDateTime userSince) throws InvalidCustomerTypeException {
		double discountPercentage = 0.0d;
		if(null == customerType)
			throw new InvalidCustomerTypeException("Customer type is not configured");
		switch (customerType) {
		case AFFILIATE:
			discountPercentage = affiliateDiscount;
			break;
		case LONG_TERM_CUSTOMER:
			if (ChronoUnit.YEARS.between(userSince, LocalDateTime.now()) > Constants.LONG_TERM_YEARS) {
				discountPercentage = longTermDiscount;
			}
			break;
		case EMPLOYEE:
			discountPercentage = employeeDiscount;
			break;					
		}
		logger.info("Discount percentage calculated for customer is {}" , discountPercentage);
		return discountPercentage;
	}
}
