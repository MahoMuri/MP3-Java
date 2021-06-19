package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import exception.*;
import exception.message.ExceptionMessages;
import model.StudentBean;
import utilities.Inputs;
import view.Display;

public class StudentRDMSController {
	
	/**
	 * [A]-dd Student
	 * [L]-ist Students
	 * [S]-earch Student
	 * [D]-elete Student
	 * [R]-eport Generator
	 * [P]-urge
	 * [Q]-uit
	 */
	
	// Static Admin account and login method
	private static String adminUsername = "admin";
	private static String adminPassword = "qwerty123";

	private static boolean adminLogin() {
		boolean isAdmin = false;

		String inputAccount = Inputs.readString("\nEnter administrator account");
		String inputPassword = Inputs.readString("Enter administrator password");

		if (inputAccount.equals(adminUsername) && inputPassword.equals(adminPassword)) {
			System.out.println("\nLogin success! Welcome Admin!\n");
			isAdmin = true;
		}

		return isAdmin;
	}

	// Main functions for the program
	public static void addStudent() {
		// TODO: Add guard clauses
		StudentBean newStudent = new StudentBean();

		// Student ID validation
		while (true) {
			try {
				newStudent.setStudentID(Inputs.readString("Enter Student ID"));
				break;
			} catch (InvalidStudentIDException iside) {
				System.err.println(iside.getMessage());
			}
		}
		
		// Student name validations
		while (true) {
			try {
				newStudent.setLastName(Inputs.readString("Enter Student Last Name"));			
				break;
			} catch (InvalidStudentNameException istne) {
				System.err.println(istne.getMessage());
			}
		}
		
		while (true) {
			try {
				newStudent.setFirstName(Inputs.readString("Enter Student First Name"));
				break;
			} catch (InvalidStudentNameException istne) {
				System.err.println(istne.getMessage());
			}
			
		}
		
		// Student course validation
		while (true) {
			try {
				newStudent.setCourse(Inputs.readString("Enter Student Course"));
				break;
			} catch (InvalidCourseException ice) {
				System.err.println(ice.getMessage());
			}
			
		}
		
		// Student year level validation
		while (true) {
			try {
				newStudent.setYrLvl(Inputs.readInt("Enter Student Year Level"));
				break;
			} catch (InvalidYearException iye) {
				System.err.println(iye.getMessage());
			}
		}
		
		// Student unit validation
		while (true) {
			try {
				newStudent.setUnitsEnrolled(Inputs.readInt("Enter Number of Units Enrolled"));
				break;
			} catch (InvalidUnitsException iue) {
				System.err.println(iue.getMessage());
			}
			
		}

		// Saves the record
		if (newStudent.save()) {
			System.out.println("\nRecord sucessfully saved!\n\n");
		} else {
			System.err.println("\nError on save! Student ID " + newStudent.getStudentID() + " already exists! See record below for details: \n");
			ResultSet record = new StudentBean().findOne(newStudent.getStudentID());
			
			try {
				if (record.next() != false) {
					Display.printRecord(record);				
				} else {
					System.err.println("\nSomething went wrong, please ask IT for assistance!\n\n");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	public static void listStudents() {
		ResultSet records = new StudentBean().findAll();

		try {
			if (records.next() == false) {
				System.err.println("\n\nDatabase is Empty!\n\n");
			} else {
				Display.printAllRecords(records, true);				
			}
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
		}
	}

	public static void searchStudent() {
		if (new StudentBean().isEmpty()) {
			System.err.println("\n\nDatabase is Empty!\n\n");
		} else {
			String studentID = Inputs.readString("Enter Student ID");
			
			System.out.println("\nPlease wait... searching for student ID " + studentID);
			
			ResultSet record = new StudentBean().findOne(studentID);
			
			try {
				if (record.next() != false) {
					// Print the record details
					System.out.println("\nRecord found!\n");
					Display.printRecord(record);
				} else {
					System.err.println("Error! Record not found!\n\n");
				}
			} catch (SQLException sqle) {
				System.err.println(sqle.getMessage());
			}
		}
	}

	public static void deleteStudent() {
		if (adminLogin()) {
			if (new StudentBean().isEmpty()) {
				System.err.println("\n\nDatabase is Empty! Logging you out...\n\n");
			} else {
				String studentID = Inputs.readString("Enter Student ID");

				System.out.println("\nPlease wait... searching for student record " + studentID);

				boolean isDeleted = new StudentBean().deleteOne(studentID);

				if (isDeleted) {
					System.out.println("\nRecord found and deleted!\n");
				} else {
					System.err.println("\nError on record search - Student ID " + studentID + " not found! Record cannot be deleted!\n\n");
				}
			}
		} else {
			System.err.println("\nInvalid Administrator Credentials! Please try again!\n");
		}
	}

	public static void reportGenerator() {
		if (adminLogin()) {
			if (new StudentBean().isEmpty()) {
				System.err.println("\n\nDatabase is Empty! Logging you out...\n\n");
			} else {
				System.out.println("\nReport Generator Facility\n");
				String course = Inputs.readString("Enter course code").toUpperCase();
				
				// Course validation
				while (!course.matches("^(BSCS|BSIS|BSIT)$")) {
					System.err.println(ExceptionMessages.INVALID_COURSE);
					course = Inputs.readString("Enter course code").toUpperCase();
				}
				
				ResultSet records = new StudentBean().findAllByCourse(course);
				
				try {
					if (records.next() != false) {
						if (course.equalsIgnoreCase("bscs")) {
							System.out.println("\n\nTotal number of CS students: " + StudentBean.totalBSCS);						
						} else if (course.equalsIgnoreCase("bsis")) {
							System.out.println("\n\nTotal number of IS students: " + StudentBean.totalBSIS);
						} else if (course.equalsIgnoreCase("bsit")) {
							System.out.println("\n\nTotal number of IT students: " + StudentBean.totalBSIT);
						}
						
						Display.printAllRecords(records, false);
						
					} else {
						System.err.println("\nNo " + course + " students found!");
					}
				} catch (SQLException sqle) {
					System.err.println(sqle.getMessage());
				}
			}
		} else {
			System.err.println("\nInvalid Administrator Credentials! Please try again!\n");
		}
	}

	public static void purge() {
		boolean deleted = false;
		if (adminLogin()) {
			if (new StudentBean().isEmpty()) {
				System.err.println("\n\nDatabase is Empty! Logging you out...\n\n");
			} else {
				String choice = Inputs.readString("Are you sure you want to purge all records?[y/N]");
				
				// Input validation
				while (!deleted) {
					if (choice.equalsIgnoreCase("y")) {
						System.out.println("\n\nPlease wait. Deleting Records....");
						deleted = new StudentBean().deleteAll();
						
						if (deleted) {
							System.out.println("\n\nALL records successfully deleted.\n\n");
						} else {
							System.err.println("\n\nDatabase is empty!\n\n");
							break;
						}
					} else if (choice.equalsIgnoreCase("n")){
						break;
					} else {
						System.err.println("\nInvalid Input! Please try again!\n");
						choice = Inputs.readString("Are you sure you want to quit?[y/N]");
					}
				}
			}
		} else {
			System.err.println("\nInvalid Administrator Credentials! Please try again!\n");
		}
	}

	public static boolean quit() {
		boolean willQuit = false;
		if (adminLogin()) {
			String choice = Inputs.readString("Are you sure you want to quit?[y/N]");
			while (!willQuit) {
				if (choice.equalsIgnoreCase("y")) {
					willQuit = true;					
				} else if (choice.equalsIgnoreCase("n")){
					break;
				} else {
					System.err.println("\nInvalid Input! Please try again!\n");
					choice = Inputs.readString("Are you sure you want to quit?[y/N]");
				}
			}
		} else {
			System.err.println("\nInvalid Administrator Credentials! Please try again!\n");
		}
		return willQuit;
	}

}
