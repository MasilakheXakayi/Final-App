import Models.Customer;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    static  List<Customer> newCustomers = new ArrayList<>();
    static  List<Customer> dbCustomers = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void getAllCustomers(){
        try{
            Statement st = MySQL.instance().getConnection().createStatement();
            String query = "SELECT * FROM customers";

            ResultSet cs = st.executeQuery(query);
            dbCustomers.clear();

            while(cs.next()){
                dbCustomers.add(new Customer(
                        cs.getString("cusname"),
                        cs.getInt("cusnumber"),
                        cs.getString("cellno"),
                        cs.getString("EmailAdress")));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void sortCustomersByName(){
        try {
            String query = "SELECT * FROM customers ORDER BY cusname ASC";
            Statement st= MySQL.instance().getConnection().createStatement();

            ResultSet cs = st.executeQuery(query);
            dbCustomers.clear();

            while(cs.next()){
                dbCustomers.add(new Customer(
                        cs.getString("cusname"),
                        cs.getInt("cusnumber"),
                        cs.getString("cellno"),
                        cs.getString("EmailAdress")));
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    private static String getSearchInput() {
        // Code to get user input, e.g. using Scanner class
        System.out.print("Enter search keyword: ");
        return scanner.next();
    }

    private static int getChoiceInput() {
        // Code to get user input, e.g. using Scanner class
        System.out.print("Enter your selection: ");
        return scanner.nextInt();
    }

    public static void search(String keyword){
        try{
            Statement st = MySQL.instance().getConnection().createStatement();
            String query = "SELECT * FROM `customers` WHERE cusname like '%"+keyword+"%';";

            ResultSet cs = st.executeQuery(query);
            dbCustomers.clear();

            while(cs.next()){
                dbCustomers.add(new Customer(
                        cs.getString("cusname"),
                        cs.getInt("cusnumber"),
                        cs.getString("cellno"),
                        cs.getString("EmailAdress")));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    public static void insertCustomers(Connection connection, Customer customer) throws SQLException {
        String query = "INSERT INTO customers (cusname, cellno, EmailAdress) VALUES (?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, customer.getCusname());
        statement.setString(2, customer.getCellno());
        statement.setString(3,customer.getEmailAdress());

        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        statement.close();
    }

    public static void readFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\user\\Documents\\SAQA\\FINAL ASSESMENT\\Final App\\data\\CustomerList.csv"));
        String rowLine;

        /**
         * Saving the csv data into a collection
         */
        while ((rowLine = reader.readLine()) != null){
            String[] row = rowLine.split(",");
            newCustomers.add(new Customer(row[0], row[1], row[2]));
        }
    }

    public static int menuSelection(){
        System.out.println("MENU");
        System.out.println("1. Upload customers");
        System.out.println("2. View customers");
        System.out.println("3. Search customers");
        System.out.println("4. Sort Customers By Name");
        System.out.println("99. Exit");

        return getChoiceInput();
    }

    public static void print(){
        System.out.println();
        dbCustomers.forEach(customer -> {
            System.out.println(customer);
        });
        System.out.println();
    }

    public static void main(String[] args) throws SQLException, IOException {
        while(true){
            switch (menuSelection()){
                case 1:
                    readFromFile();
                    // After data is saved into a collection

                    newCustomers.forEach(customer -> {
                        try {
                            insertCustomers(MySQL.instance().getConnection(), customer);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    break;
                case 2:
                    getAllCustomers();
                    print();
                    break;
                case 3:
                    search(getSearchInput());
                    print();
                    break;
                case 4:
                    sortCustomersByName();
                    print();
                    break;
                case 99:
                    System.out.println("Goodbye");
                    return;
            }
        }
    }
    }

