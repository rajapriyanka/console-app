package com.library.classes;

import java.time.LocalDate;
import java.time.LocalTime;

public class History {
	
    private int bookId;
    private String bookName;
    private int previousCopies;
    private int currentCopies;
    private int borrowCount;
    private int patronId;
    private String patronName;
    private LocalDate borrowDate;
    private LocalTime borrowTime;
    private LocalDate returnDate;
    private LocalTime returnTime;

    // Private constructor to force the use of Builder
    private History(Builder builder) {
        this.bookId = builder.bookId;
        this.bookName = builder.bookName;
        this.previousCopies = builder.previousCopies;
        this.currentCopies = builder.currentCopies;
        this.borrowCount = builder.borrowCount;
        this.patronId = builder.patronId;
        this.patronName = builder.patronName;
        this.borrowDate = builder.borrowDate;
        this.borrowTime = builder.borrowTime;
        this.returnDate = builder.returnDate;
        this.returnTime = builder.returnTime;
    }
    
    
    

    public int getBookId() {
		return bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public int getPreviousCopies() {
		return previousCopies;
	}

	public int getCurrentCopies() {
		return currentCopies;
	}

	public int getBorrowCount() {
		return borrowCount;
	}

	public int getPatronId() {
		return patronId;
	}

	public String getPatronName() {
		return patronName;
	}

	public LocalDate getBorrowDate() {
		return borrowDate;
	}

	public LocalTime getBorrowTime() {
		return borrowTime;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public LocalTime getReturnTime() {
		return returnTime;
	}




	// Builder Class
    public static class Builder {
        private int bookId;
        private String bookName;
        private int previousCopies;
        private int currentCopies;
        private int borrowCount;
        private int patronId;
        private String patronName;
        private LocalDate borrowDate;
        private LocalTime borrowTime;
        private LocalDate returnDate;
        private LocalTime returnTime;

        public Builder setBookId(int bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder setBookName(String bookName) {
            this.bookName = bookName;
            return this;
        }

        public Builder setPreviousCopies(int previousCopies) {
            this.previousCopies = previousCopies;
            return this;
        }

        public Builder setCurrentCopies(int currentCopies) {
            this.currentCopies = currentCopies;
            return this;
        }

        public Builder setBorrowCount(int borrowCount) {
            this.borrowCount = borrowCount;
            return this;
        }

        public Builder setPatronId(int patronId) {
            this.patronId = patronId;
            return this;
        }

        public Builder setPatronName(String patronName) {
            this.patronName = patronName;
            return this;
        }

        public Builder setBorrowDate(LocalDate borrowDate) {
            this.borrowDate = borrowDate;
            return this;
        }

        public Builder setBorrowTime(LocalTime borrowTime) {
            this.borrowTime = borrowTime;
            return this;
        }

        public Builder setReturnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public Builder setReturnTime(LocalTime returnTime) {
            this.returnTime = returnTime;
            return this;
        }

        public History build() {
            return new History(this);
        }
    }

    @Override
    public String toString() {
        return "History{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", previousCopies=" + previousCopies +
                ", currentCopies=" + currentCopies +
                ", borrowCount=" + borrowCount +
                ", patronId=" + patronId +
                ", patronName='" + patronName + '\'' +
                ", borrowDate=" + borrowDate +
                ", borrowTime=" + borrowTime +
                ", returnDate=" + returnDate +
                ", returnTime=" + returnTime +
                '}';
    }

	
}
