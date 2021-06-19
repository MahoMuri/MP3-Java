/**
 * 
 */
package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.InvalidCourseException;
import exception.InvalidStudentIDException;
import exception.InvalidStudentNameException;
import exception.InvalidUnitsException;
import exception.InvalidYearException;
import utilities.SQLStatements;
import utilities.Security;

/**
 * @author paolo
 *
 */
public class StudentBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1004188305941902417L;

	// Initialization of variables
	String studentID = "";
	String lastName = "";
	String firstName = "";
	String course = "";
	int yrLvl = 0;
	int unitsEnrolled = 0;

	// Private Static variables
	public static int totalStudents = 0;
	public static int totalBSCS = 0;
	public static int totalBSIT = 0;
	public static int totalBSIS = 0;

	// Generated Getters and Setters
	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		if (!studentID.matches("20[0-2][0-9]-[0-9]{6}")) {
			throw new InvalidStudentIDException();
		}
		this.studentID = studentID;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if (!lastName.matches("^^\\w(( ?[A-Za-z-]+)?)+$")) {
			throw new InvalidStudentNameException();
		}
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (!firstName.matches("^^\\w(( ?[A-Za-z-]+)?)+$")) {
			throw new InvalidStudentNameException();
		}
		this.firstName = firstName;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		if (!course.matches("^(BSCS|BSIS|BSIT)$")) {
			throw new InvalidCourseException();
		}
		this.course = course;
	}

	public int getYrLvl() {
		return yrLvl;
	}

	public void setYrLvl(int yrLvl) {
		if (yrLvl > 4 || yrLvl < 1) {
			throw new InvalidYearException();
		}
		this.yrLvl = yrLvl;
	}

	public int getUnitsEnrolled() {
		return unitsEnrolled;
	}

	public void setUnitsEnrolled(int unitsEnrolled) {
		if (unitsEnrolled > 21 || unitsEnrolled < 12) {
			throw new InvalidUnitsException();
		}
		this.unitsEnrolled = unitsEnrolled;
	}

	// JDBC Operations

	// Connection handler
	private Connection getConnection() throws SQLException {
		Connection connection = null;

		try {
			// perform JDBC driver enrollment
			Class.forName("com.mysql.jdbc.Driver");

			// DriverManager configuration for connection initialization
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/seg21-db", "root", "");

		} catch (Exception e) {
			SQLException sqle = new SQLException("Error Establishing Connection: " + e.getMessage());
			throw sqle;
		}

		return connection;
	}

	// Save method, save the record to the database
	public boolean save() {

		boolean saved = false;
		Connection connection = null;

		try {
			connection = getConnection();

			if (connection != null) {
				
				ResultSet record = findOne(studentID);
				
				if (record.next() == false) {
					/**
					 * id = 1 name = 2 course = 3 year level = 4 units = 5
					 */
					
					PreparedStatement statement = connection.prepareStatement(SQLStatements.INSERT_ONE);
					
					statement.setString(1, Security.encrypt(studentID));
					statement.setString(2, Security.encrypt(String.format("%s, %s", this.lastName, this.firstName)));
					statement.setString(3, this.course);
					statement.setInt(4, this.yrLvl);
					statement.setInt(5, this.unitsEnrolled);
					
					statement.executeUpdate();
					saved = true;
				}

			}
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
			try {
				connection.rollback();
			} catch (SQLException sqle1) {
				System.err.println(sqle1.getMessage());
			}
		}
		return saved;
	}

	// Find Methods
	public ResultSet findAll() {
		ResultSet records = null;

		try {
			Connection connection = getConnection();

			if (connection != null) {

				PreparedStatement statement = connection.prepareStatement(SQLStatements.FIND_ALL);

				records = statement.executeQuery();
			}
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
		}
		
		// Invoke student count function for counting
		getStudentCount();
		return records;
	}

	public ResultSet findOne(String studentID) {
		ResultSet record = null;

		try {
			Connection connection = getConnection();

			if (connection != null) {

				PreparedStatement statement = connection.prepareStatement(SQLStatements.FIND_ONE);

				statement.setString(1, Security.encrypt(studentID));

				record = statement.executeQuery();
			}
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
		}
		return record;

	}

	public ResultSet findAllByCourse(String course) {
		ResultSet records = null;

		try {
			Connection connection = getConnection();

			if (connection != null) {

				PreparedStatement statement = connection.prepareStatement(SQLStatements.FIND_ALL_BY_COURSE);

				statement.setString(1, course);

				records = statement.executeQuery();
			}
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
		}
		// Invoke student count function for counting
		getStudentCount();
		return records;

	}

	// Delete Methods
	public boolean deleteAll() {
		boolean deleted = false;

		try {
			Connection connection = getConnection();

			if (connection != null) {

				PreparedStatement statement = connection.prepareStatement(SQLStatements.DELETE_ALL);

				int rows = statement.executeUpdate();

				if (rows > 0) {
					deleted = true;					
				}
			}
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
		}
		return deleted;
	}

	public boolean deleteOne(String studentID) {
		boolean deleted = false;

		try {
			Connection connection = getConnection();

			if (connection != null) {

				PreparedStatement statement = connection.prepareStatement(SQLStatements.DELETE_ONE);

				statement.setString(1, Security.encrypt(studentID));
				int rows = statement.executeUpdate();

				if (rows > 0) {
					deleted = true;					
				}
			}
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
		}
		return deleted;

	}
	
	// Function to Count Students
	private void getStudentCount() {
		ResultSet record = null;
		PreparedStatement statement = null;
		
		try {
			Connection connection = getConnection();
			
			if(connection != null) {
				
				// Get All Students
				statement = connection.prepareStatement(SQLStatements.COUNT_ALL);
				record = statement.executeQuery();
				record.next();
				totalStudents = record.getInt("totalStudents");
				
				// Get All BS CS Students
				statement = connection.prepareStatement(SQLStatements.COUNT_BSCS);
				record = statement.executeQuery();
				record.next();
				totalBSCS = record.getInt("totalBSCS");
				
				// Get All BS IS Students
				statement = connection.prepareStatement(SQLStatements.COUNT_BSIS);
				record = statement.executeQuery();
				record.next();
				totalBSIS = record.getInt("totalBSIS");
				
				// Get All BS IT Students
				statement = connection.prepareStatement(SQLStatements.COUNT_BSIT);
				record = statement.executeQuery();
				record.next();
				totalBSIT = record.getInt("totalBSIT");
				
			}
			else {
				System.err.println("\nCould not get list data... skipping for now.\n");
			}
			
		} catch(SQLException sqle) {
			System.err.println(sqle.getMessage());
		}
		
	}
	
	public boolean isEmpty() {
		boolean isEmpty = false;
		try {
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(SQLStatements.COUNT_ALL);
			ResultSet record = statement.executeQuery();
			
			record.next();
			if (record.getInt("totalStudents") == 0) {
				isEmpty = true;
			}
			
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
		}
		
		return isEmpty;
	}

}
