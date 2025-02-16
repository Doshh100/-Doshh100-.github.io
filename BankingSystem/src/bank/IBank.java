package bank;

import accounts.Account;
import customer.Customer;

public interface IBank {
    void addCustomer(Customer c);
    void openAccount(Customer c, Account a);
    Account findAccountByNumber(String accountNumber);
    static void calculateMonthlyInterest(){

    };
}
