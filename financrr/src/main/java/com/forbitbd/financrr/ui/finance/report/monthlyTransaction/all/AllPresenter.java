package com.forbitbd.financrr.ui.finance.report.monthlyTransaction.all;

import com.forbitbd.financrr.models.TransactionResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AllPresenter implements AllContract.Presenter {

    private AllContract.View mView;


    public AllPresenter(AllContract.View mView){
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
