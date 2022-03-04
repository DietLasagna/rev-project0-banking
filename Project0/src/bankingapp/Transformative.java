package bankingapp;

public interface Transformative {
	
	boolean withdraw(Account accountFrom, double withdrawAmount);
	
	boolean deposit(Account accountTo, double depositAmount);
	
	boolean transfer(Account accountFrom, Account accountTo, double transferAmount);

}
