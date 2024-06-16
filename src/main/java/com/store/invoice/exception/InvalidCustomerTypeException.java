package com.store.invoice.exception;

public class InvalidCustomerTypeException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidCustomerTypeException(String str)  
    {  
        super(str);  
    }  
}  

