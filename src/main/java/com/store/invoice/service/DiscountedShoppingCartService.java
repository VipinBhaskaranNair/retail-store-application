package com.store.invoice.service;

import com.store.invoice.exception.InvalidCustomerTypeException;
import com.store.invoice.model.DiscountedShoppingCart;
import com.store.invoice.model.ShoppingCart;

public interface DiscountedShoppingCartService {

	public DiscountedShoppingCart calculateDiscount(ShoppingCart shoppingCart) throws InvalidCustomerTypeException;

}
