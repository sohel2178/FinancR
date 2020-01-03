package com.forbitbd.financrr.ui.finance.report.trialBalance;


import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.TransactionResponse;
import com.forbitbd.financrr.models.TrialBalance;

import java.util.ArrayList;
import java.util.List;

public class TrialBalancePresenter implements TrialBalanceContract.Presenter {

    private TrialBalanceContract.View mView;

    public TrialBalancePresenter(TrialBalanceContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void processData(List<Account> accountList, List<TransactionResponse> transactionList) {
        List<TrialBalance> trialBalanceList = new ArrayList<>();

        for (Account x : accountList){
            trialBalanceList.add(new TrialBalance(x));
        }

        for (TrialBalance x: trialBalanceList){

            for (TransactionResponse y: transactionList){
                if(x.getAccount().get_id().equals(y.getFrom().get_id()) ){
                    x.setCredit(x.getCredit()+y.getAmount());
                }else if(x.getAccount().get_id().equals(y.getTo().get_id())){
                    x.setDebit(x.getDebit()+y.getAmount());
                }
            }
        }

        mView.renderAdapter(trialBalanceList);
    }
}
