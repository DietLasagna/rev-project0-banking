/**
 * BankingApplication.java
 * 
 * Version 0.5
 * 
 * Mar 04, 2022
 * 
 * Apache-2.0 License 
 */
package bankingapp;

import java.util.*;

/**
 * Executable class for Project0. Contains the collections of objects to be serialized
 * and handles user log in and first time registration. Has several utility methods for
 * reading and verifying user input.
 * 
 * @version 0.5 04 Mar 2022
 * 
 * @author Michael Adams
 *
 */
public class BankingApplication {
	
	/** Sets whether user will need to register as a new user*/
	static public boolean isNewUser = true;
	/** HashMap of all Customer records: Key is username:String, Value is user:Customer */
	static public HashMap<String, Customer> CustomerMap;
	/** HashMap of all Employee records: Key is username:String, Value is user:Employee */
	static public HashMap<String, Employee> EmployeeMap;
	/** HashMap of all Bank Administrator records: Key is username:String,
			Value is user:BankAdmin */
	static public HashMap<String, BankAdmin> BankAdminMap;

	/**
	 * Executable method of Project0. Parameter is unused.
	 * @param args
	 */
	public static void main(String[] args) {
		
		/**
		 * On application start, records stored in files are deserialized. An appropriate 
		 * HashMap is returned, regardless whether records were loaded or not, so no 
		 * variable will remain null (but may be empty).
		 */
		try {
			
			CustomerMap  = Marshal.deserialize("./src/CustomerData.ser", Customer.class);
			EmployeeMap  = Marshal.deserialize("./src/EmployeeData.ser", Employee.class);
			BankAdminMap = Marshal.deserialize("./src/BankAdminData.ser", BankAdmin.class);
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			System.out.println("DID NOT DESERIALIZE"); // DEBUG
			return;
			
		}
		
		/**
		 * A single Scanner for STDIN is created in a try-with-resources block and passed 
		 * to functions as needed. 
		 */
		try (Scanner s = new Scanner(System.in)) {
			
			do {
				
				/** Holds value of user object (Customer|Employee|BankAdmin|null)*/
				UserAbstract currentUser = loginScreen(s);
				
				if(currentUser == null) {
					
					return;
					
				} else {
					
					/** Passes Scanner to object for user behavior based on Class */
					currentUser.menu(s);
				
				}
				
				/**
				 * Reaching this point means user has successfully logged out of session.
				 * 
				 * On session ending, current object states are serialized into files. 
				 * If a file is expected but not found, a new empty file is created.
				 */
				try {
					
					Marshal.serialize("./src/CustomerData.ser", CustomerMap);
					Marshal.serialize("./src/EmployeeData.ser", EmployeeMap);
					Marshal.serialize("./src/BankAdminData.ser", BankAdminMap);
					
				} catch (Exception ex) {
					
					ex.printStackTrace();
					System.out.println("DID NOT DESERIALIZE"); // DEBUG
					
				}
				
				System.out.println("\nNew login?");
			
			} while(promptUser(s, "yn").equalsIgnoreCase("y"));
			
		} catch (Exception ex) {
		
			System.out.println("Error. Try again later.");
			ex.printStackTrace();
		
		}
		
		/** Reaching this point means the program will end */

	}
	
