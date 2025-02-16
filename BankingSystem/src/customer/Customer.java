package customer;

import accounts.Account;
import readwriteCSV.ReadWriteAccounts;

import java.util.ArrayList;
import java.util.UUID;

public class Customer {
    private String customerId;
    private String name;
    private String address;
    ArrayList<Account> accounts;

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Customer(String name, String address) {
        this.customerId = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        accounts = ReadWriteAccounts.ReadCustomerAccounts(this.customerId);

    }
    public Customer(String customerId, String name, String address) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        accounts = ReadWriteAccounts.ReadCustomerAccounts(this.customerId);

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return customerId.equals(customer.customerId); // Compare based on customerId
    }
    public void openAccount(Account a) {
        ReadWriteAccounts.WriteAccount(a);
        accounts = ReadWriteAccounts.ReadCustomerAccounts(this.customerId);
    }

    public static Customer fromCSV(String csv) {
        String[] parts = csv.split(",");
        return new Customer(parts[0], parts[1], parts[2]);
    }

}
