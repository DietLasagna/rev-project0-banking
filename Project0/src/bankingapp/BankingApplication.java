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
	
	static public boolean isNewData = false;
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
			} else {
//				currentUser.menu(s);
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
		if(isNewData) {
			try {
				
				Marshal.serialize("./src/CustomerData.ser", CustomerMap);
				Marshal.serialize("./src/EmployeeData.ser", EmployeeMap);
				Marshal.serialize("./src/BankAdminData.ser", BankAdminMap);
				
			} catch (Exception ex) {
				
				ex.printStackTrace();
				System.out.println("DID NOT DESERIALIZE"); // DEBUG
				
			}
		
		}
		
		// end program

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
		
		// Start reading inputs
		System.out.println("\t WELCOME TO BANK \t");
		System.out.println("Enter Login credentials or register");
			
		// ask for username
			username = readUserInput(s, "Please type username to begin:").trim();
			
		// determine if need to register new user
			if(CustomerMap.containsKey(username) || EmployeeMap.containsKey(username) || BankAdminMap.containsKey(username)) {
				System.out.println("- - - Login for existing user - - -");
				isNewUser = false;
			} else {
				System.out.println("- - - Register new user - - -");
				isNewUser = true;
			}
			
		// ask for login
			password = readUserInput(s, "Please enter your password:").trim();
			
			if(isNewUser) {
				isNewData = true;
				System.out.println(
						"Are you a new Customer, Employee, or bank Administrator?");
				
				switch(promptUser(s, "cea")) {
				case "c":
					System.out.println("Welcome, new customer! Please fill out this form.");
					currentUser = new Customer(
							username, password,
							readUserInput(s, "Please type full name:"),
							readUserInput(s, "Please type mailing address:")
							);
					CustomerMap.put(username, (Customer) currentUser);
					break;
				case "e":
					currentUser = new Employee(username, password);
					EmployeeMap.put(username, (Employee) currentUser);
					break;
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
				if(CustomerMap.containsKey(username) && CustomerMap.get(username).getPassword() != password) {
					currentUser = CustomerMap.get(username);
				} else if(EmployeeMap.containsKey(username) && EmployeeMap.get(username).getPassword() != password) {
					currentUser = EmployeeMap.get(username);
				} else if(BankAdminMap.containsKey(username) && BankAdminMap.get(username).getPassword() != password) {
					currentUser = BankAdminMap.get(username);
				} else {
				System.out.println("\t Invalid Login \t");
				System.out.println("----------------------------------------");
				return null;
				}
			}
		
		return currentUser;
		
	}

}