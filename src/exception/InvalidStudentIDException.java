package exception;

import exception.message.ExceptionMessages;

public class InvalidStudentIDException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1143217389894583801L;

	public InvalidStudentIDException() {
		super(ExceptionMessages.INVALID_STUDENT_ID);
	}

	/**
	 * @param message
	 */
	public InvalidStudentIDException(String message) {
		super(message);
	}


	
}
