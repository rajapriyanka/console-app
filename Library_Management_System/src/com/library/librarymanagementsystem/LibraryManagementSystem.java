package com.library.librarymanagementsystem;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.library.classes.Books;
import com.library.classes.Patrons;
import com.library.librarymanagement.Library;
import com.library.librarymanagement.LibraryManagement;

public class LibraryManagementSystem {
	public static final Scanner scan = new Scanner(System.in);

	public static final Library libraryManagement = new LibraryManagement();

	static Books book;

	static Patrons patron;

	public static void main(String[] args) {
		int choice = 0;

		do {
			try {
				showMenu();

				choice = scan.nextInt();

				handleUserChoice(choice);
			} catch (InputMismatchException e) {
				System.out.println("Please Enter the correct Choice!!");
				scan.nextLine();
			} catch (Exception e) {
				System.out.println("An unexpected error occurred: " + e.getMessage());
			}
		} while (choice != 14);
		scan.close();

	}

	private static void handleUserChoice(int choice) {
		switch (choice) {
		case 1:
			addBooks();
			break;
		case 2:
			addMultipleBooks();
			break;
		case 3:
			System.out.println("these BOOKS are AVAILABLE in this LIBRARY \n");
			libraryManagement.listAllBooks();
			break;
		case 4:
			findByBookId();
			break;
		case 5:
			findByBookName();
			break;
		case 6:
			addPatron();
			break;
		case 7:
			addMultiplePatrons();
			break;
		case 8:
			System.out.println("The only Patrons are AVAILABLE in this LIBRARY as Members. \n");
			libraryManagement.listAllPatrons();
			break;
		case 9:
			findByPatronId();
			break;
		case 10:
			findByPatronName();
			break;
		case 11:
			borrowBook();
			break;
		case 12:
			libraryManagement.showBorrowHistory();
			break;
		case 13:
			returnBook();
			break;
		case 14:
			System.out.println("thank you for visited our Library_-!....#@#....!-_bye1bye......./");
			break;
		default:
			System.out.println("please ensure the input");
			break;
		}
//		} catch (NullPointerException e) {
//			System.err.println("Null value encountered: " + e.getMessage());
//		} catch (InputMismatchException e) {
//			System.out.println("Invalid input! Please enter a valid number.");
//			scan.nextLine(); // Clear invalid input
//		} catch (Exception e) {
//			System.out.println("An unexpected error occurred: " + e.getMessage());
//		}

	}

	private static void returnBook() {
		try {
			System.out.println("Enter the Record ID: ");
			int recordId = scan.nextInt();
			libraryManagement.returnBook(recordId);
		} catch (InputMismatchException e) {
			System.out.println("Invalid input! Please enter a Vaild Book ID.");
			scan.nextLine(); // Clear invalid input
		} catch (Exception e) {
			System.out.println("An unexpected error occurred!!");
//			e.printStackTrace(); // Print full stack trace for debugging
		}

	}

	private static void borrowBook() {
		try {
			System.out.println("Enter the book ID: ");
			int bookId = scan.nextInt();
			System.out.println("Enter the patron ID: ");
			int patronId = scan.nextInt();
			libraryManagement.borrowBook(bookId, patronId);
		} catch (InputMismatchException e) {
			System.out.println("Invalid input! Please enter a Vaild Book ID and Vaild Patron ID.");
			scan.nextLine(); // Clear invalid input
		} catch (Exception e) {
			System.out.println("An unexpected error occurred!!");
//			e.printStackTrace(); // Print full stack trace for debugging
		}
	}

	private static void findByPatronName() {
		try {
			System.out.println("Enter the patron Name: ");
			scan.nextLine();
			String patronName = scan.nextLine().trim(); // Trim to remove extra spaces

			if (patronName.isEmpty()) {
				System.out.println("Invalid input! Patron name cannot be empty.");
				return; // Exit early
			}

			if (!patronName.matches("[a-zA-Z ]+")) {
				System.out.println("Invalid input! Patron name should only contain letters and spaces.");
				return; // Exit early
			}

			libraryManagement.findPatronByName(patronName);
		} catch (Exception e) {
			System.out.println("An unexpected error occurred!!");
//	        e.printStackTrace(); // Print full stack trace for debugging
		}
	}

	private static void findByPatronId() {
		try {
			System.out.println("Enter the patron ID: ");
			int patronId = scan.nextInt();

			// Validate input manually before proceeding
			if (patronId <= 0) {
				throw new IllegalArgumentException("Patron ID must be a positive number.");
			}

			libraryManagement.findPatronById(patronId);
		} catch (InputMismatchException e) {
			System.out.println("Invalid input! Please enter a valid numeric Patron ID.");
			scan.nextLine(); // Clear invalid input
		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
//		    e.printStackTrace(); // Print full stack trace for debugging
		}

	}

