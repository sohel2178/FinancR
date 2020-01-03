package com.forbitbd.financrr.ui.finance.report;



import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.FinanceResponce;
import com.forbitbd.financrr.models.TransactionResponse;

import java.util.List;

public interface FinanceReportContract {

    interface Presenter{
        void getFinanceData(String projectID);
    }

    interface View{
       void showProgressDialog();
       void hideProgressDialog();
       void initList(FinanceResponce financeResponce);
       List<Account> getAccounts();
       List<TransactionResponse> getTransactions();
    }
}
