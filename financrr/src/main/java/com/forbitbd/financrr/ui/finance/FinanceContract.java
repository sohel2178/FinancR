package com.forbitbd.financrr.ui.finance;



import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.TransactionResponse;

import okhttp3.ResponseBody;

public interface FinanceContract {

    interface Presenter{
        void filter(String query);
        void showAccountAddDialog();
        void startAddTransactionActivity();
        void startFinanceReportActivity();
        void downloadFile(Project project);
        void closing(Project project);
    }

    interface View{
        SharedProject getSharedProject();
        void filter(String query);
        void showAccountAddDialog();
        void addAccount(Account account);
        void showProgressDialog();
        void hideProgressDialog();
        String saveFile(ResponseBody responseBody);
        void openFile(String path);
        void startAddTransactionActivity();
        void startFinanceReportActivity();
        void removeRelatedTransactions(Account account);
        void startUpdateOrDeleteActivity(TransactionResponse transaction);
        void showTapTargetView(String title,String content);
    }
}
