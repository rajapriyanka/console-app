package com.library.librarymanagement;

import java.time.LocalDate;
import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.library.classes.Books;
import com.library.classes.History;
import com.library.classes.Patrons;

public class LibraryManagement implements Library {

	private final List<Books> bookDb = new ArrayList<>();
	private final List<Patrons> patronDb = new ArrayList<>();

	private final Map<Integer, Books> bookMap = new HashMap<>(); // Fast lookup for books
	private final Map<Integer, Patrons> patronMap = new HashMap<>(); // Fast lookup for patrons

	private final Map<Integer, History> borrowTracker = new HashMap<>();

	AtomicInteger generateHistoryID = new AtomicInteger(1);

	@Override
	public void addBook(Books book) {
		if (book == null) {
			System.out.println("\nKindly check the book details before adding!\n");
			return;
		}

		if (bookMap.containsKey(book.getBookId())) {
			System.out.println("\nThe book already exists. Please check the Book ID.\n");
		} else {
			bookDb.add(book);
			bookMap.put(book.getBookId(), book);
			System.out.println("\nThe book \"" + book.getBookName() + "\" was successfully added to the library.\n");
		}
	}

	@Override
	public void addAllBooks(List<Books> books) {
		if (books == null || books.isEmpty()) {
			System.out.println("No books provided to add!");
			return;
		}
		for (Books book : books) {
			addBook(book);
		}
	}

	@Override
	public void listAllBooks() {
		System.out.println("\n+------------+----------------------+----------------------+----------------------+");
		System.out.printf("| %-10s | %-20s | %-20s | %-20s |%n", "Book ID", "Book Name", "Author Name",
				"Copies Available");
		System.out.println("+------------+----------------------+----------------------+----------------------+");

		for (Books book : bookDb) {
			System.out.printf("| %-10d | %-20s | %-20s | %-20d |%n", book.getBookId(), book.getBookName(),
					book.getBookAuthorName(), book.getNoOfCopiesAvailable());
		}

		System.out.println("+------------+----------------------+----------------------+----------------------+");
	}

	@Override
	public void findBookById(int bId) {
		bookCheck(bookMap.get(bId));
	}

	@Override
	public void findBookByName(String name) {
		Books foundBook = null;
		for (Books book : bookDb) {
			if (book.getBookName().equalsIgnoreCase(name)) {
				foundBook = book;
				break;
			}
		}
		bookCheck(foundBook);
	}

	@Override
	public void addPatron(Patrons patron) {
		if (patron == null) {
			System.out.println("\nKindly check the patron details before adding!\n");
			return;
		}

		if (patronMap.containsKey(patron.getPatronId())) {
			System.out.println("The patron ID already exists. Please enter a different ID.");
		} else {
			patronDb.add(patron);
			patronMap.put(patron.getPatronId(), patron);
			System.out.println("\nThank you! " + patron.getPatronName() + " is now a registered library member.\n");
		}
	}

	@Override
	public void addAllPatrons(List<Patrons> patrons) {
		if (patrons == null || patrons.isEmpty()) {
			System.out.println("No patrons provided to add!");
			return;
		}
		for (Patrons patron : patrons) {
			addPatron(patron);
		}
	}

	@Override
	public void listAllPatrons() {
		System.out.println("\n+------------+----------------------+------------+");
		System.out.printf("| %-10s | %-20s | %-10s |%n", "Patron ID", "Patron Name", "Patron Age");
		System.out.println("+------------+----------------------+------------+");

		for (Patrons patron : patronDb) {
			System.out.printf("| %-10d | %-20s | %-10d |%n", patron.getPatronId(), patron.getPatronName(),
					patron.getPatronAge());
		}

		System.out.println("+------------+----------------------+------------+");
	}

	@Override
	public void findPatronById(int patronId) {
		Patrons patron = getPatronById(patronId);
		patronCheck(patron);
	}

	@Override
	public void findPatronByName(String patronName) {
		Patrons patron = getPatronByName(patronName);
		patronCheck(patron);
	}

	public void borrowBook(int bookId, int patronId) {
		Books book = bookMap.get(bookId);
		Patrons patron = patronMap.get(patronId);

		if (book == null) {
			System.out.println("\nSorry!! The requested book is not available in the library.");
			return;
		}
		if (patron == null) {
			System.out.println("\nPlease register as a library member to borrow this book.");
			return;
		}

		int recordId = generateHistoryID.getAndIncrement();
		if (book.getNoOfCopiesAvailable() > 0) {
			// Track borrowing event
//			LocalTime returnTime = LocalTime.now();
//	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//	        String returnFormattedTime = returnTime.format(formatter);
			patron.borrowBook(book);
			book.borrowBook();
			History history = new History.Builder().setBookId(bookId).setBookName(book.getBookName())
					.setPreviousCopies(book.getNoOfCopiesAvailable()+patron.getBorrowCount(book))
					.setCurrentCopies(book.getNoOfCopiesAvailable() ).setPatronId(patronId)
					.setPatronName(patron.getPatronName()).setBorrowCount(patron.getBorrowCount(book))
					.setBorrowDate(LocalDate.now()).setBorrowTime(LocalTime.now()).build();

			borrowTracker.put(recordId, history); // Track who borrowed it

			

			System.out.println(
					"\nThe book \"" + book.getBookName() + "\" was BORROWED by " + patron.getPatronName() + ".");
		} else {
			System.out.println("\nSorry!! The book \"" + book.getBookName() + "\" is currently out of stock!\n");
		}
	}

