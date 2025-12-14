package BankService;

public interface BankServices {
	void addAccount(int accNo, String HolderName, double balance);
	
	void deposit(int accNo,double amount);
	void withDraw(int accNo, double amount);
	void checkBalance(int accNo);
	

}