	private static void addMultiplePatrons() {
		List<Patrons> patronList = new ArrayList<>(); // ✅ Store patrons in a list

		try {
			System.out.println("Enter the number of patrons: ");
			int noOfPatrons = scan.nextInt();
			scan.nextLine(); // Consume newline

			for (int i = 1; i <= noOfPatrons; i++) {
				System.out.println("\nAdding Patron " + i + " of " + noOfPatrons);
				Patrons patron = addPatronDetails(); // ✅ Get patron object

				if (patron != null) {
					patronList.add(patron); // ✅ Add patron to the list
				} else {
					System.out.println("Skipping invalid patron entry.");
				}
			}

			// ✅ Call addAllPatrons() with the list
			if (!patronList.isEmpty()) {
				libraryManagement.addAllPatrons(patronList);
				System.out.println("\nAll patrons added successfully!");
			} else {
				System.out.println("\nNo valid patrons were added.");
			}

		} catch (InputMismatchException e) {
			System.out.println("Invalid input! Please enter a valid number.");
			scan.nextLine(); // Clear scanner buffer
		} catch (Exception e) {
			System.out.println("An unexpected error occurred.");
		}
	}

	// ✅ Updated to return a Patrons object instead of boolean
	private static Patrons addPatronDetails() {
		try {
			System.out.println("Enter the patron ID: ");
			int patronId = scan.nextInt();
			scan.nextLine(); // Consume newline

			if (patronId <= 0) {
				throw new IllegalArgumentException("Patron ID must be a positive number.");
			}

			System.out.println("Enter the patron name: ");
			String patronName = scan.nextLine().trim();

			if (!patronName.matches("[a-zA-Z ]+")) {
				System.out.println("Invalid input! Patron name should only contain letters and spaces.");
				return null;
			}

			System.out.println("Enter the patron age: ");
			int patronAge = scan.nextInt();
			scan.nextLine(); // Consume newline

			if (patronAge <= 0) {
				System.out.println("Invalid input! Age must be greater than 0.");
				return null;
			}

			return new Patrons(patronId, patronName, patronAge); // ✅ Return a valid patron object

		} catch (InputMismatchException e) {
			System.out.println("Invalid input! Please enter correct values.");
			scan.nextLine(); // Clear scanner buffer
			return null;
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		} catch (Exception e) {
			System.out.println("An unexpected error occurred!");
			return null;
		}
	}

	private static void findByBookName() {
		try {
			scan.nextLine(); // Consume any leftover newline character
			System.out.println("Enter the book NAME: ");
			String bookName = scan.nextLine().trim(); // Trim to remove extra spaces

			if (bookName.isEmpty()) {
				System.out.println("Invalid input! Book name cannot be empty.");
				return; // Exit early
			}

			if (!bookName.matches("[a-zA-Z ]+")) {
				System.out.println("Invalid input! Book name should only contain letters and spaces.");
				return; // Exit early
			}

			libraryManagement.findBookByName(bookName);
		} catch (Exception e) {
			System.out.println("An unexpected error occurred!!");
			e.printStackTrace(); // Print full stack trace for debugging
		}

	}

	private static void findByBookId() {
		try {
			System.out.println("Enter the book ID: ");
			int bookId = scan.nextInt();

			// Validate input manually before proceeding
			if (bookId <= 0) {
				throw new IllegalArgumentException("Patron ID must be a positive number.");
			}

			libraryManagement.findBookById(bookId);
		} catch (InputMismatchException e) {
			System.out.println("Invalid input! Please enter a valid numeric Patron ID.");
			scan.nextLine(); // Clear invalid input
		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
//		    e.printStackTrace(); // Print full stack trace for debugging
		}

	}

	private static void addMultipleBooks() {
		System.out.println("Enter the Number of Books: ");
		int noOfBooks = scan.nextInt();
		List<Books> bookList = new ArrayList<>();
		for (int i = 1; i <= noOfBooks; i++) {
			System.out.println("Enter the book id: ");
			int bookId = scan.nextInt();
			scan.nextLine();
			System.out.println("Enter the book name: ");
			String bookName = scan.nextLine();
			System.out.println("Enter the book Author name: ");
			String authorName = scan.nextLine();
			System.out.println("Enter the available copies: ");
			int noOfCopies = scan.nextInt();
			book = new Books(bookId, bookName, authorName, noOfCopies);
			bookList.add(book);
		}
		libraryManagement.addAllBooks(bookList);

	}

