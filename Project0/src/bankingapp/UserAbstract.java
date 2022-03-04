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
		
		for(int i = 0; i < customer.myAccounts.size(); i++) {
			
			System.out.print(Integer.toString(i + 1) + " - ");
			if(customer.myAccounts.get(i).isJoint()) {
				System.out.print("~JOINT~ ");
			}
			switch(customer.myAccounts.get(i).getStatus()) {
			case 0:
				System.out.print("[OPEN APPLICATION]");
				break;
			case 1:
				System.out.print("Balance: " + String.format("%.2f",
						customer.myAccounts.get(i).getBalance()));
				break;
			case 2:
				System.out.print("[DENIED: ACCOUNT CLOSED]");
				break;
			default:
				System.out.print("ERROR: ACCOUNT OUT OF BOUNDS"); // Debug
			}
			System.out.println();
			
		}
		
		System.out.println("----------------------------------------");
		
	}

}
