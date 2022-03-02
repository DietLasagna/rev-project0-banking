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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String username = "";
		String password = "";
		boolean isNewData = false;
		boolean isNewUser = true;
		
		// Deserialize data (returns empty UserMap if no file present)
		HashMap<String, UserAbstract> UserMap = Marshal.deserialize("./src/userdata.ser");
		
		// Start reading inputs
		System.out.println("\t WELCOME TO BANK \t");
		System.out.println("Enter Login credentials or register");
		try (Scanner s = new Scanner(System.in)) {
		
			// ask for user type
			
			username = readUserInput(s, "Please type username to begin:").trim();
			
			if(UserMap.containsKey(username)) {
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
				// create new object
					// ask customer many questions...
				// add new object to UserMap
			} else if(UserMap.get(username).password != password) {
				System.out.println("Invalid Login");
				System.out.println("----------------------------------------");
				return;
			}
			// register or successful login
		// show menu based on login type
			// actions
			// logout
			
		} catch (Exception ex) {
		
			System.out.println("Error. Try again later.");
			ex.printStackTrace();
		
		}
		// Serialize data
			// only if change occurred?
		if(isNewData) {
			Marshal.serialize("./src/userdata.ser", UserMap);
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
			
			userInput = s.nextLine();
			
		}
		
		catch (Exception ex) {

			ex.printStackTrace();
			readUserInput(s, "Invaild input, please try again:");
			
		}
		
		return userInput;
		
	}
	
	

}
