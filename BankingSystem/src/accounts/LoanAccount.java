package accounts;

import customer.Customer;

public class LoanAccount extends Account {
    private double interestRate = 0.15;
    double overdraftLimit = 200.00;
    public LoanAccount(String accountNumber, Customer accountHolder, AccountTypes accountType, double initialDeposit) {
        super(accountNumber, accountHolder, accountType, initialDeposit);
    }


    public void deposit(double amount) {
        if (this.getBalance() != 0) {
            double balance = this.getBalance();
            balance -= amount;
            this.setBalance(balance);
        } else {
            System.out.println("You Can't Deposit In This Account Anymore!");
            System.exit(0);
        }

    }

    public void withdraw(Double amount) {
        System.out.println("You Cannot Withdraw From This Account!");
        System.exit(0);
    }

    public void applyInterest() {
        double balance = this.getBalance();
        balance += balance * this.interestRate;
        this.setBalance(balance);
    }

    public void repayLoan(double amount) {
        double balance = this.getBalance();
        balance -= amount;
        this.setBalance(balance);
    }

    @Override
    public void logTransaction(String type, double balance) {

    }
}
