package testLMS;

import java.sql.SQLException;
import java.util.Scanner;

public class Member {
    private int member_id;
    private String name;
    private String email;
    private int phone;


    public Member(String name, String email, int phone) {
        this.name = name;
        this.email= email;
        this.phone= phone;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }
    public void setPhone(int phone) {
        this.phone = phone;
    }

    //Add a new member
    public static void addMember(Scanner scanner) {
        System.out.print("Enter Member Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Member email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Member Phone number: ");
        int phone = 0;

        try {
            phone = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid phone number format. Please enter a valid number.");
            return;
        }


        Member newMember = new Member(name,email,phone);
        System.out.println("Member added successfully.");

        String sql = "insert into members (name,email,phone) values (?, ?, ?)";
        try {
            DatabaseConnection.executeUpdate(sql, newMember.getName(), newMember.getEmail(), newMember.getPhone());
        } catch (SQLException e) {
            System.out.println("Failed to add the member to the database. Error: " + e.getMessage());
        }
    }
}
