/**
 * Account.java
 * 
 * Version 0.6
 * 
 * Mar 05, 2022
 * 
 * Apache-2.0 License 
 */
package bankingapp;

import java.io.Serializable;
import java.util.LinkedHashSet;
import bankingexceptions.*;

/**
 * Account Class provides structure for Customers' banking accounts. Accounts initialize
 * with a status of "Open", requiring the bank to accept or reject the application before
 * use. Accounts hold a positive, dollar amount balance while status of "Approved"; and
 * are inaccessible once set to "Denied". Some accounts may be "joint" account, shared
 * between two different Customer users.
 * 
 * @version 0.6 05 Mar 2022
 * 
 * @author Michael Adams
 *
 */
public class Account implements Serializable {
	
	private static final long serialVersionUID = 6741064967516562360L;
	/** Joint accounts are shared between two Customer users */
	final private boolean isJoint;
	/** Status can be: 0 = Open | 1 = Approved | 2 = Denied */
	private byte status;
	/** Dollar amount held in Account */
	private double balance;
	/** Ordered set of all Transaction Events */
	private LinkedHashSet<TransactionEvent> eventHistory;
	
	public Account(boolean isJoint) {
		
		this.isJoint = isJoint;
		this.status = (byte) 0;
		this.setBalance(0.00d);
		this.eventHistory = new LinkedHashSet<TransactionEvent>();
		
	}
	
	/**
	 * Setter for Account status. Includes controls for allowable state changes.
	 * @param statusUpdate Desired byte number status
	 * @throws AccountStatusChangeException
	 */
	void setStatus(byte statusUpdate) throws AccountStatusChangeException {
		
		if(statusUpdate < 0 || statusUpdate > 2) {
			
			throw new AccountStatusChangeException("Unallowed account status used");
			
		}
		
		if(this.status < statusUpdate) {
			
			this.setBalance(0.00d);
			this.status = statusUpdate;
			
		} else {
			
			switch(statusUpdate) {
			
			case (byte) 0:
				throw new AccountStatusChangeException(
						"Cannot revert account to [OPEN].");
				// break;
			
			case (byte) 1:
				throw new AccountStatusChangeException(
						"Account not available for approval.");
				// break;
			
			case (byte) 2:
				throw new AccountStatusChangeException(
						"Account already closed.");
				// break;
			
			}
			
		}
		
	}
	
	/**
	 * Create next entry in Account event history log.
	 * @param event String (Deposit | Withdrawal | Transfer from | Transfer to)
	 * @param amount Double float dollar amount
	 */
	void addEvent(String event, double amount) {
		
		eventHistory.add(new TransactionEvent(event, amount));
		
	}
	
	void printEventLog() {
		
		if(status == 1) {
			
			System.out.println("---------------------------------------------------------");
			
			for(TransactionEvent e : eventHistory) {
				
				System.out.println("| " + e.getEventType() 
								+ "\t| " + String.format("%12.2f", e.getBalanceChange())
								+ "\t|  " + e.getEventTimeStamp() + "\t|");
				
			}
			
			System.out.println("---------------------------------------------------------");
		
		} else {
			
			System.out.println("Account logs unavailable.");
			
		}
		
	}
	
	/**
	 * Getter for Account "status".
	 * @return Byte number status
	 */
	byte getStatus() {
		
		return status;
		
	}
	
	/**
	 * Getter for Account "balance".
	 * @return Double float dollar balance
	 */
	double getBalance() {
		
		return balance;
		
	}
	
	/**
	 * Setter for Account "balance".
	 * @param balance Updated double float dollar balance
	 */
	void setBalance(double balance) {
		
		this.balance = balance;
		
	}
	
	/**
	 * Getter for Account "isJoint" indicator.
	 * @return Whether Account is a Joint Account
	 */
	boolean isJoint() {
		
		return isJoint;
		
	}
	
}
