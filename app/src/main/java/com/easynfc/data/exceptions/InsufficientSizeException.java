package com.easynfc.data.exceptions;

public class InsufficientSizeException extends Exception {

	private int requiredTagSize;
	private int availableTagSize;
	
	public InsufficientSizeException(int required, int available) {

	}

	@Override
	public String getMessage() {
		String message = "Insufficient tag size. required:%s, available: %s";

		return String.format(message, requiredTagSize, availableTagSize);
	}

}
