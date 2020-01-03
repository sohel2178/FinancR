package com.forbitbd.financrr.ui.finance.report.monthlyTransaction;



import com.forbitbd.financrr.models.TransactionResponse;

import java.util.List;

public interface MonthlyTransactionContract {

    interface Presenter{
        void processTransactions(List<TransactionResponse> transactionList, int year, int month);
    }

    interface View{
        void renderAdapter(List<TransactionResponse> transactionList);
    }
}
