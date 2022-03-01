package bankingapp;

public interface Transformative {
	
	void withdraw(Account accountFrom, double withdrawAmount);
	
	void deposit(Account accountTo, double depositAmount);
	
	void transfer(Account accountFrom, Account accountTo, double transferAmount);

}
