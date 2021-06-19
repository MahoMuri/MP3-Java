package exception;

import exception.message.ExceptionMessages;

public class InvalidCourseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9034968762958569618L;

	public InvalidCourseException() {
		super(ExceptionMessages.INVALID_COURSE);
	}
	
	/**
	 * @param message
	 */
	public InvalidCourseException(String message) {
		super(message);
	}

}
