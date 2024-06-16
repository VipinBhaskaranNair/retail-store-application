/**
 * 
 */
package com.store.invoice.model;




import com.store.invoice.constants.ProductTypes;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	private String productId;
	private String productName;
	@NotNull(message="productType cannot be null")
	private ProductTypes productType;
	@NotNull(message="unitPrice cannot be null")
	private double unitPrice;

}
