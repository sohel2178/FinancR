package com.forbitbd.financrr.ui.finance.account.accountDetail;



import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.TransactionResponse;

import java.util.List;

public interface AccountDetailContract {

    interface Presenter{
        void getAllTransactionsForAccount(Account account);
        void updateGeneralInfo(Account account, List<TransactionResponse> transactionResponseList);
        void toggle();
        void filterTransaction(Account account, List<TransactionResponse> transactionList, int position);
    }

    interface View{
        void showProgressDialog();
        void hideProgressDialog();
        void initialize(List<TransactionResponse> transactionResponseList);
        void renderInfo(int count, double debit, double credit, double balance, String text);
        void measureHeightAndAnimate();
        void renderTransactionList(List<TransactionResponse> transactionResponseList);
    }


}
