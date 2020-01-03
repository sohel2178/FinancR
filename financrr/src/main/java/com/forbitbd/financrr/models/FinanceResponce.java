package com.forbitbd.financrr.models;

import java.util.List;

public class FinanceResponce {

    private List<Account> accounts;
    private List<TransactionResponse> transactions;


    public FinanceResponce() {
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionResponse> transactions) {
        this.transactions = transactions;
    }
}
