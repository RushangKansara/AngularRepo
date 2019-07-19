package com.pioneer.userreg.exception;

import com.pioneer.userreg.domain.UserDTO;

public class CustomErrorType extends UserDTO{
	
	private String errorMessage;
	
	public CustomErrorType(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}

}
