/**
 * TransactionEvent.java
 * 
 * Version 0.5
 * 
 * Mar 04, 2022
 * 
 * Apache-2.0 License 
 */
package bankingapp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A Transaction Event records details of a change in balance of an Account.
 * 
 * @version 0.5 04 Mar 2022
 * 
 * @author Master
 *
 */
public class TransactionEvent implements Serializable {
	
	private static final long serialVersionUID = -8070826080198750371L;
	final private String eventType;
	final private double balanceChange;
	final private LocalDateTime eventTime;
	final private String eventTimeStamp;
	
	public TransactionEvent(String event, double amount) {
		
		this.eventType = event;
		this.balanceChange = amount;
		this.eventTime = LocalDateTime.now();
		this.eventTimeStamp = DateTimeFormatter.ofPattern(
				"HH:mm:ss yyyy/MM/dd").format(eventTime);
		
	}

	/** 
	 * Getter for TransactionEvent "eventType".
	 * @return The String eventType
	 */
	String getEventType() {
		
		return eventType;
		
	}

	/**
	 * Getter for TransactionEvent "balanceChange".
	 * @return The double float dollar amount balanceChange
	 */
	double getBalanceChange() {
		
		return balanceChange;
		
	}

	/**
	 * Getter for TransactionEvent "eventTimeStamp".
	 * @return The String eventTimeStamp
	 */
	String getEventTimeStamp() {
		
		return eventTimeStamp;
		
	}
}
