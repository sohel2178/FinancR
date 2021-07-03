package com.forbitbd.financrr.ui.finance.report.monthlyTransaction.all;

import com.forbitbd.financrr.models.TransactionResponse;

import java.util.List;

public interface AllContract {

    interface Presenter{
        void processTransactions(List<TransactionResponse> transactionList, int year, int month);
    }

    interface View{
        void renderAdapter(List<TransactionResponse> transactionList);
    }
}
