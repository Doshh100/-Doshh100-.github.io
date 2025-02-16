import accounts.AccountTypes;
import bank.Bank;
import bank.InterestThread;
import customer.Customer;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InterestThread interestThread = new InterestThread();
        interestThread.start();
        Bank bank = new Bank("MyBank");

        while (true) {
            System.out.println("Welcome to " + bank.getBankName());
            System.out.println("1. Create a new customer");
            System.out.println("2. Open an account");
            System.out.println("3. Deposit money");
            System.out.println("4. Withdraw money");
            System.out.println("5. Transfer money");
            System.out.println("6. Check balance");
            System.out.println("7. View transaction history");
            System.out.println("8. Repay loan");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
                continue; // Skip to the next iteration of the loop
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Customer ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Customer Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Customer Address: ");
                    String address = scanner.nextLine();
                    bank.addCustomer(new Customer(id, name, address));
                    break;

                case 2:
                    AccountTypes accountType = null;
                    System.out.print("Enter Customer ID: ");
                    id = scanner.nextLine();
                    Customer customer = bank.findCustomerById(id);
                    if (customer == null) {
                        System.out.println("Customer not found.");
                    } else {
                        System.out.print("Account type (Savings/Current/Loan): ");
                        String chosenaccountType = scanner.nextLine();
                        try {
                            switch (chosenaccountType) {
                                case "Savings":
                                    accountType = AccountTypes.SAVINGS_ACCOUNT;
                                    break;
                                case "Current":
                                    accountType = AccountTypes.CURRENT_ACCOUNT;
                                    break;
                                case "Loan":
                                    accountType = AccountTypes.LOAN_ACCOUNT;
                                    break;
                                default:
                                    System.out.println("Enter A Proper Account Type!!!");
                                    System.exit(0);
                                    break;
                            }
                        } catch (IllegalArgumentException e) {
                            throw new IllegalArgumentException("Unknown account type: " + chosenaccountType);
                        }
                        System.out.print("Initial deposit: ");
                        double deposit;
                        try {
                            deposit = scanner.nextDouble();
                            if (deposit < 0) {
                                System.out.println("Initial deposit cannot be negative.");
                                continue;
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input. Please enter a valid deposit amount.");
                            scanner.nextLine(); // Clear the invalid input
                            continue;
                        }
                        bank.openAccount(customer, accountType, deposit);
                    }
                    break;

                case 3:
                    System.out.print("Enter Account Number: ");
                    String accNo = scanner.nextLine();
                    System.out.print("Enter deposit amount: ");
                    double amount;
                    try {
                        amount = scanner.nextDouble();
                        if (amount < 0) {
                            System.out.println("Deposit amount cannot be negative.");
                            continue;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter a valid deposit amount.");
                        scanner.nextLine(); // Clear the invalid input
                        continue;
                    }
                    bank.deposit(accNo, amount);
                    break;

                case 4:
                    System.out.print("Enter Account Number: ");
                    accNo = scanner.nextLine();
                    System.out.print("Enter withdrawal amount: ");
                    try {
                        amount = scanner.nextDouble();
                        if (amount < 0) {
                            System.out.println("Withdrawal amount cannot be negative.");
                            continue;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter a valid withdrawal amount.");
                        scanner.nextLine(); // Clear the invalid input
                        continue;
                    }
                    bank.withdraw(accNo, amount);
                    break;

                case 5:
                    System.out.print("From Account: ");
                    String fromAcc = scanner.nextLine();
                    System.out.print("To Account: ");
                    String toAcc = scanner.nextLine();
                    System.out.print("Amount: ");
                    try {
                        amount = scanner.nextDouble();
                        if (amount < 0) {
                            System.out.println("Transfer amount cannot be negative.");
                            continue;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter a valid amount.");
                        scanner.nextLine(); // Clear the invalid input
                        continue;
                    }
                    bank.transferFunds(fromAcc, toAcc, amount);
                    break;

                case 6:
                    System.out.print("Enter Account Number: ");
                    accNo = scanner.nextLine();
                    bank.checkBalance(accNo);
                    break;

                case 7:
                    System.out.print("Enter Account Number: ");
                    accNo = scanner.nextLine();
                    bank.viewTransactionHistory(accNo);
                    break;

                case 8:
                    System.out.print("Enter Loan Account Number: ");
                    accNo = scanner.nextLine();
                    System.out.print("Enter repayment amount: ");
                    try {
                        amount = scanner.nextDouble();
                        if (amount < 0) {
                            System.out.println("Repayment amount cannot be negative.");
                            continue;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter a valid repayment amount.");
                        scanner.nextLine(); // Clear the invalid input
                        continue;
                    }
                    bank.repayLoan(accNo, amount);
                    break;

                case 9:
                    System.out.println("Thank you for using the system.");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
