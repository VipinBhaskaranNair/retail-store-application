package com.store.invoice.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
	
	private String cartId;
	@Valid
	private CustomerDetails customerDetails;
	@NotNull(message="shoppingDate cannot be null")
	private LocalDateTime shoppingDate;
	
	//Stores list of product and quantity as cartItem
	@Valid
	private List<CartItem> cartItems;

}
