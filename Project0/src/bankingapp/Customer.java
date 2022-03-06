/**
 * Customer.java
 * 
 * Version 0.6
 * 
 * Mar 05, 2022
 * 
 * Apache-2.0 License 
 */
package bankingapp;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Customer Class provides data and menus specific to customer users, including 
 * a collection of the customer's accounts. Customers can view their own personal data, 
 * modify their own account balances, and apply to open a new account.
 * 
 * @version 0.6 05 Mar 2022
 * 
 * @author Michael Adams
 *
 */
public class Customer extends UserAbstract implements Transformative, java.io.Serializable {
	
	private static final long serialVersionUID = 6471777691115479433L;
	private final String username;
	private final String password;
	private final String fullName;
	private final String streetAddress;
	/** Mutable collection of Customer's Accounts */
	ArrayList<Account> myAccounts;
	

	public Customer(String user, String pass, String name, String addr) {

		this.username = user;
		this.password = pass;
		this.fullName = name;
		this.streetAddress = addr;
		this.myAccounts = new ArrayList<Account>();
		
	}
	
	
	@Override
	void menu(Scanner s) {
		
		/** Controls looping back to menu until user logs off */
		boolean isNotDone = true;
		/** Used to hold second customer account for new joint account */
		UserAbstract secondLogin;
		Customer jointCustomer;
		/** Holds selected accounts for transactions */
		Account accountTo;
		Account accountFrom;
		/** Holds dollar amount for transactions */
		double transactionAmount;
		
		System.out.println("\nHello, " + fullName + "!");
		
		do {
			
			printCustomerData(this);
			System.out.println("\nWhat would you like to do today?\n"
					+ "1 - Open a new account\n"
					+ "2 - Make a withdrawal\n"
					+ "3 - Make a deposit\n"
					+ "4 - Transfer funds between accounts\n"
					+ "5 - See Account activity\n"
					+ "0 - Log out\n"
					);
			
			switch(BankingApplication.promptUser(s, "123450")) {
			
			// Open new account
			case "1":
				if(myAccounts.size() < 8) {
					
					System.out.println("Is this new account a joint account?");
					if(BankingApplication.promptUser(s, "yn").equals("y")) {
						
						do {
							
							System.out.println("Please log in or register with "
									+ "a second customer account.");
							secondLogin = BankingApplication.loginScreen(s);
						
						} while(!(secondLogin instanceof Customer) &&
								!(secondLogin.equals(this)));
						
						jointCustomer = (Customer) secondLogin;
						jointCustomer.myAccounts.add(openAccount(true));
						
					} else {
						
						openAccount(false);
						
					}
					
//					openAccount(BankingApplication.promptUser(s, "yn") == "y");
					System.out.println("Your application was sent!");
					BankingApplication.saveData();
					
				} else {
					
					System.out.println("You have reached your allowed limit of accounts.");
					
				}
				
				break;
				
			// Make a withdrawal
			case "2":
				if(countAcceptedAccounts() < 1) {
					
					System.out.println("You do not have any accounts!");
				
				} else {
					
					System.out.println("Please select account:");
					accountFrom = myAccounts.get(Integer.parseInt(
							BankingApplication.promptUser(s,
							generateNumbers(myAccounts.size()))) - 1);
					transactionAmount = BankingApplication.readUserAmount(s);
	
					/** Make withdrawal based on user selected account and amount*/
					if(withdraw(accountFrom, transactionAmount)) {
						
						accountFrom.addEvent("Withdrawal", transactionAmount);
						
					}
					
				}
				
				break;
				
			// Make a deposit
			case "3":
				if(countAcceptedAccounts() < 1) {
					
					System.out.println("You do not have any accounts!");
					
				} else {
					
					System.out.println("Please select account:");
					accountTo = myAccounts.get(Integer.parseInt(
							BankingApplication.promptUser(s,
									generateNumbers(myAccounts.size()))) - 1);
					transactionAmount = BankingApplication.readUserAmount(s);
					
					/** Make deposit based on user selected account and amount*/
					if(deposit(accountTo, transactionAmount)) {

						accountTo.addEvent("Deposit", transactionAmount);
						
					}
					
				}
				break;
			
			// Transfer between Accounts
			case "4":
				if(countAcceptedAccounts() < 2) {
					
					System.out.println("You do not have enough accounts!");
					
				} else {
					
					System.out.println("Please select account to transfer from:");
					accountFrom = myAccounts.get(Integer.parseInt(
							BankingApplication.promptUser(s,
							generateNumbers(myAccounts.size()))) - 1);
					System.out.println("Please select an account to transfer to:");
					accountTo = myAccounts.get(Integer.parseInt(
							BankingApplication.promptUser(s,
							generateNumbers(myAccounts.size()))) - 1);
					transactionAmount = BankingApplication.readUserAmount(s);
					
					/** Make transfer based on user selected accounts and amount*/
					if(transfer(accountFrom, accountTo, transactionAmount)) {

						accountFrom.addEvent("Transfer from", transactionAmount);
						accountTo.addEvent("Transfer to", transactionAmount);
						
					}
					
				}
				
				break;
				
			// See Account activity
			case "5":
				if(countAcceptedAccounts() < 1) {
					
					System.out.println("You do not have any accounts!");
					
				} else {
					
					System.out.println("Please select account");
					myAccounts.get(Integer.parseInt(BankingApplication.promptUser(s,
								generateNumbers(myAccounts.size()))) - 1).printEventLog();
				
				}
				
				break;
				
			// Log out
			default:
			case "0":
				System.out.println("Goodbye!");
				isNotDone = false;
				
			}
		
		} while(isNotDone);
		
		/** Reaching this point means user is ending session (logged out) */
		
	}
	