	private static void showMenu() {
		System.out.println("\n=========================================================\n"
				+ "|                                                       |\n"
				+ "|         WELCOME TO LIBRARY MANAGEMENT SYSTEM          |\n"
				+ "|                                                       |\n"
				+ "=========================================================\n"
				+ "| Please select an option from the menu below:          |\n"
				+ "+-------------------------------------------------------+\n" +

				"|  1  | Add a NEW BOOK to the library                   |\n"
				+ "|  2  | Add MULTIPLE BOOKS to the library               |\n"
				+ "|  3  | List ALL BOOKS in the library                   |\n"
				+ "|  4  | Find a BOOK BY BOOK ID                          |\n"
				+ "|  5  | Find a BOOK BY BOOK NAME                        |\n"
				+ "_________________________________________________________\n"
				+ "|  6  | Add a NEW PATRON to the library                 |\n"
				+ "|  7  | Add MULTIPLE PATRONS to the library             |\n"
				+ "|  8  | List ALL PATRONS in the library                 |\n"
				+ "|  9  | Find a PATRON BY PATRON ID                      |\n"
				+ "| 10  | Find a PATRON BY PATRON NAME                    |\n"
				+ "_________________________________________________________\n"
				+ "| 11  | BORROW a BOOK by ID with PATRON ID              |\n"
				+ "| 12  | Show LIBRARY BORROW History                     |\n"
				+ "| 13  | RETURN  a BOOK TO the LIBRARY                   |\n"
				+ "| 14  | EXIT the Library System                         |\n"
				+ "+-------------------------------------------------------+\n" + "Enter your choice: ");
	}

	private static void addBooks() {
		try {
			System.out.println("Enter the book id: ");
			int bookId = scan.nextInt();
			scan.nextLine();

			if (bookId <= 0) {
				throw new IllegalArgumentException("Book ID must be a positive number");
			}

			System.out.println("Enter the book name: ");
			String bookName = scan.nextLine().trim();
			if (!bookName.matches("[a-zA-Z ]+")) {
				System.out.println("Invalid input! Book name should only contain letters and spaces.");
				return; // Exit method early
			}
			System.out.println("Enter the book Author name: ");
			String authorName = scan.nextLine();

			if (!authorName.matches("[a-zA-Z ]+")) {
				System.out.println("Invalid input! Author name should only contain letters and spaces.");
				return; // Exit method early
			}

			System.out.println("Enter the available copies: ");
			int noOfCopies = scan.nextInt();
			if (noOfCopies < 0) {
				System.out.println("Invalid input! copies must be greater than or equal to  0.");
				return;
			}
			book = new Books(bookId, bookName, authorName, noOfCopies);
			libraryManagement.addBook(book);
		} catch (InputMismatchException e) {
			System.out.println("Invalid input! Please enter correct values.");
			scan.nextLine(); // Clear the scanner buffer to avoid infinite loops
		} catch (IllegalArgumentException e) {
			System.out.println("Error! :" + e.getMessage());
		} catch (Exception e) {
			System.out.println("An unexpected error occurred!!");
//		    e.printStackTrace(); // Print the full error stack trace for debugging
		}

	}
	private static void addPatron() {
		try {
			System.out.println("Enter the patron ID: ");
			int patronId = scan.nextInt();
			scan.nextLine(); // Consume newline

			if (patronId <= 0) {
				throw new IllegalArgumentException("Patron ID must be a positive number.");
			}

			System.out.println("Enter the patron name: ");
			String patronName = scan.nextLine().trim();

			if (!patronName.matches("[a-zA-Z ]+")) {
				System.out.println("Invalid input! Patron name should only contain letters and spaces.");
				
			}

			System.out.println("Enter the patron age: ");
			int patronAge = scan.nextInt();
			scan.nextLine(); // Consume newline

			if (patronAge <= 0) {
				System.out.println("Invalid input! Age must be greater than 0.");
				
			}

			patron = new Patrons(patronId, patronName, patronAge);
			
			libraryManagement.addPatron(patron);
		} catch (InputMismatchException e) {
			System.out.println("Invalid input! Please enter correct values.");
			scan.nextLine(); // Clear scanner buffer
			
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
			
		} catch (Exception e) {
			System.out.println("An unexpected error occurred!");
			
		}
	}

}

//bookList.add(new Books(1, "Java", "James Gosling", 10));
//bookList.add(new Books(2, "Python", "Guido van Rossum", 5));
//bookList.add(new Books(3, "C++", "Bjarne Stroustrup", 7));
//bookList.add(new Books(4, "JavaScript", "Brendan Eich", 6));
//bookList.add(new Books(5, "C#", "Anders Hejlsberg", 4));
//bookList.add(new Books(6, "Swift", "Chris Lattner", 8));
//bookList.add(new Books(7, "Go", "Robert Griesemer", 9));
//bookList.add(new Books(8, "Kotlin", "JetBrains", 3));
//bookList.add(new Books(9, "Ruby", "Yukihiro Matsumoto", 2));
//bookList.add(new Books(10, "PHP", "Rasmus Lerdorf", 1));
