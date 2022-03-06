/**
 * Employee.java
 * 
 * Version 0.6
 * 
 * Mar 05, 2022
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
 * @version 0.6 05 Mar 2022
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
		String userInput;
		/** The Customer currently being viewed */
		Customer currentCustomer;
		/** Controls looping back to menu until user logs off */
		boolean isNotDone = true;
		/** Controls focus on one Customer until tasks are complete */
		boolean isSameCustomer = true;
		
		do {

			System.out.println();
			printCustomers();
			System.out.println();
			
			userInput = BankingApplication.readUserInput(s,
					"Type customer username to view accounts,"
					+ " or 0 to logout").trim().toLowerCase();
			
			if(userInput.equalsIgnoreCase("0")) {
	
				System.out.println("Logged out.");
				isNotDone = false;
				
			} else if (BankingApplication.CustomerMap.containsKey(userInput)) {
				
				currentCustomer = BankingApplication.CustomerMap.get(userInput);
				
				do {
					
					/** Keep focus on this Customer until told otherwise */
					isSameCustomer = true;
					
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
						isSameCustomer = false;
						
					}
				
				} while(isSameCustomer);
				
				
			} else {
				
				System.out.println("Invalid input.");
				
			}
		
		} while(isNotDone);
		
		/** Reaching this point means user is ending session (logged out) */
		
	}
	
	/**
	 * Prints a standardized list of Customers and Account information:<ul>
	 * <li>username
	 * <li>full name
	 * <li>number of accounts
	 * <li>number of open account applications.</ul>
	 */
	protected void printCustomers() {
		
		BankingApplication.CustomerMap.forEach((k,v) -> System.out.println(
				k + "\t | " + v.getFullName() +
				"\t ||\t Accounts: " + v.myAccounts.size() +
				" | " + v.countOpenAccounts() + " [OPEN]"));
		
	}
	
	/**
	 * Set Account Status to "Approved". This enables users to change the balance.
	 * @param account Account object to change
	 */
	protected void approveAccount(Account account) {
		
		if(account.getStatus() == 0) {
			
			try {
				
				account.setStatus((byte)1);
				System.out.println("Account is approved.");
				BankingApplication.saveData();
				
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
	protected void closeAccount(Account account) {
		
		if(account.getStatus() == 0) {
			
			try {
				
				account.setStatus((byte)2);
				System.out.println("Account is closed.");
				BankingApplication.saveData();
				
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
