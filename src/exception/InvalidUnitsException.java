package exception;

import exception.message.ExceptionMessages;

public class InvalidUnitsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6064893939537644485L;

	/**
	 * 
	 */
	public InvalidUnitsException() {
		super(ExceptionMessages.INVALID_UNITS);
	}

	/**
	 * @param message
	 */
	public InvalidUnitsException(String message) {
		super(message);
	}

}
