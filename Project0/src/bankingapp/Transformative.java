/**
 * Transformative.java
 * 
 * Version 1.0
 * 
 * Mar 07, 2022
 * 
 * Apache-2.0 License 
 */
package bankingapp;

/**
 * Interface for users who may modify account balances.
 * 
 * @version 1.0 07 Mar 2022
 * 
 * @author Michael Adams
 *
 */
interface Transformative {
	
	/**
	 * Decreases account balance by the given amount.
	 * 
	 * @param accountFrom Account object to change balance value
	 * @param withdrawAmount (Double) Float value (dollars) to decrease balance value by
	 * @return True if successful; false if transaction failed
	 */
	boolean withdraw(Account accountFrom, double withdrawAmount);
	
	/**
	 * Increases account balance by the given amount.
	 * 
	 * @param accountTo Account object to change balance value
	 * @param depositAmount (Double) Float value (dollars) to decrease balance value by
	 * @return True if successful; false if transaction failed
	 */
	boolean deposit(Account accountTo, double depositAmount);
	
	/**
	 * Changes two account balances. First account has its balanced decreased by the 
	 * given amount; the second, increased. If either action is unsuccessful, any
	 * completed transaction is reversed.
	 * 
	 * @param accountFrom Account object to decrease balance value
	 * @param accountTo Account object to increase balance value
	 * @param transferAmount (Double) Float value (dollars) to decrease balance value by
	 * @return True if completely successful; false if transaction failed
	 */
	boolean transfer(Account accountFrom, Account accountTo, double transferAmount);

}
