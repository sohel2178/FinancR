package com.forbitbd.financrr.ui.newFinance.dashboard.topfive;

import com.forbitbd.financrr.models.TransactionResponse;

import java.util.List;

public interface TopFiveContract {

    interface Presenter{
        void getTopFiveTransaction(String projectID);
        void getRecentFiveTransaction(String projectID);
    }

    interface View{
        void render(List<TransactionResponse> transactionResponses);
    }
}
