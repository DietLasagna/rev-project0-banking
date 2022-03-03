package bankingapp;

import java.util.Scanner;

public abstract class UserAbstract {
	
	/**
	 * Every user has username and password
	 * 	these attributes never change
	 * 
	 * Every user can print customer data
	 */
	
	public UserAbstract() {
		
	}
	
	abstract void menu(Scanner s);
	
	public void printCustomerData(Customer customer) {
		
		System.out.println(customer.getFullName());
		System.out.println(customer.getStreetAddress());
		System.out.println("Accounts: " + customer.myAccounts.size());
		System.out.println("----------------------------------------");
		
		for(Account a : customer.myAccounts) {
			
			System.out.print(a.getAccountNumber() + " ");
			if(a.isJoint()) {
				System.out.print("~JOINT~ ");
			}
			switch(a.getStatus()) {
			case 0:
				System.out.print("[OPEN APPLICATION]");
				break;
			case 1:
				System.out.print("Balance: " + a.getBalance());
				break;
			case 2:
				System.out.print("[DENIED: ACCOUNT CLOSED]");
				break;
			default:
				System.out.print("ERROR: ACCOUNT OUT OF BOUNDS"); // Debug
			}
			System.out.println();
			
		}
		
	}

}
