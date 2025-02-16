package tests;

import accounts.Account;
import accounts.CurrentAccount;
import bank.Bank;
import customer.Customer;
import readwriteCSV.ReadWriteCustomer;

import java.util.ArrayList;

import static accounts.AccountTypes.CURRENT_ACCOUNT;

public class Main {
    public static void main(String[] args) {
//        Account account;
//        Customer c = new Customer("Mr Gang", "112 55 Gang Grove");
//        ReadWriteCustomer.WriteCustomer(c);
//        Customer d = new Customer("Mr Lang", "Gang Grove");
//        ReadWriteCustomer.WriteCustomer(d);
//        Customer e = new Customer("Mr Kang", "112 Grove");
//        ReadWriteCustomer.WriteCustomer(e);
//
//        ArrayList<Customer> data = ReadWriteCustomer.ReadCustomers();
        Bank bank = new Bank("ABZ");

        bank.transferFunds("89879", "89849", 12.99 );

//        for (Customer row : data) {
//            account = new CurrentAccount("8989", row, CURRENT_ACCOUNT, 230.89);
//            bank.openAccount(row, account);
////            System.out.println(row.getName() + " " + row.getCustomerId() + " " + row.getAddress());
//        }


    }
}