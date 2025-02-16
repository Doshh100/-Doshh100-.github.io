package readwriteCSV;

import accounts.*;
import customer.Customer;

import java.io.*;
import java.util.ArrayList;

public class ReadWriteAccounts {
    public static void WriteAccount(Account a) {
        String csvpath = "./resources/accounts.csv";

        String data[] = {a.getAccountNumber(), String.valueOf(a.getBalance()), a.getAccountHolder().getCustomerId(), String.valueOf(a.getAccountType())};

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvpath, true))) {
            writer.write(String.join(",", data));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed To Write To Csv File");
        }
    }
    public static void WriteAccounts(ArrayList<Account> a) {
        String csvpath = "./resources/accounts.csv";

        String data[] = {"accountNumber", "balance", "accountHolder", "accountType"};

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvpath))) {
            writer.write(String.join(",", data));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed To Write To Csv File");
        }

        for (Account account: a) {
            String id = account.getAccountHolder().getCustomerId();
            System.out.println(id);
            String[] acc = {account.getAccountNumber(), String.valueOf(account.getBalance()), id, String.valueOf(account.getAccountType())};

            try (BufferedWriter writter = new BufferedWriter(new FileWriter(csvpath, true))){
                writter.write(String.join(",", acc));
                writter.newLine();
            } catch (IOException e) {
                System.err.println("Failed To Write To Csv File");
            }
        }
    }

    private static Customer searchCustomerById(String customerId) {
        String csvpath = "./resources/customers.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(csvpath))) {
            String line;

            // Read the header (if necessary)
            reader.readLine(); // Skip the header line

            // Read the remaining lines
            while ((line = reader.readLine()) != null) {
                // Split the line into parts
                String[] parts = line.split(",");

                // Check if the first item matches the customerId
                if (parts[0].equals(customerId)) {
                    // Return a new Customer object based on the CSV data
                    return Customer.fromCSV(line); // Adjust based on your Customer constructor
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        // Return null if no customer is found
        return null;
    }

    public static ArrayList<Account> ReadAccounts() {
        String csvpath = "./resources/accounts.csv";
        Account account;
        AccountTypes accountType;
        ArrayList<Account> accounts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvpath))){
            String line;
            if ((line = reader.readLine()) != null) {

            }
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    return null; // Return null if the line is empty
                }
                String[] parts = line.split(",");
                String customerId = parts[2].trim();
                Customer customer = ReadWriteAccounts.searchCustomerById(customerId);
                try {
                    // Convert the string from CSV to AccountType enum
                    accountType = AccountTypes.valueOf(parts[3].toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("No Accounts");
                    return null;
                }
                account = genrateAccount(accountType, parts, customer);
                accounts.add(account);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("No Accounts Found!");
        }

        return accounts;
    }

    private static Account genrateAccount(AccountTypes accountType, String[] parts, Customer customer) {
        Account account = null;
        switch (accountType) {
            case CURRENT_ACCOUNT:
                account = new CurrentAccount(parts[0].trim(), customer, AccountTypes.CURRENT_ACCOUNT, Double.parseDouble(parts[1]));
                break;
            case LOAN_ACCOUNT:
                account = new LoanAccount(parts[0].trim(), customer, AccountTypes.LOAN_ACCOUNT, Double.parseDouble(parts[1]));
                break;
            case SAVINGS_ACCOUNT:
                account = new SavingsAccount(parts[0].trim(), customer, AccountTypes.SAVINGS_ACCOUNT, Double.parseDouble(parts[1]));
                break;
            default:
                throw new IllegalArgumentException("No Account Type Found");
        }
        return account;
    }

    public static ArrayList<Account> ReadCustomerAccounts(String customerID) {
        String csvpath = "./resources/accounts.csv";
        Account account;
        AccountTypes accountType;
        ArrayList<Account> accounts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvpath))){
            String line;
            if ((line = reader.readLine()) != null) {

            }
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String customerId = parts[0].trim();

                // Use .equals() for string comparison
                if (customerId.equals(customerID)) {
                    Customer customer = ReadWriteAccounts.searchCustomerById(customerId);

                    try {
                        // Convert the string from CSV to AccountType enum
                        accountType = AccountTypes.valueOf(parts[3].trim());
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Unknown account type: " + parts[3]);
                    }

                    // Check if customer is found
                    if (customer != null) {
                        account = genrateAccount(accountType, parts, customer);
                        accounts.add(account);
                    } else {
                        throw new IllegalArgumentException("Customer with ID " + customerId + " not found.");
                    }
                }
            }


        } catch (IOException e) {
            throw new IllegalArgumentException("No Accounts Found!");

        }

        return accounts;
    }
}
