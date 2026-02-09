package flight.entity;

public class Passenger {
	
  private String userName;
  private String password;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private double wallet;

  @Override
  public String toString() {
	return "Passenger [userName=" + userName + ", password=" + password + ", firstName=" + firstName + ", lastName="
			+ lastName + ", email=" + email + ", phone=" + phone + ", wallet=" + wallet + "]";
}


  public Passenger(String userName, String password, String firstName, String lastName, String email, String phone,
		double wallet) {
	super();
	this.userName = userName;
	this.password = password;
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.phone = phone;
	this.wallet = wallet;
}


  public String getPassword() {
	return password;
}


  public void setPassword(String password) {
	this.password = password;
  }
  public double getWallet() {
	return wallet;
}


  public void setWallet(double wallet) {
	this.wallet = wallet;
  }


  public String getUserName() {
   return userName;
}


  public String getFirstName() {
	return firstName;
}


  public void setFirstName(String firstName) {
	this.firstName = firstName;
}


  public String getLastName() {
	return lastName;
}


  public void setLastName(String lastName) {
	this.lastName = lastName;
}


  public String getEmail() {
	return email;
}


  public void setEmail(String email) {
	this.email = email;
}


  public String getPhone() {
	return phone;
}


  public void setPhone(String phone) {
	this.phone = phone;
}


  public void setUserName(String userName) {
	this.userName = userName;
}


}