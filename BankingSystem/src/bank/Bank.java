package bank;

import accounts.*;
import customer.Customer;
import logging.Logs;
import logging.Transactions;
import readwriteCSV.ReadWriteAccounts;
import readwriteCSV.ReadWriteCustomer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Bank{
    private String bankName;
    private ArrayList<Customer> customers;
    private ArrayList<Account> accounts;

    private static final int DIGIT_LENGTH = 4;
    private static final int LETTER_LENGTH = 4;

    public Bank(String bankName) {
        this.bankName = bankName;
        this.customers = ReadWriteCustomer.ReadCustomers();
        this.accounts = ReadWriteAccounts.ReadAccounts();
    }

    public void addCustomer(Customer c) {
        ReadWriteCustomer.WriteCustomer(c);
        this.customers = ReadWriteCustomer.ReadCustomers();
    }

    public void openAccount(Customer c, Account a) {
        for (Customer customer: customers) {
            if (customer.equals(c)) {
                ReadWriteAccounts.WriteAccount(a);
                Random random = new Random();
                int tid = random.nextInt(10000) + 1;
                Date date = new Date();
                Transactions t = new Transactions(String.valueOf(tid), a.getAccountNumber(), "Check Balance", Double.valueOf(""), date);
                Logs.LogTransaction(t);
                ReadWriteAccounts.WriteAccount(a);
                this.accounts = ReadWriteAccounts.ReadAccounts();
            }
        }
    }
    public void openAccount(Customer c, AccountTypes accountType, double deposit) {
        for (Customer customer: customers) {
            if (customer.equals(c)) {
                Account a = null;

                switch (accountType) {
                    case CURRENT_ACCOUNT:
                        a = new CurrentAccount(this.generateRandomString(), c, accountType, deposit);
                        break;
                    case LOAN_ACCOUNT:
                        a = new LoanAccount(this.generateRandomString(), c, accountType, deposit);
                        break;
                    case SAVINGS_ACCOUNT:
                        a = new SavingsAccount(this.generateRandomString(), c, accountType, deposit);
                        break;
                    default:
                        System.out.println("Check The Details You Entered!!!");
                        break;
                }
                ReadWriteAccounts.WriteAccount(a);
                this.accounts = ReadWriteAccounts.ReadAccounts();
                Random random = new Random();
                int tid = random.nextInt(10000) + 1;
                Date date = new Date();
                Transactions t = new Transactions(String.valueOf(tid), a.getAccountNumber(), "Open Account", deposit, date);
                Logs.LogTransaction(t);
            }
        }
    }

    public String getBankName() {
        return bankName;
    }

    public Customer findCustomerById(String  id) {
        this.customers = ReadWriteCustomer.ReadCustomers();
        for (Customer customer: customers) {
            if (customer.getCustomerId().equals(id)) {
                return customer;
            }
        }
        return null;
    }

    public void transferFunds(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = null, toAccount = null;
        for (Account account : accounts ) {
            if (account.getAccountNumber().equals(fromAccountNumber)) {
                fromAccount = account;
            } else if (account.getAccountNumber().equals(toAccountNumber)) {
                toAccount = account;
            }
        }

        if (fromAccount == null || toAccount == null) {
            System.out.println("Accounts not found, please check the details you entered!");
            System.exit(0);
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }

        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds in the from account.");
        }

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
        Date currentdate = new Date();
        Transactions t = new Transactions("12", fromAccountNumber, "Transfer", amount, currentdate);
        Logs.LogTransaction(t);
        ReadWriteAccounts.WriteAccounts(this.accounts);
        System.out.println("Transferred " + amount + " from " + fromAccountNumber + " to " + toAccountNumber);
    }

    public static String generateRandomString() {
        Random random = new Random();

        // Generate 4 random digits
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < DIGIT_LENGTH; i++) {
            int digit = random.nextInt(10); // Generates a random digit between 0 and 9
            randomString.append(digit);
        }

        // Generate 4 random letters
        for (int i = 0; i < LETTER_LENGTH; i++) {
            char letter = (char) ('a' + random.nextInt(26)); // Generates a random letter from 'a' to 'z'
            randomString.append(letter);
        }

        return randomString.toString();
    }

    static void calculateMonthlyInterest() {
        LocalDate currentDate = LocalDate.now();
        if (currentDate.getDayOfMonth() == 15) { //Calculates Interest On The 15th Day Each Month
            ArrayList<Account> accounts = ReadWriteAccounts.ReadAccounts();

            if (accounts != null) {
                for (Account account : accounts) {
                    if (account.getAccountType().equals(AccountTypes.LOAN_ACCOUNT) || account.getAccountType().equals(AccountTypes.SAVINGS_ACCOUNT)) {
                        account.applyInterest();
                        Random random = new Random();
                        int tid = random.nextInt(10000) + 1;
                        Date date = new Date();
                        Transactions t = new Transactions(String.valueOf(tid), account.getAccountNumber(), "Interest", account.getBalance(), date);
                        Logs.LogTransaction(t);

                    }
                }
                ReadWriteAccounts.WriteAccounts(accounts);
            }

        }

    }

    public void deposit(String accNo, double amount) {
        this.accounts = ReadWriteAccounts.ReadAccounts();
        for (Account account: accounts) {
            if (account.getAccountNumber().equals(accNo)) {
                account.deposit(amount);
                Random random = new Random();
                int tid = random.nextInt(10000) + 1;
                Date date = new Date();
                Transactions t = new Transactions(String.valueOf(tid), accNo, "Deposit", amount, date);
                Logs.LogTransaction(t);
                ReadWriteAccounts.WriteAccounts(this.accounts);
            }
        }
    }

    public void withdraw(String accNo, double amount) {
        this.accounts = ReadWriteAccounts.ReadAccounts();

        for (Account account: accounts) {
            if (account.getAccountNumber().equals(accNo)) {
                account.withdraw(amount);
                Random random = new Random();
                int tid = random.nextInt(10000) + 1;
                Date date = new Date();
                Transactions t = new Transactions(String.valueOf(tid), accNo, "Withdraw", amount, date);
                Logs.LogTransaction(t);
                ReadWriteAccounts.WriteAccounts(this.accounts);
            }
        }
    }

    public void checkBalance(String accNo) {
        this.accounts = ReadWriteAccounts.ReadAccounts();
        for (Account account: accounts) {
            if (account.getAccountNumber().equals(accNo)) {
                System.out.println("Current Balance: " + account.getBalance());
                Random random = new Random();
                int tid = random.nextInt(10000) + 1;
                Date date = new Date();
                Transactions t = new Transactions(String.valueOf(tid), accNo, "Check Balance", account.getBalance(), date);
                Logs.LogTransaction(t);
            }
        }
    }

    public void repayLoan(String accNo, double amount)  {
        this.accounts = ReadWriteAccounts.ReadAccounts();
        for (Account account: accounts) {
            if (account.getAccountNumber().equals(accNo) && account.getAccountType().equals(AccountTypes.LOAN_ACCOUNT)) {
                account.repayLoan(amount);
                Random random = new Random();
                int tid = random.nextInt(10000) + 1;
                Date date = new Date();
                Transactions t = new Transactions(String.valueOf(tid), accNo, "Repay Loan", amount, date);
                Logs.LogTransaction(t);
                ReadWriteAccounts.WriteAccounts(this.accounts);
            }
        }
    }

    public void viewTransactionHistory(String accNo) {
        logging.Logs.getTransactionLogs(accNo);
    }
}
