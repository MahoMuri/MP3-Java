/**
 * 
 */
package controller;

import utilities.Inputs;
import view.Display;

/**
 * @author Paolo
 *
 */
public class Main {

	/**
	 * @param args
	 */
	
	/**
	 * [A]-dd Student
	 * [L]-ist Students
	 * [S]-earch Student
	 * [D]-elete Student
	 * [R]-eport Generator
	 * [P]-urge
	 * [Q]-uit
	 */
	public static void main(String[] args) {
		boolean sessionOver = false;
		do {
			Display.printMenu();
			String choice = Inputs.readString("Select option");

			switch (choice.toLowerCase()) {
			case "a":
				StudentRDMSController.addStudent();
				break;
			case "l":
				StudentRDMSController.listStudents();
				break;
			case "s":
				StudentRDMSController.searchStudent();
				break;
			case "d":
				StudentRDMSController.deleteStudent();
				break;
			case "r":
				StudentRDMSController.reportGenerator();
				break;
			case "p":
				StudentRDMSController.purge();
				break;
			case "q":
				sessionOver = StudentRDMSController.quit();
				break;
			default:
				System.err.println("\n\nInvalid Input! Please try again\n\n");
				break;
			}

		} while (!sessionOver);
		
		System.out.println("\nProgram terminated. Thank you for using the system!");
	}

}
