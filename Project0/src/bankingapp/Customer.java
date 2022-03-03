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
		
		System.out.println("Hello, " + fullName + "!");
		printCustomerData(this);
		System.out.println("What would you like to do today?\n"
				+ "1 - Open a new account\n"
				+ "2 - Make a withdrawl\n"
				+ "3 - Make a deposit\n"
				+ "4 - Transfer funds between accounts\n"
				+ "0 - Log out\n"
				);
		switch(BankingApplication.promptUser(s, "12340")) {
		case "1":
			System.out.println("Is this new account a joint account?");
			openAccount(BankingApplication.promptUser(s, "yn") == "y");
			System.out.println("Your application was sent!");
			break;
		case "2":
			if(this.myAccounts.size() < 1) {
				System.out.println("You do not have any accounts!");	// NEED to account for only open accounts too
			} else {
				//
			}
			break;
		case "3":
			// 
			break;
		case "4":
			// 
			break;
		default:
		case "5":
			System.out.println("Goodbye!");
			return;
		}
		
		menu(s);
		return;
		
	}
	
	@Override
	public void printCustomerData(Customer customer) {
		
		super.printCustomerData(this); // Customer can only print own data
		
	}

	@Override
	public void withdraw(Account accountFrom, double withdrawAmount) {
		
		// if accountFrom is in myAccount
			// if accountFrom balance is greater than withdrawAmount
				// subtract and update balance
		
		// Use custom exception to pass error message?
		
	}

	@Override
	public void deposit(Account accountTo, double depositAmount) {
		
		// if accountFrom is in myAccount
			// add depositAmount and update balance
		
		// Use custom exception to pass error message?
		
	}

	@Override
	public void transfer(Account accountFrom, Account accountTo,
						double transferAmount) {
		
		// TODO use other methods?
		// if custom exception, can pass along too
		
	}
	
	public Account openAccount(boolean isJoint) {
		
		Account myNewAccount = new Account(isJoint);
		
		myAccounts.add(myNewAccount);
		
		return myNewAccount;
		
	}

	public String getFullName() {
		return fullName;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	

}
