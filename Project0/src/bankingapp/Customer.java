package bankingapp;

import java.util.ArrayList;
import java.util.Scanner;


public class Customer extends UserAbstract implements Transformative, java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6471777691115479433L;
	ArrayList<Account> myAccounts; // Maybe LinkedHashMap with <#, Account object> ?
	private final String username;
	private final String password;
	private final String fullName;
	private final String streetAddress;
	

	public Customer(String user, String pass, String name, String addr) {

		this.username = user;
		this.password = pass;
		myAccounts = new ArrayList<Account>();
		this.fullName = name;
		this.streetAddress = addr;
		
	}

	@Override
	void menu(Scanner s) {
		
		String inputOptions = "";
		
		System.out.println("\nHello, " + fullName + "!");
		printCustomerData(this);
		System.out.println("\nWhat would you like to do today?\n"
				+ "1 - Open a new account\n"
				+ "2 - Make a withdrawal\n"
				+ "3 - Make a deposit\n"
				+ "4 - Transfer funds between accounts\n"
				+ "0 - Log out\n"
				);
		switch(BankingApplication.promptUser(s, "12340")) {
		
		// Open new account
		case "1":
			if(myAccounts.size() < 9) {
				
				System.out.println("Is this new account a joint account?");
				openAccount(BankingApplication.promptUser(s, "yn") == "y");
				System.out.println("Your application was sent!");
				
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
				// prompt based on number of accounts in myAccounts
				for(int i = 0; i < myAccounts.size(); i++) {
					inputOptions = inputOptions.concat(Integer.toString(i + 1));
				}
				
				withdraw(myAccounts.get(Integer.parseInt(BankingApplication.promptUser(s, inputOptions)) - 1),
						BankingApplication.readUserAmount(s));
			}
			break;
			
		// Make a deposit
		case "3":
			if(countAcceptedAccounts() < 1) {
				System.out.println("You do not have any accounts!");
			} else {
				System.out.println("Please select account");
				// prompt based on number of accounts in myAccounts
				for(int i = 0; i < myAccounts.size(); i++) {
					inputOptions = inputOptions.concat(Integer.toString(i + 1));
				}
				
				deposit(myAccounts.get(Integer.parseInt(BankingApplication.promptUser(s, inputOptions)) - 1),
						BankingApplication.readUserAmount(s));
			}
			break;
		
		// Transfer between accounts
		case "4":
			if(countAcceptedAccounts() < 2) {
				System.out.println("You do not have enough accounts!");
			} else {
				System.out.println("Please select account to transfer from, then an account to transfer to:");
				// prompt based on number of accounts in myAccounts
				for(int i = 0; i < myAccounts.size(); i++) {			// NEED to account for more than 9 accounts
					inputOptions = inputOptions.concat(Integer.toString(i + 1));
				}
				
				transfer(myAccounts.get(Integer.parseInt(BankingApplication.promptUser(s, inputOptions)) - 1),
						myAccounts.get(Integer.parseInt(BankingApplication.promptUser(s, inputOptions)) - 1),
						BankingApplication.readUserAmount(s));
				
			}
			// 
			break;
			
		// Log out
		default:
		case "5":
			System.out.println("Goodbye!");
			return;
		}
		
		menu(s);
//		return;
		
	}
	
	@Override
	public void printCustomerData(Customer customer) {
		
		super.printCustomerData(this); // Customer can only print own data
		
	}

	@Override
	public boolean withdraw(Account accountFrom, double withdrawAmount) {
		
		boolean isComplete = false;
		double accountBalance = accountFrom.getBalance();

		// check if account is approved
		if(accountFrom.getStatus() != 1) {
			
			System.out.println("Account unavailable.");
			return false;
			
		}
		
		if(withdrawAmount < accountBalance && this.myAccounts.indexOf(accountFrom) > -1) {
			
			accountFrom.setBalance(accountBalance - withdrawAmount);
			isComplete = true;
			
		} else {
			
			System.out.println("Insufficient funds. Transaction cancelled.");
			
		}
		
		return isComplete;
		
	}

	@Override
	public boolean deposit(Account accountTo, double depositAmount) {

		boolean isComplete = false;
		
		// check if account is approved
		if(accountTo.getStatus() != 1) {
			
			System.out.println("Account unavailable.");
			return false;
			
		}
		
		if(this.myAccounts.indexOf(accountTo) > -1) {
			
			accountTo.setBalance(accountTo.getBalance() + depositAmount);
			isComplete = true;
			
		} else {
			
			System.out.println("Error. Transaction cancelled.");
			
		}
		
		return isComplete;
		
	}

	@Override
	public boolean transfer(Account accountFrom, Account accountTo,
						double transferAmount) {

		boolean isComplete = false;
		
		if(withdraw(accountFrom, transferAmount)) {
			
			if(deposit(accountTo, transferAmount)) {
			
				isComplete = true;
			
			} else {
				
				// reverse the action of the withdraw to cancel the transfer
				deposit(accountFrom, transferAmount);
				
			}
		}
		
		return isComplete;
		
	}
	
//	public String selectAccount() {
//		
//		String inputOptions = "";
//		
//		for(int i = 1; i <= myAccounts.size(); i++) {			// NEED to account for more than 9 accounts
//			inputOptions = inputOptions.concat(Integer.toString(i));
//		}
//		
//		return inputOptions;
//		
//	}
	
	public byte countAcceptedAccounts() {
		
		byte numberOfAcceptedAccounts = 0;
		
		for(Account a : myAccounts) {
			
			if(a.getStatus() == 1) {
				
				numberOfAcceptedAccounts++;
				
			}
			
		}
		
		return numberOfAcceptedAccounts;
		
	}
	
	public Account openAccount(boolean isJoint) {
		
		Account myNewAccount = new Account(isJoint);
		
		myAccounts.add(myNewAccount);
		
		return myNewAccount;
		
	}

	String getFullName() {
		return fullName;
	}

	String getStreetAddress() {
		return streetAddress;
	}

	String getUsername() {
		return username;
	}

	String getPassword() {
		return password;
	}
	

}
