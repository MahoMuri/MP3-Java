package exception;

import exception.message.ExceptionMessages;

public class InvalidYearException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6911158272825480140L;

	public InvalidYearException() {
		super(ExceptionMessages.INVALID_YEAR);

	}

	/**
	 * @param message
	 */
	public InvalidYearException(String message) {
		super(message);
	}

}
