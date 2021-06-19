package exception.message;

public interface ExceptionMessages {

	String INVALID_STUDENT_ID = "\nInvalid Student ID! Format is:(20**-******).\n";
	
	String INVALID_STUDENT_NAME = "\nTnvalid Student Name! Must not include numbers of hanging spaces.\n";
	
	String INVALID_COURSE = "\nInvalid Course! Avaiable courses are: BSCS, BSIS & BSIT!\n";
	
	String INVALID_YEAR = "\nInvalid year level! Must be between 1-4!\n";
	
	String INVALID_UNITS = "\nInvalid units! Units must be between 12-21!\n";
			
}
