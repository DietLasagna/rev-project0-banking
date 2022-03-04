package bankingapp;

import java.util.Scanner;

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
	void menu(Scanner s) {
		
		String userInput = "";
		String inputOptions = "";
		Customer currentCustomer;
		
		// List all customers
		System.out.println(":: Good morning ::");
		BankingApplication.CustomerMap.forEach((k,v) -> System.out.println(k + "\t | "
				+ v.getFullName() + "\t - Accounts: " + v.myAccounts.size()));
		
		System.out.println();
		userInput = BankingApplication.readUserInput(s,
				"Type customer username to view accounts, or 0 to logout").trim();
		
		if(userInput.equalsIgnoreCase("0")) {

			System.out.println("Logged out.");
			return;
			
		} else if (BankingApplication.CustomerMap.containsKey(userInput)) {
			
			currentCustomer = BankingApplication.CustomerMap.get(userInput);
			printCustomerData(currentCustomer);
			System.out.println("\n"
					+ "1 - Approve a new account\n"
					+ "2 - Make a withdrawal\n"
					+ "3 - Make a deposit\n"
					+ "4 - Transfer funds between accounts\n"
					+ "5 - Close an account\n"
					+ "0 - Back\n"
					);
			
			switch(BankingApplication.promptUser(s, "123450")) {
			
			// Approve an account
			case "1":
				// Select account
				if(currentCustomer.myAccounts.size() < 1) {
					
					System.out.println("This customer has no accounts.");
					
				} else {
					
					System.out.println("Please select account to approve:");
					// prompt based on number of accounts in myAccounts
					for(int i = 0; i < currentCustomer.myAccounts.size(); i++) {
						inputOptions = inputOptions.concat(Integer.toString(i + 1));
					}

					approveAccount(currentCustomer.myAccounts.get(Integer
							.parseInt(BankingApplication.promptUser(s, inputOptions)) - 1));
					
				}
				
				break;
				
			// Make a withdrawal
			case "2":
				if(currentCustomer.myAccounts.size() < 1) {
					
					System.out.println("This customer has no accounts.");
					
				} else {
					
					System.out.println("Please select account:");
					// prompt based on number of accounts in myAccounts
					for(int i = 0; i < currentCustomer.myAccounts.size(); i++) {
						inputOptions = inputOptions.concat(Integer.toString(i + 1));
					}
					
					withdraw(currentCustomer.myAccounts.get(Integer.parseInt(BankingApplication.promptUser(s, inputOptions)) - 1),
							BankingApplication.readUserAmount(s));
				
				}
				
				break;// Make a deposit
			case "3":
				if(currentCustomer.myAccounts.size() < 1) {
					System.out.println("This customer has no accounts.");
				} else {
					System.out.println("Please select account");
					// prompt based on number of accounts in myAccounts
					for(int i = 0; i < currentCustomer.myAccounts.size(); i++) {
						inputOptions = inputOptions.concat(Integer.toString(i + 1));
					}
					
					deposit(currentCustomer.myAccounts.get(Integer.parseInt(BankingApplication.promptUser(s, inputOptions)) - 1),
							BankingApplication.readUserAmount(s));
				}
				break;
		
			// Transfer between accounts
			case "4":
				if(currentCustomer.myAccounts.size() < 2) {
					System.out.println("This customer has too few accounts.");
				} else {
					System.out.println("Please select account to transfer from, then an account to transfer to:");
					// prompt based on number of accounts in myAccounts
					for(int i = 0; i < currentCustomer.myAccounts.size(); i++) {
						inputOptions = inputOptions.concat(Integer.toString(i + 1));
					}
					
					transfer(currentCustomer.myAccounts.get(Integer.parseInt(BankingApplication.promptUser(s, inputOptions)) - 1),
							currentCustomer.myAccounts.get(Integer.parseInt(BankingApplication.promptUser(s, inputOptions)) - 1),
							BankingApplication.readUserAmount(s));
					
				}
				// 
				break;
				
			// Close an approved account
			case "5":
				// Select account
				if(currentCustomer.myAccounts.size() < 1) {
					
					System.out.println("This customer has no accounts.");
					
				} else {
					
					System.out.println("Please select account to close:");		// NEED to allow more than one approval before going back - how to clear inputOptions?
					// prompt based on number of accounts in myAccounts
					for(int i = 0; i < currentCustomer.myAccounts.size(); i++) {	// NEED to account for more than 9 accounts
						inputOptions = inputOptions.concat(Integer.toString(i + 1));
					}

					closeAccount(currentCustomer.myAccounts.get(Integer
							.parseInt(BankingApplication.promptUser(s, inputOptions)) - 1));
					
				}
				
				break;
		
			default:
			case "0":
				// back
				
			}
			
			
		} else {
			
			System.out.println("Invalid input.");
			
		}
		
		menu(s);
		
	}

	@Override
	public boolean withdraw(Account accountFrom, double withdrawAmount) {
		
		boolean isComplete = false;
		double accountBalance = accountFrom.getBalance();

		// check if account is approved
		if(accountFrom.getStatus() != 1) {
			
			System.out.println("Account unavailable.");
			return false;
			
		}
		
		if(withdrawAmount < accountBalance) {
			
			accountFrom.setBalance(accountBalance - withdrawAmount);
			isComplete = true;
			
		} else {
			
			System.out.println("Insufficient funds. Transaction cancelled.");
			
		}
		
		return isComplete;
		
	}

	@Override
	public boolean deposit(Account accountTo, double depositAmount) {

		boolean isComplete = false;
		
		// check if account is approved
		if(accountTo.getStatus() != 1) {
			
			System.out.println("Account unavailable.");
			return false;
			
		}
		
//		if() {
			
			accountTo.setBalance(accountTo.getBalance() + depositAmount);
			isComplete = true;
			
//		} else {
//			
//			System.out.println("Error. Transaction cancelled.");
//			
//		}
		
		return isComplete;
		
	}

	@Override
	public boolean transfer(Account accountFrom, Account accountTo,
			double transferAmount) {

		boolean isComplete = false;
		
		if(withdraw(accountFrom, transferAmount)) {
			
			if(deposit(accountTo, transferAmount)) {
			
				isComplete = true;
			
			} else {
				
				// reverse the action of the withdraw to cancel the transfer
				deposit(accountFrom, transferAmount);
				
			}
		}
		
		return isComplete;
		
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
