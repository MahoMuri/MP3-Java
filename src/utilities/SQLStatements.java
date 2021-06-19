/**
 * 
 */
package utilities;

/**
 * @author paolo
 *
 */
public interface SQLStatements {

	// Inserting one record to the Database
	String INSERT_ONE = "INSERT INTO Student (id, name, course, yearLevel, unitsEnrolled) VALUES (?,?,?,?,?)";

	// List all records in the Database
	String FIND_ALL = "SELECT * FROM Student";

	// Find one record in the Database
	String FIND_ONE = "SELECT * FROM Student WHERE id = ?";

	// Find one record by course in the Database
	String FIND_ALL_BY_COURSE = "SELECT * FROM Student WHERE course = ?";

	// Find one record in the Database
	String DELETE_ONE = "DELETE FROM Student WHERE id = ?";

	// Find all records in the Database
	String DELETE_ALL = "DELETE FROM Student";

	// Student Counters
	String COUNT_ALL = "SELECT COUNT(*) AS 'totalStudents' FROM Student";
	String COUNT_BSCS = "SELECT COUNT(course) AS 'totalBSCS' FROM Student WHERE course REGEXP 'CS$';";
	String COUNT_BSIS = "SELECT COUNT(course) AS 'totalBSIS' FROM Student WHERE course REGEXP 'IS$';";
	String COUNT_BSIT = "SELECT COUNT(course) AS 'totalBSIT' FROM Student WHERE course REGEXP 'IT$';";

}
