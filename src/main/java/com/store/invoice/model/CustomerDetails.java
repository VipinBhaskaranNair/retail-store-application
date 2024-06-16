package com.store.invoice.model;

import java.time.LocalDateTime;

import com.store.invoice.constants.CustomerTypes;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetails {

	private String customerId;
	private String customerName;
	@NotNull(message = "customerType cannot be null")
	private CustomerTypes customerType;
	@NotNull(message = "customerSince cannot be null")
	private LocalDateTime customerSince;

}
