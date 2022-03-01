package bankingexceptions;

public class AccountStatusChangeException extends Exception {
	
	public AccountStatusChangeException(String errorMessage) {
		super(errorMessage);
	}

}
