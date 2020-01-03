package com.forbitbd.financrr.ui.finance.cashFlow;



import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.financrr.models.CashFlow;
import com.forbitbd.financrr.models.TransactionResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CashFlowPresenter implements CashFlowContract.Presenter {

    private CashFlowContract.View mView;

    public CashFlowPresenter(CashFlowContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void filterDailyCashAccountData(List<TransactionResponse> transactionList) {
        List<TransactionResponse> cashTransactionList = new ArrayList<>();

        for (TransactionResponse x: transactionList){
            if(x.getFrom().getName().equals("Cash") || x.getTo().getName().equals("Cash") ){
                cashTransactionList.add(x);
            }
        }
        int duration = MyUtil.getDuration(cashTransactionList.get(cashTransactionList.size()-1).getDate().getTime(),cashTransactionList.get(0).getDate().getTime());

        Date startDate = cashTransactionList.get(0).getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime (startDate);


        List<CashFlow> cashFlowList = new ArrayList<>();

        for (int i=0;i<duration;i++){
            if(i==0){
                CashFlow cashFlow = new CashFlow(startDate);
                //cashFlowList.add(new CashFlow(startDate));

                for (TransactionResponse x:cashTransactionList){
                    if(MyUtil.getStringDate(cashFlow.getDate()).equals(MyUtil.getStringDate(x.getDate()))){
                        if(x.getFrom().getName().equals("Cash")){
                            cashFlow.reduceAmount(x.getAmount());
                        }else{
                            cashFlow.addAmount(x.getAmount());
                        }
                    }
                }
                cashFlowList.add(cashFlow);

            }else{
                cal.add(Calendar.DATE,1);
                CashFlow cashFlow = new CashFlow(cal.getTime());

                for (TransactionResponse x:cashTransactionList){
                    if(MyUtil.getStringDate(cashFlow.getDate()).equals(MyUtil.getStringDate(x.getDate()))){
                        if(x.getFrom().getName().equals("Cash")){
                            cashFlow.reduceAmount(x.getAmount());
                        }else{
                            cashFlow.addAmount(x.getAmount());
                        }
                    }
                }

                cashFlowList.add(cashFlow);

            }
        }

        mView.renderChart(cashFlowList);
    }

}
