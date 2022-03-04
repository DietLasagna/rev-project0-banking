package bankingapp;

import java.io.Serializable;

//import java.util.LinkedHashSet;
import bankingexceptions.*;

public class Account implements Serializable {
	
	/**
	 * 	isJoint (false = single, true = joint account)
	 *  Status (0 Open, 1 Approved, 2 Denied)
	 *  Balance (double >= 0.00)
	 */
	private static final long serialVersionUID = 6741064967516562360L;
	final private boolean isJoint;
	private byte status;
	private double balance;
	
	public Account(boolean isJoint) {
		
		this.isJoint = isJoint;
		this.status = (byte) 0;
		this.setBalance(0.00);
		
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
			
			if(statusUpdate == 1) {
				
				this.setBalance(0.00);
				
			}
			
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

	public boolean isJoint() {
		return isJoint;
	}
	
}
