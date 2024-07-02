package testLMS;

import java.util.Scanner;

public class LMS {
    public static void main(String[] args) {

        if (!DatabaseConnection.isDBConnected()) {
            System.out.println("Failed to connect to the database. Exiting....");
            System.exit(1);
        } else {
            System.out.println("Database is connected.");
        }

// select options
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add a New Book");
            System.out.println("2. Update Book Details");
            System.out.println("3. Delete a Book");
            System.out.println("4. Search for a Book");
            System.out.println("5. Add a New Member");
            System.out.println("6. Loan a Book");
            System.out.println("7. Return a Book");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Book.addBook(scanner);
                    break;
                case 2:
                    Book.updateBook(scanner);
                    break;
                case 3:
                    Book.deleteBook(scanner);
                    break;
                case 4:
                    Book.searchBook(scanner);
                    break;
                case 5:
                    Member.addMember(scanner);
                    break;
                case 6:
                    Loan.loanBook(scanner);
                    break;
                case 7:
                    Loan.returnBook(scanner);
                    break;
                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
