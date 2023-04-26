import Models.Customer;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    static  List<Customer> newCustomers = new ArrayList<>();
    static  List<Customer> dbCustomers = new ArrayList<>();

    public static void getAllCustomers(){
        try{
            Statement st = MySQL.instance().getConnection().createStatement();
            String query = "SELECT * FROM customers";

            ResultSet cs = st.executeQuery(query);

            while(cs.next()){
                dbCustomers.add(new Customer(
                        cs.getString("cusname"),
                        cs.getInt("cusnumber"),
                        cs.getString("cellno"),
                        cs.getString("EmailAdress")));

                System.out.println(dbCustomers.get(dbCustomers.size()-1));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

//    public static void searchCus(){
//        try {
//            Statement st = MySQL.instance().getConnection().createStatement();
//            String query = "SELECT * FROM customers";
//            String keyword = getInput(); // Get user input
//
//            String sql = "SELECT * FROM customers WHERE cusname LIKE ? OR cusnumber LIKE ?";
//            PreparedStatement stmt = (PreparedStatement) st.getConnection();
//            stmt.setString(1, "%" + keyword + "%"); // Set parameterized query for first column
//            stmt.setString(2, "%" + keyword + "%"); // Set parameterized query for second column
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                // Process the results
//                String column1 = rs.getString("column1_name");
//                int column2 = rs.getInt("column2_name");
//                // ...
//            }
//
//            rs.close();
//            stmt.close();
//            st.close();
//        } catch (SQLException e) {
//            System.out.println("Error connecting to database: " + e.getMessage());
//        }
//    }

    private static String getInput() {
        // Code to get user input, e.g. using Scanner class
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();
        scanner.close();
        return keyword;
    }




    public static void search(String keyword){
        try{
            Statement st = MySQL.instance().getConnection().createStatement();
            String query = "SELECT * FROM `customers` WHERE cusname like '%"+keyword+"%';";

            ResultSet cs = st.executeQuery(query);

            while(cs.next()){
                dbCustomers.clear();
                dbCustomers.add(new Customer(
                        cs.getString("cusname"),
                        cs.getInt("cusnumber"),
                        cs.getString("cellno"),
                        cs.getString("EmailAdress")));

                System.out.println(dbCustomers.get(dbCustomers.size()-1));
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
            System.out.println("Row inserted");
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

    public static void main(String[] args) throws SQLException, IOException {
        readFromFile();
        // After data is saved into a collection

        newCustomers.forEach(customer -> {
            try {
                insertCustomers(MySQL.instance().getConnection(), customer);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        getAllCustomers();

        System.out.println("================================================");

        search("Mi");
    }
    }

