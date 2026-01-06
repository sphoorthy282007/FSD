package com.fsd;

class BankAccount {
    double balance;

    BankAccount(double balance) {
        this.balance = balance;
    }

    void displayBalance() {
        System.out.println("Balance: " + balance);
    }
}

class SavingsAccount extends BankAccount {

    SavingsAccount(double balance) {
        super(balance);
    }

    void calculateInterest() {
        double interest = balance * 0.05;
        System.out.println("Interest: " + interest);
    }
}

public class Inheritance_Bank {

    public static void main(String[] args) {

        SavingsAccount sa = new SavingsAccount(10000);
        sa.displayBalance();
        sa.calculateInterest();
    }
}
