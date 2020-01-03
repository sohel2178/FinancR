package com.forbitbd.financrr.ui.finance.transaction;


import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.TransactionResponse;

import java.util.List;

public interface TransactionContract {

    interface Presenter{
        void getProjectTransactions(String projectID);
    }

    interface View{
        void showDialog();
        void hideDialog();
        void renderAdapter(List<TransactionResponse> transactionList);
        void addTransaction(TransactionResponse transactionResponse);
        void update(TransactionResponse transactionResponse);
        void remove(TransactionResponse transactionResponse);
        void removeRelatedTransactions(Account account);
    }
}
