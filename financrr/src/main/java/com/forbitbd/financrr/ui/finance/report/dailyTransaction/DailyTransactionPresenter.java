package com.forbitbd.financrr.ui.finance.report.dailyTransaction;



import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.financrr.models.TransactionResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyTransactionPresenter implements DailyTransactionContract.Presenter {

    private DailyTransactionContract.View mView;

    public DailyTransactionPresenter(DailyTransactionContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void openCalender() {
        mView.openCalender();
    }

    @Override
    public void filterData(List<TransactionResponse> transactionList, Date date) {
        List<TransactionResponse> tmpLIst = new ArrayList<>();

        for (TransactionResponse x: transactionList){
            if(MyUtil.getStringDate(date).equals(MyUtil.getStringDate(x.getDate()))){
                tmpLIst.add(x);
            }
        }
        mView.renderAdapter(tmpLIst);
    }
}
