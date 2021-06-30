package com.forbitbd.financrr.models;

public class Dashboard {

    private double income;
    private double expenses;
    private double balance;
    private int accounts_count;
    private int transactions_count;


    public Dashboard() {
    }


    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getAccounts_count() {
        return accounts_count;
    }

    public void setAccounts_count(int accounts_count) {
        this.accounts_count = accounts_count;
    }

    public int getTransactions_count() {
        return transactions_count;
    }

    public void setTransactions_count(int transactions_count) {
        this.transactions_count = transactions_count;
    }
}
