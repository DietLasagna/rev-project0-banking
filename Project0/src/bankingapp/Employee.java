package bankingapp;

import bankingexceptions.*;

public class Employee extends UserAbstract {

	public Employee(String user, String pass) {
		super(user, pass);
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

}
