package accounts;

import customer.Customer;

public class CurrentAccount extends Account {
    private double interestRate = 0.1;
    double overdraftLimit = 10.00;
    public CurrentAccount(String accountNumber, Customer accountHolder, AccountTypes accountType, double initialDeposit) {
        super(accountNumber, accountHolder, accountType, initialDeposit);

    }

    @Override
    public void withdraw(double amount) {
        if (this.getBalance() < overdraftLimit) {
            System.out.println("You Have Exceeded The Withdraw Limit!");

        } else {
            super.withdraw(amount);
        }

    }



    public void applyInterest() {
//        double balance = this.getBalance();
//        balance = balance - (balance * interestRate);
//        this.setBalance(balance);
    }

    public void logTransaction(String type, double amount) {

    }

}
