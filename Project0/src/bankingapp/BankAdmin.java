package bankingapp;

import bankingexceptions.AccountStatusChangeException;

public class BankAdmin extends Employee implements Transformative, java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1014524170595655849L;

	public BankAdmin(String user, String pass) {
		super(user, pass);
		
	}

	@Override
	public void withdraw(Account accountFrom, double withdrawAmount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deposit(Account accountTo, double depositAmount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void transfer(Account accountFrom, Account accountTo,
			double transferAmount) {
		// TODO Auto-generated method stub

	}
	
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
