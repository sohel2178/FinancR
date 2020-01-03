package com.forbitbd.financrr.ui.finance.report.trialBalance;



import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.TransactionResponse;
import com.forbitbd.financrr.models.TrialBalance;

import java.util.List;

public interface TrialBalanceContract {

    interface Presenter{
        void processData(List<Account> accountList, List<TransactionResponse> transactionList);
    }

    interface View{
        void renderAdapter(List<TrialBalance> trialBalanceList);
    }
}
