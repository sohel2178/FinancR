package com.forbitbd.financrr.ui.finance;



import com.forbitbd.androidutils.models.Project;
import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.TransactionResponse;

public interface FinanceContract {

    interface Presenter{
        void filter(String query);
        void showAccountAddDialog();
        void startAddTransactionActivity();
        void startFinanceReportActivity();
    }

    interface View{
        Project getProject();
        void filter(String query);
        void showAccountAddDialog();
        void addAccount(Account account);
        void startAddTransactionActivity();
        void startFinanceReportActivity();
        void removeRelatedTransactions(Account account);
        void startUpdateOrDeleteActivity(TransactionResponse transaction);
    }
}
