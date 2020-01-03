package com.forbitbd.financrr.ui.finance.account;



import com.forbitbd.financrr.models.Account;

import java.util.List;

public interface AccountContract {

    interface Presenter{
        void getProjectAccounts(String projectId);
        void deleteAccountFromDatabase(Account account);
    }


    interface View{
        void showDialog();
        void hideDialog();

        void renderAdapter(List<Account> accountList);
        void addAccount(Account account);
        void editAccountRequest(Account account);
        void updateAccountInAdapter(Account account);
        void startAccountDetailActivity(Account account);
        void showDeleteDialog(Account account);
        void removeAccountFromAdapter(Account account);

    }
}
