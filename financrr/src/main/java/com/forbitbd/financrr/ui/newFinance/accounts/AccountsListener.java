package com.forbitbd.financrr.ui.newFinance.accounts;

import com.forbitbd.financrr.models.Account;

public interface AccountsListener {

    void addAccount(Account account);
    void updateAccount(Account account);
    void showDialog();
    void hideDialog();
}
