package com.forbitbd.financrr.ui.finance.accountAdd;


import com.forbitbd.financrr.models.Account;

public interface AddAccountContract {

    interface Presenter{
        void bind(Account account);
        boolean validate(Account account);
        void saveAccount(Account account);
        void updateAccount(Account account);
    }

    interface View{
        void bind(Account account);
        void showToast(String message);
        void clearPreError();
        void showDialog();
        void hideDialog();
        void complete(Account account);
        void updateAccountInAdapter(Account account);
    }
}
