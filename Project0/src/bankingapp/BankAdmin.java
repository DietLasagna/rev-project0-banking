/**
 * BankAdmin.java
 * 
 * Version 0.5
 * 
 * Mar 04, 2022
 * 
 * Apache-2.0 License 
 */
package bankingapp;

import java.util.Scanner;

import bankingexceptions.AccountStatusChangeException;

/**
 * The Bank Administrator Class provides data and menus specific to bank administration 
 * users. Administrators can view any customer's personal data, approve or deny open 
 * applications for accounts as well as cancel approved accounts, and modify any Customer
 * Account balance.
 * 
 * @version 0.5 04 Mar 2022
 * 
 * @author Michael Adams
 *
 */
public class BankAdmin extends Employee implements Transformative, java.io.Serializable {

	private static final long serialVersionUID = 1014524170595655849L;

	public BankAdmin(String user, String pass) {
		
		super(user, pass);
		
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
			System.out.println(":: Good morning ::");
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
						+ "2 - Make a withdrawal\n"
						+ "3 - Make a deposit\n"
						+ "4 - Transfer funds between accounts\n"
						+ "5 - Close an account\n"
						+ "6 - See Account activity\n"
						+ "0 - Back\n"
						);
				
				switch(BankingApplication.promptUser(s, "1234560")) {
				
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
					
				// Make a withdrawal
				case "2":
					if(currentCustomer.countAcceptedAccounts() < 1) {
						
						System.out.println("This customer has no available accounts.");
						
					} else {
						
						System.out.println("Please select account:");
						
						withdraw(currentCustomer.myAccounts.get(Integer.parseInt(
								BankingApplication.promptUser(s,
								generateNumbers(currentCustomer.myAccounts.size()))) - 1),
								BankingApplication.readUserAmount(s));
					
					}
					
					break;
					
				// Make a deposit
				case "3":
					if(currentCustomer.countAcceptedAccounts() < 2) {
						
						System.out.println("This customer has too few available accounts.");

					} else {
						
						System.out.println("Please select account");
						
						deposit(currentCustomer.myAccounts.get(Integer.parseInt(
								BankingApplication.promptUser(s,
								generateNumbers(currentCustomer.myAccounts.size()))) - 1),
								BankingApplication.readUserAmount(s));
						
					}
					
					break;
			
				// Transfer between Accounts
				case "4":
					if(currentCustomer.countAcceptedAccounts() < 1) {
						
						System.out.println("This customer has no available accounts.");
						
					} else {
						
						System.out.println("Please select account to transfer from,"
								+ "then an account to transfer to:");
						
						transfer(currentCustomer.myAccounts.get(Integer.parseInt(
								BankingApplication.promptUser(s,
								generateNumbers(currentCustomer.myAccounts.size()))) - 1),
								currentCustomer.myAccounts.get(Integer.parseInt(
								BankingApplication.promptUser(s,
								generateNumbers(currentCustomer.myAccounts.size()))) - 1),
								BankingApplication.readUserAmount(s));
						
					}
					
					break;
					
				// Close an Account
				case "5":
					// Select account
					if(currentCustomer.myAccounts.size() < 1) {
						
						System.out.println("This customer has no accounts.");
						
					} else {
						
						System.out.println("Please select account to close:");
	
						closeAccount(currentCustomer.myAccounts.get(Integer.parseInt(
								BankingApplication.promptUser(s,
								generateNumbers(currentCustomer.myAccounts.size()))) - 1));
						
					}
					
					break;
					
				// See Account activity
				case "6":
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

	@Override
	public boolean withdraw(Account accountFrom, double withdrawAmount) {
		
		boolean isComplete = false;
		double accountBalance = accountFrom.getBalance();

		// Check if Account is Approved
		if(accountFrom.getStatus() != 1) {
			
			System.out.println("Account unavailable.");
			return false;
			
		}
		
		if(withdrawAmount < accountBalance) {
			
			accountFrom.setBalance(accountBalance - withdrawAmount);
			isComplete = true;
			accountFrom.addEvent("Withdrawal", withdrawAmount);
			
		} else {
			
			System.out.println("Insufficient funds. Transaction cancelled.");
			
		}
		
		return isComplete;
		
	}

	@Override
	public boolean deposit(Account accountTo, double depositAmount) {

		boolean isComplete = false;
		
		// Check if Account is Approved
		if(accountTo.getStatus() != 1) {
			
			System.out.println("Account unavailable.");
			return false;
			
		}
			
		accountTo.setBalance(accountTo.getBalance() + depositAmount);
		isComplete = true;
		accountTo.addEvent("Deposit", depositAmount);
		
		return isComplete;
		
	}

	@Override
	public boolean transfer(Account accountFrom, Account accountTo, double transferAmount) {

		boolean isComplete = false;
		
		if(withdraw(accountFrom, transferAmount)) {
			
			if(deposit(accountTo, transferAmount)) {
			
				isComplete = true;
				accountFrom.addEvent("Transfer from", transferAmount);
				accountTo.addEvent("Transfer to", transferAmount);
			
			} else {
				
				// Reverse the action of the withdraw to cancel the transfer
				deposit(accountFrom, transferAmount);
				
			}
		}
		
		return isComplete;
		
	}
	
	@Override
	public void closeAccount(Account account) {
		
		if(account.getStatus() != 2) {
			
			try {
				
				account.setStatus((byte)2);
				System.out.println("Account is closed.");
				
			} catch(AccountStatusChangeException e) {
				
				System.out.println(e.getMessage());
				
			}
			
		} else {
			
			System.out.println("Error: account is already closed.");
			
		}
		
	}

}
