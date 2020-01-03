package com.forbitbd.financrr.ui.finance.cashFlow;



import com.forbitbd.financrr.models.CashFlow;
import com.forbitbd.financrr.models.TransactionResponse;

import java.util.List;

public interface CashFlowContract {

    interface Presenter{
        void filterDailyCashAccountData(List<TransactionResponse> transactionList);
    }

    interface View{
        void renderChart(List<CashFlow> cashFlowList);
    }
}
