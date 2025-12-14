package BankService;

import bank.model.AccountStore;
import bank.model.BankAccount;

public class BankServicesImpl implements BankServices {
	
	@Override
	public void addAccount(int accNo, String HolderName, double balance) {
		BankAccount account = new BankAccount(accNo, HolderName, balance);
		AccountStore.accounts.put(accNo, account);
		System.out.println("Account Created Successfully");
	}

	@Override
	public void deposit(int accNo, double amount) {
	    BankAccount account = AccountStore.accounts.get(accNo);
	    if(account != null) {
	        account.deposit(amount);
	        System.out.println("Amount deposited successfully");
	    } else {
	        System.out.println("There is no account");
	    }
	}

	
	@Override
	public void withDraw(int accNo, double amount) {
	    BankAccount account = AccountStore.accounts.get(accNo);

	    if(account != null) {
	        if(account.withDraw(amount)) {
	            System.out.println("Amount withdrawn successfully");
	        } else {
	            System.out.println("Insufficient balance");
	        }
	    } else {
	        System.out.println("There is no account");
	    }
	}


	@Override
	public void checkBalance(int accNo) {
	    BankAccount account = AccountStore.accounts.get(accNo);

	    if(account != null) {
	        System.out.println("Current Balance: " + account.checkBalance());
	    } else {
	        System.out.println("There is no account");
	    }
	}

}
