package bankingapp;

import java.util.ArrayList;

public class Customer extends UserAbstract implements Transformative {
	
	ArrayList<Account> myAccounts; // Maybe LinkedHashMap with <#, Account object> ?
	private final String fullName;
	private final String streetAddress;
	

	public Customer(String user, String pass, String name, String addr) {
		super(user, pass);
		myAccounts = new ArrayList<Account>();
		this.fullName = name;
		this.streetAddress = addr;
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
	

}
