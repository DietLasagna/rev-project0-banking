/**
 * UserAbstract.java
 * 
 * Version 0.6
 * 
 * Mar 05, 2022
 * 
 * Apache-2.0 License 
 */
package bankingapp;

import java.util.Scanner;

/**
 * Abstract class for all user types: Customer, Employee, and BankAdmin.
 * 
 * @version 0.6 05 Mar 2022
 * 
 * @author Michael Adams
 *
 */
public abstract class UserAbstract {
	
	public UserAbstract() {
		
	}
	
	/**
	 * Utility method for creating a String of radix10 numbers from one up to a given limit.
	 * For example, creates "12345" from Integer 5.
	 * 
	 * @param size Largest number in sequence
	 * @return String of numbers
	 */
	public static String generateNumbers(int size) {
		
		String numbers = "";
		
		for(int i = 0; i <size; i++) {
			
			numbers = numbers.concat(Integer.toString(i + 1));
			
		}
		
		return numbers;
	}
	
	/**
	 * Print options and direct users through information and actions available to that
	 * user.
	 * @param s An open STDIN Scanner
	 */
	abstract void menu(Scanner s);
	
	/**
	 * Prints all available information for a given Customer. This includes:<ul>
	 * <li> Full Name </li>
	 * <li> Street Address </li>
	 * <li> Numbered list of Accounts with status or balance </li></ul>
	 * @param customer The Customer object to display
	 */
	protected void printCustomerData(Customer customer) {
		
		System.out.println();
		System.out.println(customer.getFullName());
		System.out.println(customer.getStreetAddress());
		System.out.println("Accounts: " + customer.myAccounts.size());
		System.out.println("----------------------------------------");
		
		for(int i = 0; i < customer.myAccounts.size(); i++) {
			
			System.out.print(Integer.toString(i + 1) + " - ");
			
			if(customer.myAccounts.get(i).isJoint()) {
				
				System.out.print("~JOINT~ ");
				
			}
			
			switch(customer.myAccounts.get(i).getStatus()) {
			
			// Status is Open
			case 0:
				System.out.print("[OPEN APPLICATION]");
				break;
				
			// Status is Approved
			case 1:
				System.out.print("Balance: " + String.format("%.2f",
						customer.myAccounts.get(i).getBalance()));
				break;
				
			// Status is Closed
			case 2:
				System.out.print("[DENIED: ACCOUNT CLOSED]");
				break;
				
			default:
				System.out.print("ERROR: ACCOUNT OUT OF BOUNDS"); // DEBUG
				
			}
			
			System.out.println();
			
		}
		
		System.out.println("----------------------------------------");
		
	}

}
