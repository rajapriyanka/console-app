package movieticket.model;

public class Users {
	
	private int id;
	private String fname;
	private String lname;
	private int age;
	private double wallet;
	
	public Users(int id,String fname,String lname,int age,double wallet) {
		this.id=id;
		this.fname=fname;
		this.lname=lname;
		this.age=age;
		this.wallet=wallet;
	}
	
	public int getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Users [id=" + id + ", fname=" + fname + ", lname=" + lname + ", age=" + age + ", wallet=" + wallet
				+ "]";
	}

	public String getFname() {
		return fname;
	}
	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public double getWallet() {
		return wallet;
	}
	
	public void setWallet(double wallet) {
		this.wallet=wallet;
	}
	

	

}
