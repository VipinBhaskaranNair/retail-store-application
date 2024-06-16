package com.store.invoice.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {	

	@Valid
	private Product product;
	@NotNull(message="quantity cannot be null")
	private double quantity;

}
