/**
 * 
 */
package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author paolo
 *
 */
public class Inputs {
	// use this instead
	private static BufferedReader getReader() {
		BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));

		return inputStream;

	}

	// No String Formatting
	public static String readString(String message) {

		String input = "";

		System.out.print(message + ": ");

		try {
			input = getReader().readLine();
		} catch (IOException exc) {
			System.err.println(exc.getMessage());
		}

		return input;

	}

	public static int readInt(String message) {

		int input = 0;

		System.out.print(message + ": ");

		try {
			input = Integer.parseInt(getReader().readLine());
		} catch (IOException exc) {
			System.err.println(exc.getMessage());
		} catch (NumberFormatException nfe) {
			System.err.println("Invalid Input!\n" + nfe.getLocalizedMessage());

		}

		return input;

	}

	public static double readDouble(String message) {

		double input = 0;

		System.out.print(message + ": ");

		try {
			input = Double.parseDouble(getReader().readLine());
		} catch (IOException exc) {
			System.err.println(exc.getMessage());
		} catch (NumberFormatException nfe) {
			System.err.println("Invalid Input!\n" + nfe.getLocalizedMessage());
		}

		return input;

	}

	// With String Formatting
	public static String readString(String message, String variables) {
		String input = "";

		System.out.printf(message + ": ", variables);

		try {
			input = getReader().readLine();
		} catch (IOException exc) {
			System.err.println(exc.getMessage());
		}

		return input;
	}

	public static int readInt(String message, String variables) {

		int input = 0;

		System.out.printf(message + ": ", variables);

		try {
			input = Integer.parseInt(getReader().readLine());
		} catch (IOException exc) {
			System.err.println(exc.getMessage());
		} catch (NumberFormatException nfe) {
			System.err.println("Invalid Input!\n" + nfe.getLocalizedMessage());
		}

		return input;

	}

	public static double readDouble(String message, String variables) {
		double input = 0;

		System.out.printf(message + ": ", variables);

		try {
			input = Double.parseDouble(getReader().readLine());
		} catch (IOException exc) {
			System.err.println(exc.getMessage());
		} catch (NumberFormatException nfe) {
			System.err.println("Invalid Input!\n" + nfe.getLocalizedMessage());
		}

		return input;
	}
}
