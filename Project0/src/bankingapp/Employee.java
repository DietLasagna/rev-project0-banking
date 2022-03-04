package bankingapp;

import java.util.Scanner;

import bankingexceptions.*;

public class Employee extends UserAbstract implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4450309457885994468L;
	private final String username;
	private final String password;

	public Employee(String user, String pass) {

		this.username = user;
		this.password = pass;
	}

	@Override
	void menu(Scanner s) {
		
		String userInput = "";
		String inputOptions = "";
		Customer currentCustomer;
		
		// List all customers
		BankingApplication.CustomerMap.forEach((k,v) -> System.out.println(k + "\t | "
				+ v.getFullName() + "\t - Accounts: " + v.myAccounts.size()));
		
		System.out.println();
		userInput = BankingApplication.readUserInput(s,
				"Type customer username to view accounts, or 0 to logout").trim();
		
		if(userInput.equalsIgnoreCase("0")) {

			System.out.println("Logged out.");
			return;
			
		} else if (BankingApplication.CustomerMap.containsKey(userInput)) {
			
			currentCustomer = BankingApplication.CustomerMap.get(userInput);
			printCustomerData(currentCustomer);
			System.out.println("\n"
					+ "1 - Approve a new account\n"
					+ "0 - Back\n"
					);
			
			switch(BankingApplication.promptUser(s, "10")) {
			
			// Approve an account
			case "1":
				// Select account
				if(currentCustomer.myAccounts.size() < 1) {
					
					System.out.println("This customer has no accounts.");
					
				} else {
					
					System.out.println("Please select account to approve:");
					// prompt based on number of accounts in myAccounts
					for(int i = 0; i < currentCustomer.myAccounts.size(); i++) {
						inputOptions = inputOptions.concat(Integer.toString(i + 1));
					}

					approveAccount(currentCustomer.myAccounts.get(Integer
							.parseInt(BankingApplication.promptUser(s, inputOptions)) - 1));
					
				}
				
				break;
				
			default:
			case "0":
				// back
				
			}
			
			
		} else {
			
			System.out.println("Invalid input.");
			
		}
		
		menu(s);
		
	}
	
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

	String getUsername() {
		return username;
	}

	String getPassword() {
		return password;
	}

}
