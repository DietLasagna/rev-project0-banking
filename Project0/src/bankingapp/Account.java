package bankingapp;

import java.util.LinkedHashSet;
import bankingexceptions.*;

public class Account {
	
	/**
	 * Accounts have four properties:
	 *  ID number (automatically assigned in constructor)
	 *  isJoint (false = single, true = joint account)
	 *  Status (0 Open, 1 Approved, 2 Denied)
	 *  Balance (double >= 0.00)
	 */
	
	final private short accountNumber;
	final private boolean isJoint;
	private byte status;
	private double balance;
	public static LinkedHashSet<Account> allAccounts; // populate from deserialize
	
	public Account(boolean isJoint) {
		
		this.accountNumber = (short) (1000 + allAccounts.size());
		this.isJoint = isJoint;
		this.status = (byte) 0;
		this.setBalance(0.00);
		
		allAccounts.add(this);
		
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte statusUpdate) throws AccountStatusChangeException {
		
		if(statusUpdate < 0 || statusUpdate > 2) {
			
			throw new AccountStatusChangeException("Unallowed account status used");
			
		}
		
		if(this.status < statusUpdate) {
			
			this.status = statusUpdate;
			
		} else {
			
			switch(statusUpdate) {
			case (byte) 0:
				throw new AccountStatusChangeException(
						"Cannot revert account to [OPEN]");
				// break;
			case (byte) 1:
				throw new AccountStatusChangeException(
						"Account not available for approval");
				// break;
			case (byte) 2:
				throw new AccountStatusChangeException(
						"Account already closed");
				// break;
			}
			
		}
		
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public short getAccountNumber() {
		return accountNumber;
	}

	public boolean isJoint() {
		return isJoint;
	}
	
}
