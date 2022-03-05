/**
 * Employee.java
 * 
 * Version 0.5
 * 
 * Mar 04, 2022
 * 
 * Apache-2.0 License 
 */
package bankingapp;

import java.util.Scanner;

import bankingexceptions.*;

/**
 * The Employee Class provides data and menus specific to bank employee users. Employees 
 * can view any customer's personal data, and approve or deny open applications for accounts.
 * 
 * @version 0.5 04 Mar 2022
 * 
 * @author Michael Adams
 *
 */
public class Employee extends UserAbstract implements java.io.Serializable {
	
	private static final long serialVersionUID = 4450309457885994468L;
	private final String username;
	private final String password;

	public Employee(String user, String pass) {

		this.username = user;
		this.password = pass;
	}

	@Override
	void menu(Scanner s) {
		
		/** Stores Customer username when selecting who to view */
		String userInput = "";
		/** The Customer currently being viewed */
		Customer currentCustomer;
		/** Controls looping back to menu until user logs off */
		boolean isNotDone = true;
		
		do {
			
			// List all customers
			BankingApplication.CustomerMap.forEach((k,v) -> System.out.println(k + "\t | "
					+ v.getFullName() + "\t - Accounts: " + v.myAccounts.size()
					+ " |\t " + v.countOpenAccounts() + " [OPEN]"));
			System.out.println();
			
			userInput = BankingApplication.readUserInput(s,
					"Type customer username to view accounts,"
					+ " or 0 to logout").trim().toLowerCase();
			
			if(userInput.equalsIgnoreCase("0")) {
	
				System.out.println("Logged out.");
				isNotDone = false;
				
			} else if (BankingApplication.CustomerMap.containsKey(userInput)) {
				
				currentCustomer = BankingApplication.CustomerMap.get(userInput);
				printCustomerData(currentCustomer);
				System.out.println("\n"
						+ "1 - Approve a new account\n"
						+ "2 - Deny a new account\n"
						+ "3 - See Account activity\n"
						+ "0 - Back\n"
						);
				
				switch(BankingApplication.promptUser(s, "1230")) {
				
				// Approve an Open Account
				case "1":
					if(currentCustomer.countOpenAccounts() < 1) {
						
						System.out.println("This customer has no open applications.");
						
					} else {
						
						System.out.println("Please select account to approve:");
	
						approveAccount(currentCustomer.myAccounts.get(Integer
								.parseInt(BankingApplication.promptUser(s,
								generateNumbers(currentCustomer.myAccounts.size()))) - 1));
						
					}
					
					break;
					
				// Deny an Open Account
				case "2":
					if(currentCustomer.countOpenAccounts() < 1) {
						
						System.out.println("This customer has no open applications.");
						
					} else {
						
						System.out.println("Please select account to approve:");
	
						closeAccount(currentCustomer.myAccounts.get(Integer
								.parseInt(BankingApplication.promptUser(s,
								generateNumbers(currentCustomer.myAccounts.size()))) - 1));
						
					}
					
					break;
					
				// See Account activity
				case "3":
					if(currentCustomer.countAcceptedAccounts() < 1) {
						
						System.out.println("This customer has no applicable accounts.");
						
					} else {
						
						System.out.println("Please select account");
						currentCustomer.myAccounts.get(Integer.parseInt(
								BankingApplication.promptUser(s,
								generateNumbers(currentCustomer.myAccounts.size()))) - 1)
								.printEventLog();
					
					}
					
					break;
					
				default:
				case "0":
					// Loop back to top of menu
					
				}
				
				
			} else {
				
				System.out.println("Invalid input.");
				
			}
		
		} while(isNotDone);
		
		/** Reaching this point means user is ending session (logged out) */
		
	}
	
	/**
	 * Set Account Status to "Approved". This enables users to change the balance.
	 * @param account Account object to change
	 */
	public void approveAccount(Account account) {
		
		if(account.getStatus() == 0) {
			
			try {
				
				account.setStatus((byte)1);
				System.out.println("Account is approved.");
				
			} catch(AccountStatusChangeException e) {
				
				System.out.println(e.getMessage());
				
			}
			
		} else {
			
			System.out.println("Error: account is not open for approval.");
			
		}
		
	}
	
	/**
	 * Set Account Status to "Closed". This disables user from changing the balance.
	 * @param account Account object to change
	 */
	public void closeAccount(Account account) {
		
		if(account.getStatus() == 0) {
			
			try {
				
				account.setStatus((byte)2);
				System.out.println("Account is closed.");
				
			} catch(AccountStatusChangeException e) {
				
				System.out.println(e.getMessage());
				
			}
			
		} else {
			
			System.out.println("Error: permissions not set to close this account.");
			
		}
		
	}
	
	/**
	 * Getter for Employee "username"
	 * @return String username
	 */
	String getUsername() {
		
		return username;
		
	}

	/**
	 * Getter for Employee "password"
	 * @return String password
	 */
	String getPassword() {
		
		return password;
		
	}

}
