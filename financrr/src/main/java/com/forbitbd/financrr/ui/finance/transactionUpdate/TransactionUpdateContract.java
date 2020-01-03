package com.forbitbd.financrr.ui.finance.transactionUpdate;


import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.TransactionResponse;

import java.util.List;

public interface TransactionUpdateContract {

    interface Presenter{
        void getProjectAccount(String projectID);
        void bind(TransactionResponse transactionResponse);

        void openCalendar();
        void browseClick();
        boolean validate(TransactionResponse transaction);
        void updateTransaction(TransactionResponse transactionResponse, byte[] bytes);
        void deleteTransaction(TransactionResponse transactionResponse);
    }


    interface View{
        void showProgressDialog();
        void hideProgressDialog();

        void clearPreError();
        void showToast(String message);
        void showError(String message, int fieldId);

        void openCalendar();
        void openCameraActivity();

        void updateSpinnerAdapter(List<Account> accountList);
        void bind(TransactionResponse transactionResponse);
        void updateTransaction(TransactionResponse transactionResponse);
        void deleteFromAdapter();
    }
}
