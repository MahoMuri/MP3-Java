package view;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.StudentBean;
import utilities.Security;

public class Display {

	public static void printMenu() {
		System.out.println("=========================");
		System.out.println("iACADEMY");
		System.out.println("Student Management System\n");
		System.out.println("[A]-dd Student\r" + "[L]-ist Students\r\n" + "[S]-earch Student\r\n"
				+ "[D]-elete Student\r\n" + "[R]-eport Generator\r\n" + "[P]-urge\r\n" + "[Q]-uit");

	}

	public static void printAllRecords(ResultSet records, boolean printStats) {
		System.out.println("\nList of Students Enrolled");
		System.out.println("=========================\n");
		
		try {
			do {
				
				System.out.println("\nID: " + Security.decrypt(records.getString("id")));
				System.out.println("Name: " + Security.decrypt(records.getString("name")));
				System.out.println("Course: " + records.getString("course"));
				System.out.println("Year Level:" + records.getInt("yearLevel"));
				System.out.println("Units Enrolled: " + records.getInt("unitsEnrolled") + "\n");
				
			} while (records.next());
			
			if (printStats) {
				System.out.println("\n=========================");
				System.out.println("Total Students Enrolled: " + StudentBean.totalStudents);
				System.out.println("\n\n\nTotal # of CS: " + StudentBean.totalBSCS);
				System.out.println("Total # of IS: " + StudentBean.totalBSIS);
				System.out.println("Total # of IT: " + StudentBean.totalBSIT + "\n\n");				
			}
			
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
		}
	}
	
	public static void printRecord(ResultSet record) {
		try {
			System.out.println("\nID: " + Security.decrypt(record.getString("id")));
			System.out.println("Name: " + Security.decrypt(record.getString("name")));
			System.out.println("Course: " + record.getString("course"));
			System.out.println("Year Level:" + record.getInt("yearLevel"));
			System.out.println("Units Enrolled: " + record.getInt("unitsEnrolled") + "\n");
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
		}
	}
}