	public void showBorrowHistory() {
		if (borrowTracker.isEmpty()) {
			System.out.println("\nNo Record history available.");
			return;
		}

		printBorrowHistoryHeader();

		for (Map.Entry<Integer, History> entry : borrowTracker.entrySet()) {
            int recordId = entry.getKey();
			History history = entry.getValue();
			int bookId = history.getBookId();
			Books book = bookMap.get(bookId);
			if (book == null)
				continue;

			int previousCopies = book.getNoOfCopiesAvailable() + history.getBorrowCount();
			int currentCopies = book.getNoOfCopiesAvailable();

			int patronId = history.getPatronId();
			Patrons patron = patronMap.get(patronId);

			String patronName = (patron != null) ? patron.getPatronName() : "Unknown";
			LocalDate borrowDate = (patron != null) ? history.getBorrowDate() : null;
			LocalDate returnDate = (patron != null) ? history.getReturnDate() : null;

			System.out.printf(
					"| %-10d | %-10d | %-20s | %-17d | %-17d | %-10d | %-20s | %-20d | %-20s | %-20s | %-20s | %-20s |%n",
					recordId,book.getBookId(), book.getBookName(), previousCopies, currentCopies, patronId, patronName,
					history.getBorrowCount(), borrowDate, history.getBorrowTime(), returnDate, history.getReturnTime());
		}

		System.out.println(
				"+------------+------------+----------------------+-------------------+-------------------+------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+");
		System.out.println();
	}

	public void returnBook(int recordId) {

		History history = borrowTracker.get(recordId);

		if (history == null) {
			System.out.println("\nInvalid history ID! No matching borrow record found.");
			return;
		}

		Books book = bookMap.get(history.getBookId());
		Patrons patron = patronMap.get(history.getPatronId());

		if (book == null || patron == null) {
			System.out.println("Error: Book or Patron not found.");
			return;
		}
		book.returnBook();
		patron.returnBook(book);
		History updatedHistory = new History.Builder().setBookId(history.getBookId()).setBookName(history.getBookName())
				.setPreviousCopies(history.getPreviousCopies()).setCurrentCopies(book.getNoOfCopiesAvailable())
				.setPatronId(history.getPatronId()).setPatronName(history.getPatronName())
				.setBorrowCount(patron.getBorrowCount(book)).setBorrowDate(history.getBorrowDate())
				.setBorrowTime(history.getBorrowTime()).setReturnDate(LocalDate.now()).setReturnTime(LocalTime.now())
				.build();

		borrowTracker.put(recordId, updatedHistory);

		System.out.println("The Book \"" + book.getBookName() + "\" was Successfully Returned by "
				+ patron.getPatronName() + " on " + LocalDate.now() + " | " + LocalTime.now());
	}

//	utility methods
	public boolean isUnique(Books book) {
		for (Books books : bookDb) {
			if (books.getBookId() == book.getBookId()) {
				return false;
			}
		}
		return true;
	}

	public boolean uniquePatron(Patrons patron) {
		for (Patrons patrons : patronDb) {
			if (patrons.getPatronId() == patron.getPatronId()) {
				return false;
			}
		}
		return true;
	}

	public Patrons getPatronByName(String patronName) {
		for (Patrons patron : patronDb) {
			if (patron.getPatronName().equalsIgnoreCase(patronName)) {
				return patron;
			}
		}
		return null;
	}

	public Patrons getPatronById(int patronId) {
		for (Patrons patron : patronDb) {
			if (patron.getPatronId() == patronId) {
				return patron;
			}
		}
		return null;

	}

//	public Map.Entry<Books, Patrons> checkBookId(int bookId) {
//
//		for (Map.Entry<Books, Patrons> entry : borrowDb.entrySet()) {
//			Books book = entry.getKey();
//			Patrons patron = entry.getValue();
//
//			if (book.getBookId() == bookId && patron.getBorrowCount(book) > 0) {
//				return entry;
//			}
//		}
//		return null;
//
//	}
//	helping both bookbyID and bookbyNAME

//	public Books getBookById(int bId) {
//		for (Books book : bookDb) {
//			if (book.getBookId() == bId) {
//				return book;
//			}
//		}
//		return null;
//	}
//
//	public Books getBookByName(String name) {
//
//		for (Books book : bookDb) {
//			if (book.getBookName().equalsIgnoreCase(name)) {
//				return book;
//			}
//		}
//		return null;
//	}

	private void bookCheck(Books book) {
		if (book != null) {
			System.out.println("\nBook found: " + book.getBookName());
		} else {
			System.out.println("\nBook not found!\n");
		}

	}

	// helping both Patron by ID and patron by NAME
	public void patronCheck(Patrons patron) {
		if (patron != null) {
			System.out.println();
			System.out.println("+------------+----------------------+------------+");
			System.out.printf("| %-10s | %-20s | %-10s |%n", "Patron ID", "Patron Name", "Patron Age");
			System.out.println("+------------+----------------------+------------+");

			System.out.printf("| %-10d | %-20s | %-10d |%n", patron.getPatronId(), patron.getPatronName(),
					patron.getPatronAge());

			System.out.println("+------------+----------------------+------------+");

		} else {
			System.out.println();
			System.out.println("Patron  Not found!!");
			System.out.println();

		}

	}

	private void printBorrowHistoryHeader() {
		System.out.println(
				"+------------+------------+----------------------+-------------------+-------------------+------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+");
		System.out.printf("| %-10s | %-10s | %-20s | %-17s | %-17s | %-10s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |%n",
				"Record ID","Book ID", "Book Name", "Previous Copies", "Current Copies", "Patron ID", "Patron Name",
				"Borrow Book Count", "Borrowed Date", "Borrowed Time", "Return Date", "Return Time");
		System.out.println(
				"+------------+------------+----------------------+-------------------+-------------------+------------+----------------------+----------------------+----------------------+----------------------+----------------------+----------------------+");
	}
}
