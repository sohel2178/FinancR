package com.forbitbd.financrr.ui.finance.report.monthlyTransaction.summery;

import com.forbitbd.financrr.models.TransactionResponse;
import com.forbitbd.financrr.models.TrialBalance;

import java.util.List;

public interface SummeryContract {

    interface Presenter{
        void processTransactions(List<TransactionResponse> transactionList, int year, int month);
    }

    interface View{
        void renderAdapter(List<TrialBalance> trialBalanceList);
    }
}
