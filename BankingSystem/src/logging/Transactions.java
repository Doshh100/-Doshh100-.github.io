package logging;

import java.util.Date;

public class Transactions {
    private String transactionID;
    private String accountNumber;
    private String TransactionType;
    private double amount;
    private Date date;

    public Transactions(String transactionID, String accountNumber, String transactionType, double amount, Date date) {
        this.transactionID = transactionID;
        this.accountNumber = accountNumber;
        TransactionType = transactionType;
        this.amount = amount;
        this.date = date;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }
}
