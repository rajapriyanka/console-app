package com.library.librarymanagement;




import java.util.List;

import com.library.classes.Books;
import com.library.classes.Patrons;

public interface Library {

	public abstract void addBook(Books book);

	public abstract void addAllBooks(List<Books> books);

	public abstract void findBookById(int id);

	public abstract void findBookByName(String name);

	public abstract void listAllBooks();

	public abstract void addPatron(Patrons patron);

	public abstract void addAllPatrons(List<Patrons> patrons);

	public abstract void findPatronById(int patronId);

	public abstract void findPatronByName(String patronName);

	public abstract void listAllPatrons();

	public abstract void borrowBook(int bookId, int patronId);

	public abstract void returnBook(int recordId);

	public abstract void showBorrowHistory();

}

