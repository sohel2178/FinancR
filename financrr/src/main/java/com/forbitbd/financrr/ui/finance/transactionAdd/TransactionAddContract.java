package com.forbitbd.financrr.ui.finance.transactionAdd;



import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.Transaction;
import com.forbitbd.financrr.models.TransactionResponse;

import java.util.List;

public interface TransactionAddContract {

    interface Presenter{
        void getProjectAccount(String projectId);
        void openCalendar();
        void browseClick();
        boolean validate(Transaction transaction);
        void saveTransaction(Transaction transaction, byte[] bytes);
    }

    interface View{
        void showProgressDialog();
        void hideProgressDialog();

        void clearPreError();
        void showToast(String message);
        void showError(String message, int fieldId);

        void updateSpinnerAdapter(List<Account> accountList);

        void openCalendar();
        void openCameraActivity();
        void complete(TransactionResponse transaction);
    }
}
