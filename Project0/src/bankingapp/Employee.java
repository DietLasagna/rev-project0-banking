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
		System.out.println("");
		
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

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
