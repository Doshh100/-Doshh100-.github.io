package logging;

import customer.Customer;

import java.io.*;

public class Logs {

    public static void LogTransaction(Transactions t) {
        final String transactionCsvPath = "./resources/transactionLogs.csv";


        String data[] = {String.valueOf(t.getDate()), t.getTransactionID(), t.getAccountNumber(), t.getTransactionType(), String.valueOf(t.getAmount())};

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(transactionCsvPath, true))) {
            writer.write(String.join(",", data));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed To Write To Csv File");
        }
    }

    public static void getTransactionLogs(String accNo) {
        final String transactionCsvPath = "./resources/transactionLogs.csv";

        String data[];

        try (BufferedReader reader = new BufferedReader(new FileReader(transactionCsvPath))){
            String line;
            if ((line = reader.readLine()) != null) {

            }
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[2].equals(accNo)){
                    System.out.println("##############################################################################");
                    System.out.println("Date:" + parts[0] + "\nTransactionID: " + parts[1] + "\nAccountID: " + parts[2] + "\nTransactionType: " + parts[3] + "\nAmount: " + parts[4]);
                    System.out.println("##############################################################################\n");

                }
            }
        } catch (IOException e) {
            System.out.println("No Transactions");
        }

    }
}
