package accounts;

import customer.Customer;

public abstract class Account {
    private String accountNumber;
    private AccountTypes accountType;
    private double balance;
    private Customer accountHolder;



    protected Account(String accountNumber, Customer accountHolder, AccountTypes accountType, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialDeposit;
        this.accountType = accountType;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }

    public AccountTypes getAccountType() {
        return this.accountType;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getAccountHolder() {
        return this.accountHolder;
    }

    public void setAccountHolder(Customer accountHolder) {
        this.accountHolder = accountHolder;
    }

    public abstract void applyInterest();

    protected abstract void logTransaction(String type, double amount);


    public void repayLoan(double amount) {
    }
}
