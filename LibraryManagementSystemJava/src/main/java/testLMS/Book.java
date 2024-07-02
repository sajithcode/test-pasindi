package testLMS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Book {
    private int book_id;
    private String title;
    private String author;
    private String publisher;
    private Date year_published;

    public Book(String title, String author, String publisher, Date year_published) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year_published = year_published;
    }

    public Book(int book_id, String title, String author, String publisher, Date year_published) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year_published = year_published;
    }

    public Book(int book_id) {
        this.book_id = book_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getYear_published() {
        return year_published;
    }

    public void setYear_published(Date year_published) {
        this.year_published = year_published;
    }
//Add a new book
    public static void addBook(Scanner scanner) {
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Book Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter Book Publisher: ");
        String publisher = scanner.nextLine();
        System.out.print("Enter Year Published (YYYY-MM-DD): ");
        String yearPublishedStr = scanner.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date year_published = null;

        try {
            year_published = dateFormat.parse(yearPublishedStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        Book newBook = new Book(title, author, publisher, year_published);
        System.out.println("Book added successfully.");

        String sql = "insert into books (title, author, publisher, year_published) values (?, ?, ?, ?)";


        try {
            DatabaseConnection.executeUpdate(sql, newBook.getTitle(), newBook.getAuthor(), newBook.getPublisher(), newBook.getYear_published());
        } catch (SQLException e) {
            System.out.println("Failed to add the book to the database. Error: " + e.getMessage());
        }
    }

//Update a book, you added
    public static void updateBook(Scanner scanner) {
        System.out.print("Enter Book ID to update: ");
        int book_id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new Book Title: ");
        String newTitle = scanner.nextLine();
        System.out.print("Enter new Book Author: ");
        String newAuthor = scanner.nextLine();
        System.out.print("Enter new Book Publisher: ");
        String newPublisher = scanner.nextLine();
        System.out.print("Enter new Year Published (YYYY-MM-DD): ");
        String newYearPublishedStr = scanner.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date newYearPublished = null;
        if (!newYearPublishedStr.isEmpty()) {
            try {
                newYearPublished = dateFormat.parse(newYearPublishedStr);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                return;
            }
        }

        StringBuilder sqlBuilder = new StringBuilder("UPDATE books SET ");
        List<Object> params = new ArrayList<>();

        if (!newTitle.isEmpty()) {
            sqlBuilder.append("title = ?, ");
            params.add(newTitle);
        }
        if (!newAuthor.isEmpty()) {
            sqlBuilder.append("author = ?, ");
            params.add(newAuthor);
        }
        if (!newPublisher.isEmpty()) {
            sqlBuilder.append("publisher = ?, ");
            params.add(newPublisher);
        }
        if (newYearPublished != null) {
            sqlBuilder.append("year_published = ?, ");
            params.add(new java.sql.Date(newYearPublished.getTime()));
        }

        if (params.size() > 0) {
            sqlBuilder.setLength(sqlBuilder.length() - 2);
        }

        sqlBuilder.append(" WHERE book_id = ?");
        params.add(book_id);

        String sql = sqlBuilder.toString();

        try {
            DatabaseConnection.executeUpdate(sql, params.toArray());
            System.out.println("Book updated successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to update the book in the database. Error: " + e.getMessage());
        }
    }

//delete a book, you added
    public static void deleteBook(Scanner scanner) {
        System.out.print("Enter Book ID to delete: ");
        int book_id = scanner.nextInt();
        scanner.nextLine();

        Book bookToDelete = new Book(book_id);
        System.out.println("Deleting book with ID " + book_id + "...");

        String sql = "delete from books where book_id = ?";
        try {
            DatabaseConnection.executeUpdate(sql, bookToDelete.getBook_id());
            System.out.println("Book deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to delete the book from the database. Error: " + e.getMessage());
        }
    }

//serach a book, you want to get
    public static void searchBook(Scanner scanner) {
        System.out.print("Enter title, author, or year published to search: ");
        String searchTerm = scanner.nextLine();

        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR year_published = ?";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date searchDate = null;

            try {
                searchDate = new java.sql.Date(dateFormat.parse(searchTerm).getTime());
            } catch (ParseException e) {
            }

            Object[] params = new Object[]{
                    "%" + searchTerm + "%",
                    "%" + searchTerm + "%",
                    searchDate
            };

            ResultSet rs = DatabaseConnection.executeQuery(sql, params);
            boolean found = false;

            while (rs.next()) {
                found = true;
                int bookId = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                Date yearPublished = rs.getDate("year_published");

                System.out.println("Book ID: " + bookId);
                System.out.println("Title: " + title);
                System.out.println("Author: " + author);
                System.out.println("Publisher: " + publisher);
                System.out.println("Year Published: " + yearPublished);
                System.out.println("---------------------------------");
            }

            if (!found) {
                System.out.println("No books found matching the search criteria.");
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("Failed to search the book in the database. Error: " + e.getMessage());
        }
    }
}