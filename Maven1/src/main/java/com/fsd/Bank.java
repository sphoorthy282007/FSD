package com.fsd;

public class Bank {

    double getInterestRate() {
        return 0.0;
    }

    public static void main(String[] args) {
        Bank b1 = new SBI();
        Bank b2 = new HDFC();

        System.out.println("SBI Interest Rate: " + b1.getInterestRate() + "%");
        System.out.println("HDFC Interest Rate: " + b2.getInterestRate() + "%");
    }
}

class SBI extends Bank {
    double getInterestRate() {
        return 6.75;
    }
}

class HDFC extends Bank {
    double getInterestRate() {
        return 7.25;
    }
}
