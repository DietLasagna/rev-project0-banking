/**
 * 
 */
package bankingapp;

import java.util.*;

/**
 * @author Michael Adams
 *
 */
public class BankingApplication {
	
//	static public boolean isNewData = false;
	static public boolean isNewUser = true;
	static public HashMap<String, Customer> CustomerMap;
	static public HashMap<String, Employee> EmployeeMap;
	static public HashMap<String, BankAdmin> BankAdminMap;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Deserialize data (returns empty UserMap if no file present)
			// Perform first time main() every time
		try {
			
			CustomerMap  = Marshal.deserialize("./src/CustomerData.ser", Customer.class);
			EmployeeMap  = Marshal.deserialize("./src/EmployeeData.ser", Employee.class);
			BankAdminMap = Marshal.deserialize("./src/BankAdminData.ser", BankAdmin.class);
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			System.out.println("DID NOT DESERIALIZE"); // DEBUG
			return;
			
		}
		
		try (Scanner s = new Scanner(System.in)) {
			
			UserAbstract currentUser = loginScreen(s);
			
			if(currentUser == null) {
				return;
			} else if(currentUser instanceof Customer || currentUser instanceof Employee){	// TEMPORARY
				currentUser.menu(s);
			}
		
		// show menu based on login type
			// System.out.println("Is currentUser typeOf Employee? " + (currentUser instanceof Employee)); // DEBUG - is working!
			// actions
			// currentUser.menu(s)
			
			// logout
			
		} catch (Exception ex) {
		
			System.out.println("Error. Try again later.");
			ex.printStackTrace();
		
		}
		
		// Serialize data
//		if(isNewData) {
			
			try {
				
				Marshal.serialize("./src/CustomerData.ser", CustomerMap);
				Marshal.serialize("./src/EmployeeData.ser", EmployeeMap);
				Marshal.serialize("./src/BankAdminData.ser", BankAdminMap);
				
			} catch (Exception ex) {
				
				ex.printStackTrace();
				System.out.println("DID NOT DESERIALIZE"); // DEBUG
				
			}
		
//		}
		
		// end application

	}
	
	/**
	 * With Scanner open, prints message asking for input,
	 * and returns that input
	 * @param s
	 * @param message
	 * @return
	 */
	public static String readUserInput(Scanner s, String message) {

		System.out.println(message);
		String userInput = "";
		
		try {
			
			userInput = s.nextLine(); // need to wait until isNextLine?
			
		}
		
		catch (Exception ex) {

			ex.printStackTrace();
			readUserInput(s, "Invaild input, please try again:");
			
		}
		
		return userInput;
		
	}
	
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
	
	public static String promptUser(Scanner s, String options) {
		
		String userChoice;
		
		userChoice = readUserInput(s, "Enter one of (" + options + "):").trim();
		
		if(!options.contains(userChoice) || userChoice.length() > 1) {
			
			System.out.println("Option not available");
			userChoice = promptUser(s, options);
			
		}
		
		return userChoice;
	
	}
	
	public static UserAbstract loginScreen(Scanner s) {
		
		String username;
		String password;
		UserAbstract currentUser;
		
		System.out.println("\t WELCOME TO BANK \t");
		System.out.println("Enter Login credentials or register");
		
		do {
			
			username = readUserInput(s, "Please type username to begin:").trim();	// NEED to control for username limits (character limits, etc)
			
			if(username.length() < 4) {
				
				System.out.println("Username must be at least 4 characters.");
				
			}
			
		} while(username.length() < 4);
		
		// determine if need to register new user based on user given username
		if(		CustomerMap.containsKey(username) ||
				EmployeeMap.containsKey(username) ||
				BankAdminMap.containsKey(username)
				) {
			
			System.out.println("- - - Login for existing user - - -");
			isNewUser = false;
			
		} else {
			
			System.out.println("- - - Register new user - - -");
			isNewUser = true;
			
		}
			
		// ask for login
		do {
			
			password = readUserInput(s, "Please enter your password:").trim();

			if(password.length() < 4) {
				
				System.out.println("Username must be at least 4 characters.");
				
			}
			
		} while(password.length() < 4);
		
		if(isNewUser) {
			
//			isNewData = true;
			System.out.println(
					"Are you a new Customer, Employee, or bank Administrator?");
			
			switch(promptUser(s, "cea")) {
			
			// New Customer
			case "c":
				System.out.println("Welcome, new customer! Please fill out this form.");
				currentUser = new Customer(
						username, password,
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
			
			// Attempt to log in with user given credentials
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
		
		return currentUser; // this user is logged in and ready to use the app
		
	}

}