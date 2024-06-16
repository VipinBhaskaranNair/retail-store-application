package com.store.invoice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class DiscountedShoppingCart extends ShoppingCart{
	
	private double discountedCartAmount;
	
	public DiscountedShoppingCart(ShoppingCart shoppingCart, double discountedCartAmount) {
		this.setCartId(shoppingCart.getCartId());
		this.setCartItems(shoppingCart.getCartItems());
		this.setCustomerDetails(shoppingCart.getCustomerDetails());
		this.setShoppingDate(shoppingCart.getShoppingDate());
		this.setDiscountedCartAmount(discountedCartAmount);
	}
	

}
