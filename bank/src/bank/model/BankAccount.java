package bank.model;

public class BankAccount {
	
	private int accNo;
	private String HolderName;
	private double balance;
	
	public BankAccount(int accNo, String HolderName , double balance) {
		this.accNo=accNo;
		this.HolderName=HolderName;
		this.balance=balance;
	}
	public int getAccountNo() {
		return accNo;
	}
	public String getHolderName() {
		return HolderName;
	}
	public double checkBalance() {
		return balance;
	}
	public void deposit(double amount) {
		balance +=amount;
	}
	public boolean withDraw(double amount) {
		if(amount <= balance) {
			balance -= amount;
			return true;
			
		}
		return false;
	}
	@Override
    public String toString() {
        return "BankAccount {" +
                "Account No = " + accNo +
                ", Holder Name = '" + HolderName + '\'' +
                ", Balance = " + balance +
                '}';
    }

}
