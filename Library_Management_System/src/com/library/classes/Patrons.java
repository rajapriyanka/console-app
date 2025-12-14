package com.library.classes;

import java.util.HashMap;
import java.util.Map;

public class Patrons {
	
	private int patronId;
	private String patronName;
	private int patronAge;
//	private LocalDate borrowDate;
//	private LocalDate returnDate;
	
	private Map<Books,Integer> borrowedBooks = new HashMap<>();
	
	
	public Patrons(int patronId, String patronName, int patronAge) {
		super();
		this.patronId = patronId;
		this.patronName = patronName;
		this.patronAge = patronAge;
	}


	




	public int getPatronId() {
		return patronId;
	}







	public void setPatronId(int patronId) {
		this.patronId = patronId;
	}







	public String getPatronName() {
		return patronName;
	}







	public void setPatronName(String patronName) {
		this.patronName = patronName;
	}







	public int getPatronAge() {
		return patronAge;
	}







	public void setPatronAge(int patronAge) {
		this.patronAge = patronAge;
	}
	
	


//
//	public LocalDate getBorrowDate() {
//		return borrowDate;
//	}
//
//
//
//
//
//
//
//	public void setBorrowDate(LocalDate borrowDate) {
//		this.borrowDate = borrowDate;
//	}
//
//
//
//
//
//
//
//	public LocalDate getReturnDate() {
//		return returnDate;
//	}
//
//
//
//
//
//
//
//	public void setReturnDate(LocalDate returnDate) {
//		this.returnDate = returnDate;
//	}
//
//
//




	public void borrowBook(Books book) {
		
		borrowedBooks.put(book, borrowedBooks.getOrDefault(book, 0)+1);
	}
	
	public void returnBook(Books book) {
		if(borrowedBooks.containsKey(book)) {
			int count = borrowedBooks.get(book);
			if(count>1) {
				borrowedBooks.put(book, count-1);
			}else {
				borrowedBooks.remove(book);
			}
		}
	}

	public int getBorrowCount(Books book) {
        return borrowedBooks.getOrDefault(book, 0);
    }

	




	@Override
	public String toString() {
		return " " + patronId + " " + patronName + " " + patronAge + " ";
	}

	
	

}
