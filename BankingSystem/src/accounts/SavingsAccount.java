package accounts;

import customer.Customer;

public class SavingsAccount extends Account {
    private double interestRate = 0.1;

    public SavingsAccount(String accountNumber, Customer accountHolder, AccountTypes accountType, double initialDeposit) {
        super(accountNumber, accountHolder, accountType, initialDeposit);
    }

    public void withdraw(double amount) {
        if (this.getBalance() < 0) {
            System.out.println("You Have Exceeded The Withdraw Limit!");

        } else {
            super.withdraw(amount);
        }

    }

    public void applyInterest() {
        double balance = this.getBalance();
        balance = balance - (balance * interestRate);
        this.setBalance(balance);

    }

    public void logTransaction(String type, double amount) {

    }
}