	/**
	 * Prompts user to log in or register as needed.
	 * @param s An open STDIN Scanner
	 * @return The user object who is logged in this session, or null if log in fails
	 */
	public static UserAbstract loginScreen(Scanner s) {
		
		String username;
		String password;
		/** Holds user object of who is logging in */
		UserAbstract currentUser;
		/** Controls moving to next step in process */
		boolean isNotReady = false;
		
		System.out.println("\t WELCOME TO BANK \t");
		System.out.println("Enter Login credentials or register");
		
		do {
			
			username = readUserInput(s, "Please type username:").trim().toLowerCase();
			isNotReady = false;
			
			if(username.length() < 4) {
				
				System.out.println("Username must be at least 4 characters.");
				isNotReady = true;
				
			}
			
			if(username.contains(" ")) {

				System.out.println("Username cannot contain spaces.");
				isNotReady = true;
				
			}
			
		} while(isNotReady);
		
		/** Determine if user is existing or new user based on user given username */
		if(	CustomerMap.containsKey(username) ||
			EmployeeMap.containsKey(username) ||
			BankAdminMap.containsKey(username)) {
			
			System.out.println("- - - Login for existing users - - -");
			isNewUser = false;
			
		} else {
			
			System.out.println("- - - Register new user - - -");
			isNewUser = true;
			
		}
			
		do {
			
			password = readUserInput(s, "Please enter your password:").trim().toLowerCase();
			isNotReady = false;
			
			if(password.length() < 4) {
				
				System.out.println("Password must be at least 4 characters.");
				isNotReady = true;
				
			}
			
			if(password.contains(" ")) {

				System.out.println("Password cannot contain spaces.");
				isNotReady = true;
				
			}
			
		} while(isNotReady);
		
		/** Register new user or process log in for existing users */
		if(isNewUser) {
			
			System.out.println(
					"Are you a new [C]ustomer, [E]mployee, or Bank [A]dministrator?");
			
			switch(promptUser(s, "cea")) {
			
			// New Customer
			case "c":
				System.out.println("Welcome, new customer! Please fill out this form.");
				currentUser = new Customer(username, password,
						readUserInput(s, "Please type full name:"),
						readUserInput(s, "Please type mailing address:")
						);
				CustomerMap.put(username, (Customer) currentUser);
				break;
				
			// New Employee	
			case "e":
				currentUser = new Employee(username, password);
				EmployeeMap.put(username, (Employee) currentUser);
				break;
				
			// New Bank Administrator	
			case "a":
				currentUser = new BankAdmin(username, password);
				BankAdminMap.put(username, (BankAdmin) currentUser);
				break;
				
			default:
				System.out.println("\t Invalid Login \t");
				System.out.println("----------------------------------------");
				return null;
				
			}
			
		} else {
			
			/** Check each user collection for matching credentials */
			if(			CustomerMap.containsKey(username) &&
						CustomerMap.get(username).getPassword() != password) {
				
				currentUser = CustomerMap.get(username);
			
			} else if(	EmployeeMap.containsKey(username) &&
						EmployeeMap.get(username).getPassword() != password) {
				
				currentUser = EmployeeMap.get(username);
			
			} else if(	BankAdminMap.containsKey(username) &&
						BankAdminMap.get(username).getPassword() != password) {
			
				currentUser = BankAdminMap.get(username);
			
			} else {
				
			System.out.println("\t Invalid Login \t");
			System.out.println("----------------------------------------");
			return null;
			
			}
			
		}
		
		return currentUser; // Customer, Employee, or BankAdmin object, or null
		
	}
	
	/**
	 * Prints a prompt and gets appropriate user response. Repeats prompt until user gives
	 * usable input.
	 * @param s An open STDIN Scanner
	 * @param message String to be printed as user prompt
	 * @return User typed response as a String
	 */
	public static String readUserInput(Scanner s, String message) {

		System.out.println(message);
		String userInput = "";
		
		try {
			
			userInput = s.nextLine();
			
		}
		
		catch (Exception ex) {

			ex.printStackTrace();
			readUserInput(s, "Invaild input, please try again:");
			
		}
		
		return userInput;
		
	}
	
	/**
	 * Prints a prompt and gets appropriate user input as a dollar amount. Repeats 
	 * prompt until user gives usable input.
	 * @param s An open STDIN Scanner
	 * @return Dollar amount specified by user
	 */
	public static double readUserAmount(Scanner s) {
		
		System.out.println("Enter dollar amount:");
		double userInput;
		
		try {
			
			userInput = Math.abs(s.nextDouble());
			
			if(s.hasNextLine()) {

				s.nextLine();
				
			}
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			System.out.println("Invalid input");
			userInput = Math.abs(readUserAmount(s));
			
		}
		
		if(userInput < 0.01d) {

			System.out.println("Invalid input");
			userInput = Math.abs(readUserAmount(s));
		
		}
		
		return Math.floor(userInput * 100) / 100;
		
	}
	
	/**
	 * Prints a prompt with input options and gets appropriate user input. Repeats prompt
	 * until user gives input which is one of the options given.
	 * @param s An open STDIN Scanner
	 * @param options A String of all characters allowed as inputs
	 * @return String of single character
	 * 
	 * @see readUserInput
	 */
	public static String promptUser(Scanner s, String options) {
		
		String userChoice;
		
		userChoice = readUserInput(s, "Enter one of (" + options + "):").trim();
		
		if(!options.contains(userChoice) || userChoice.length() > 1) {
			
			System.out.println("Option not available");
			userChoice = promptUser(s, options);
			
		}
		
		return userChoice;
	
	}
	
}