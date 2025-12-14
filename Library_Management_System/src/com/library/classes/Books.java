package com.library.classes;

public class Books {
// Fields
	private int bookId;
	private String bookName;
	private String bookAuthorName;
	private int noOfCopiesAvailable;
	

// Parameterized Constructor
	public Books(int bookId, String bookName, String bookAuthorName, int noOfCopiesAvailable) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.bookAuthorName = bookAuthorName;
		this.noOfCopiesAvailable = noOfCopiesAvailable;
	}
// Getters and Setters
	
	public int getBookId() {
		return bookId;
	}





	public void setBookId(int bookId) {
		this.bookId = bookId;
	}





	public String getBookName() {
		return bookName;
	}





	public void setBookName(String bookName) {
		this.bookName = bookName;
	}





	public String getBookAuthorName() {
		return bookAuthorName;
	}





	public void setBookAuthorName(String bookAuthorName) {
		this.bookAuthorName = bookAuthorName;
	}





	public int getNoOfCopiesAvailable() {
		return noOfCopiesAvailable;
	}





	public void setNoOfCopiesAvailable(int noOfCopiesAvailable) {
		this.noOfCopiesAvailable = noOfCopiesAvailable;
	}
	
	
	
	
	
	public void borrowBook() {
		noOfCopiesAvailable--;
	}
	
	public void returnBook() {
		noOfCopiesAvailable++;
	}
	
	
	

	
	@Override
	public String toString() {
		return " "+bookId+" "+bookName+" "+bookAuthorName+" "+noOfCopiesAvailable+" ";
	}





	
	
	
}
