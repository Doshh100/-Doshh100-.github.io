package readwriteCSV;

import customer.Customer;

import java.io.*;
import java.util.ArrayList;

public final class ReadWriteCustomer {
    public static void WriteCustomer(Customer c) {
        String csvpath = "./resources/customers.csv";

        String data[] = {c.getCustomerId(), c.getName(), c.getAddress()};

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvpath, true))) {
            writer.write(String.join(",", data));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed To Write To Csv File");
        }
    }

    public static ArrayList<Customer> ReadCustomers() {
        String csvpath = "./resources/customers.csv";
        ArrayList<Customer> customers = new ArrayList<>();

        ArrayList<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvpath))){
            String line;
            if ((line = reader.readLine()) != null) {

            }
            while ((line = reader.readLine()) != null) {
                customers.add(Customer.fromCSV(line));
            }
        } catch (IOException e) {

        }
        return customers;
    }
}