	@Override
	protected void printCustomerData(Customer customer) {
		
		super.printCustomerData(this); // Customer can only print own data
		
	}
	
	/**
	 * Customer files to open a new account; must be approved by the bank before using.
	 * @param isJoint boolean for if the account type is "Joint Account"
	 * @return The Account object for the new account
	 */
	protected Account openAccount(boolean isJoint) {
		
		Account myNewAccount = new Account(isJoint);
		myAccounts.add(myNewAccount);
		
		return myNewAccount;
		
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
		
		if(withdrawAmount < accountBalance && this.myAccounts.indexOf(accountFrom) > -1) {
			
			accountFrom.setBalance(accountBalance - withdrawAmount);
			isComplete = true;
			BankingApplication.saveData();
			
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
		
		if(this.myAccounts.indexOf(accountTo) > -1) {
			
			accountTo.setBalance(accountTo.getBalance() + depositAmount);
			isComplete = true;
			BankingApplication.saveData();
			
		} else {
			
			System.out.println("Error. Transaction cancelled.");
			
		}
		
		return isComplete;
		
	}

	@Override
	public boolean transfer(Account accountFrom, Account accountTo, double transferAmount) {

		boolean isComplete = false;
		
		if(withdraw(accountFrom, transferAmount)) {
			
			if(deposit(accountTo, transferAmount)) {
			
				isComplete = true;
				BankingApplication.saveData();
			
			} else {
				
				// Reverse the action of the withdraw to cancel the transfer
				deposit(accountFrom, transferAmount);
				
			}
		}
		
		return isComplete;
		
	}
	
	/**
	 * Utility method to get the number of Customer Accounts which are of Approved status
	 * @return Number of Approved Accounts
	 */
	public byte countAcceptedAccounts() {
		
		byte numberOfAcceptedAccounts = 0;
		
		for(Account a : myAccounts) {
			
			if(a.getStatus() == 1) {
				
				numberOfAcceptedAccounts++;
				
			}
			
		}
		
		return numberOfAcceptedAccounts;
		
	}
	
	/**
	 * Utility method to get the number of Customer Accounts which are of Open status
	 * @return Number of Open Accounts
	 */
	public byte countOpenAccounts() {
		
		byte numberOfOpenAccounts = 0;
		
		for(Account a : myAccounts) {
			
			if(a.getStatus() == 0) {
				
				numberOfOpenAccounts++;
				
			}
			
		}
		
		return numberOfOpenAccounts;
		
	}
	
	/**
	 * Getter for Customer "fullName"
	 * @return String fullName
	 */
	String getFullName() {
		
		return fullName;
		
	}
	
	/**
	 * Getter for Customer "streetAddress"
	 * @return String streetAddress
	 */
	String getStreetAddress() {
		
		return streetAddress;
		
	}
	
	/**
	 * Getter for Customer "username"
	 * @return String username
	 */
	String getUsername() {
		
		return username;
		
	}
	
	/**
	 * Getter for Customer "password"
	 * @return String password
	 */
	String getPassword() {
		
		return password;
		
	}
	

}
