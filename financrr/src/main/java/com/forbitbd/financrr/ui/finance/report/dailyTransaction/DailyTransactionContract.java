package com.forbitbd.financrr.ui.finance.report.dailyTransaction;



import com.forbitbd.financrr.models.TransactionResponse;

import java.util.Date;
import java.util.List;

public interface DailyTransactionContract {

    interface Presenter{
        void openCalender();
        void filterData(List<TransactionResponse> transactionList, Date date);
    }

    interface View{
        void openCalender();
        void renderAdapter(List<TransactionResponse> transactionList);
    }
}
