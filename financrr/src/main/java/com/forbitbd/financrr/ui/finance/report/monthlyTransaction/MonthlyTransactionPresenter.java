package com.forbitbd.financrr.ui.finance.report.monthlyTransaction;


import com.forbitbd.financrr.models.TransactionResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthlyTransactionPresenter implements MonthlyTransactionContract.Presenter {

    private MonthlyTransactionContract.View mView;


    public MonthlyTransactionPresenter(MonthlyTransactionContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void processTransactions(List<TransactionResponse> transactionList, int year, int month) {

        List<TransactionResponse> tmpList = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        for (TransactionResponse x: transactionList){
           c.setTime(x.getDate());

           if(c.get(Calendar.YEAR)==year && c.get(Calendar.MONTH)==month){
               tmpList.add(x);
           }
        }

        mView.renderAdapter(tmpList);
    }
}
