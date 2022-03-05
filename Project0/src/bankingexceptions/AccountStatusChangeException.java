/**
 * AccountStatusChangeException.java
 * 
 * Version 1.0
 * 
 * Mar 04, 2022
 * 
 * Apache-2.0 License 
 */
package bankingexceptions;

public class AccountStatusChangeException extends Exception {
	
	private static final long serialVersionUID = 1371860407641608709L;

	public AccountStatusChangeException(String errorMessage) {
		
		super(errorMessage);
		
	}

}
