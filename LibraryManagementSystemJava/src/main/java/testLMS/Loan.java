package testLMS;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class Loan {
    private int loan_id;
    private int book_id;
    private int member_id;
    private Date loan_date;
    private Date due_date;
    private Date return_date;

    public Loan(int book_id, int member_id, Date loan_date, Date due_date) {
        this.book_id = book_id;
        this.member_id = member_id;
        this.loan_date = loan_date;
        this.due_date = due_date;
        this.return_date = null;
    }
    public Loan(int loan_id, int book_id, int member_id, Date loan_date, Date due_date, Date return_date) {
        this.loan_id = loan_id;
        this.book_id = book_id;
        this.member_id = member_id;
        this.loan_date = loan_date;
        this.due_date = due_date;
        this.return_date= return_date;
    }
    public Loan(int loan_id, Date return_date) {
        this.loan_id = loan_id;
        this.return_date= return_date;
    }


    public int getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(int loan_id) {
        this.loan_id = loan_id;
    }
    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }
    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }
    public Date getLoan_date() {
        return loan_date;
    }

    public void setLoan_date(Date loan_date) {
        this.loan_date = loan_date;
    }
    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }
    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

// date about took a book
    public static void loanBook(Scanner scanner) {

        System.out.print("Enter Book ID: ");
        int book_id = scanner.nextInt();
        System.out.print("Enter Member ID: ");
        int member_id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Loan Date (YYYY-MM-DD): ");
        Date loan_date = Date.valueOf(scanner.nextLine());
        System.out.print("Enter Due Date (YYYY-MM-DD): ");
        Date due_date = Date.valueOf(scanner.nextLine());

        Loan newLoan = new Loan(book_id, member_id, loan_date, due_date);
        String sql = "insert into loans (book_id, member_id, loan_date, due_date) values (?, ?, ?, ?)";
        try {
            DatabaseConnection.executeUpdate(sql,newLoan.getBook_id(), newLoan.getMember_id(), newLoan.getLoan_date(), newLoan.getDue_date());
            System.out.println("Book loaned successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to loan the book. Error: " + e.getMessage());
        }
    }

//date you returned the book
    public static void returnBook(Scanner scanner) {
        System.out.print("Enter Loan ID: ");
        int loan_id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Return Date (YYYY-MM-DD): ");
        Date return_date = Date.valueOf(scanner.nextLine());

        Loan newLoan = new Loan(loan_id,return_date);
        String sql = "update loans set return_date = ? where loan_id = ?";
        try {
            DatabaseConnection.executeUpdate(sql,return_date,loan_id  );
            System.out.println("Book returned successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to return the book. Error: " + e.getMessage());
        }
    }
}
