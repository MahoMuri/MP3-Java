package exception;

import exception.message.ExceptionMessages;

public class InvalidStudentNameException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8125108639880491881L;

	public InvalidStudentNameException() {
		super(ExceptionMessages.INVALID_STUDENT_NAME);
	}

	/**
	 * @param message
	 */
	public InvalidStudentNameException(String message) {
		super(message);
	}


}
