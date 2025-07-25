package com.flipfit.client;

import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;
import com.flipfit.client.*;


public class GymApplicationMenu
{
    public static Scanner scanner = new Scanner(System.in);
    private static GymAdminMenu adminClient = new GymAdminMenu();
    private static GymCustomerMenu customerClient = new GymCustomerMenu();
    private static GymOwnerMenu gymOwnerClient = new GymOwnerMenu();

    private static final String cust_register = "INSERT INTO Customer (Customer_id, User_id, Email, Phone, Address) VALUES (?, ?, ?, ?, ?);";
    private static final String cust_pass = "INSERT INTO User (User_Id, Role_Id, Username, Password) VALUES (?, ?, ?, ?);";

    public static int cust_user_id = 1014;
    public static int cust_id = 104;

    private static final String own_register = "INSERT INTO Owner (Owner_Id, user_Id, Email, Phone, PAN, Aadhar, GSTIN) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String own_pass = "INSERT INTO User (User_Id, Role_Id, Username, Password) VALUES (?, ?, ?, ?);";

    public static int own_user_id = 2013;
    public static int own_id = 203;

    private static final String get_pass = "SELECT Password, Role_Id FROM User where Username = ?;";
    private static final String role = "SELECT Role FROM User;";

    private static void mainPage()
    {
        System.out.println("WELCOME TO FLIPFIT APPLICATION");
        System.out.println("Enter your choice:");
        System.out.println("1. Login\n2. Registration\n3. Change Password\n4. Exit");
        int choice = scanner.nextInt();
       while(true)
       {
           switch (choice)
           {
               case 1:
                   login();
                   break;

               case 2:
                    registration();
                    break;

//                case 3:
//                    changePassword();

               case 4:
                   System.out.println("Thanks for visiting!");
                   return;

               default:
                   System.out.println("Invalid choice selected");
                   break;
           }
       }
    }


    private static void login() {

        String userName, password, passw;
        int roleid;

        try {
            System.out.println("Enter your Role \n 1 Admin \n 2 Customer \n 3 Gym Owner \n 4 Exit");
            int role = scanner.nextInt();

            System.out.println("Enter your Username");
            userName = scanner.next();

            System.out.println("Enter your Password");
            password = scanner.next();

            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
                 PreparedStatement preparedStatement = connection.prepareStatement(get_pass)) {

                preparedStatement.setString(1, userName);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String dbPassword = resultSet.getString("Password");
                        roleid = resultSet.getInt("Role_Id");
                        System.out.println(roleid);

                        switch (role) {
                            case 1:
                                if (roleid == 1 && password.equals(dbPassword)) {
                                    System.out.println("Login successful!");
                                    System.out.println("Welcome Admin!");
                                    GymAdminMenu gam = new GymAdminMenu();
                                    gam.GAMenu();
                                }
                                else
                                {
                                    System.out.println("Login unsuccessful!");
                                    return;
                                }
                                break;
                            case 2:
                                if (roleid == 2 && password.equals(dbPassword)) {
                                    System.out.println("Login successful!");
                                    System.out.println("Welcome Customer!");
                                    GymCustomerMenu gcm = new GymCustomerMenu();
                                    gcm.showCustomerDashboard();

                                }
                                else
                                {
                                    System.out.println("Login unsuccessful!");
                                    return;
                                }
                                break;
                            case 3:
                                if (roleid == 3 && password.equals(dbPassword)) {
                                    System.out.println("Login successful!");
                                    System.out.println("Welcome Owner!");
                                    GymOwnerMenu gom = new GymOwnerMenu();
                                    gom.OwnerMenu();

                                }
                                else
                                {
                                    System.out.println("Login unsuccessful!");
                                    return;
                                }
                                break;
                        }
                    }
                } catch (SQLException e) {
                    System.err.println("A database error occurred during login: " + e.getMessage());
                    e.printStackTrace(); // Always print stack trace for debugging
                }
            } catch (Exception e) {

                System.out.println("Invalid Option Selected");
            }

        } catch (Exception e) {

            System.out.println("Invalid Option Selected");
        }
    }

    private static void registration()
    {
        String userName, password, email, phone, add, pan, aadhar, gstin;

        System.out.println("Enter your role \n1. Customer \n2. Owner");
        int cho= scanner.nextInt();

        switch(cho)
        {
            case 1:
                System.out.println("Enter your Username");
                userName = scanner.next();

                    System.out.println("Enter your Password");
                    password = scanner.next();

                    System.out.println("Enter your email");
                    email = scanner.next();

                    System.out.println("Enter your phone no");
                    phone = scanner.next();

                    System.out.println("Enter your address");
                    add = scanner.next();


                    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
                         PreparedStatement preparedStatement = connection.prepareStatement(cust_pass)) {

                        preparedStatement.setInt(1, cust_user_id);
                        preparedStatement.setInt(2, 2);
                        preparedStatement.setString(3, userName);
                        preparedStatement.setString(4, password);

                        int rowsAffected = preparedStatement.executeUpdate();
                        System.out.println(rowsAffected + " row(s) inserted.");

                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }

                    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
                         PreparedStatement preparedStatement = connection.prepareStatement(cust_register)) {

                        preparedStatement.setInt(1, cust_id);
                        preparedStatement.setInt(2, cust_user_id);
                        preparedStatement.setString(3, email);
                        preparedStatement.setString(4, phone);
                        preparedStatement.setString(5, add);

                        int rowsAffected = preparedStatement.executeUpdate();
                        System.out.println(rowsAffected + " row(s) inserted.");

                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }

                    cust_user_id++;
                    cust_id++;
                    System.out.println("Registered as Customer");
                    return;

                case 2:
                    System.out.println("Enter your Username");
                    userName = scanner.next();

                    System.out.println("Enter your Password");
                    password = scanner.next();

                    System.out.println("Enter your email");
                    email = scanner.next();

                    System.out.println("Enter your phone no");
                    phone = scanner.next();

                    System.out.println("Enter your PAN No");
                    pan = scanner.next();

                    System.out.println("Enter your AADHAR No");
                    aadhar = scanner.next();

                    System.out.println("Enter your GSTIN No");
                    gstin = scanner.next();


                    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
                         PreparedStatement preparedStatement = connection.prepareStatement(cust_pass)) {

                        preparedStatement.setInt(1, own_user_id);
                        preparedStatement.setInt(2, 3);
                        preparedStatement.setString(3, userName);
                        preparedStatement.setString(4, password);

                        int rowsAffected = preparedStatement.executeUpdate();
                        System.out.println(rowsAffected + " row(s) inserted.");

                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }

                    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Flipfit_Schema", "root", "Sanvi@2003");
                         PreparedStatement preparedStatement = connection.prepareStatement(cust_register)) {

                        preparedStatement.setInt(1, own_id);
                        preparedStatement.setInt(2, own_user_id);
                        preparedStatement.setString(3, email);
                        preparedStatement.setString(4, phone);
                        preparedStatement.setString(5, pan);
                        preparedStatement.setString(6, aadhar);
                        preparedStatement.setString(7, gstin);

                        int rowsAffected = preparedStatement.executeUpdate();
                        System.out.println(rowsAffected + " row(s) inserted.");

                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }

                    own_user_id++;
                    own_id++;

                    System.out.println("Registered as Owner");
                    return ;
            }

    }

    public static void main(String[] args) {
        System.out.println("WELCOME TO FLIPFIT APPLICATION");
        System.out.println("Enter your choice:");
        mainPage();
    }

}