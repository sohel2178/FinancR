package com.forbitbd.financrr.ui.finance.report.monthlyTransaction.summery;

import android.util.Log;

import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.TransactionResponse;
import com.forbitbd.financrr.models.TrialBalance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SummeryPresenter implements SummeryContract.Presenter {

    private SummeryContract.View mView;

    public SummeryPresenter(SummeryContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void processTransactions(List<TransactionResponse> transactionList, int year, int month) {
        List<TransactionResponse> tmpList = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        for (TransactionResponse x: transactionList){
            c.setTime(x.getDate());

            if(c.get(Calendar.YEAR)==year && c.get(Calendar.MONTH)==month){
                tmpList.add(x);
                if(!exists(accounts,x.getFrom())){
                    accounts.add(x.getFrom());
                }

                if(!exists(accounts,x.getTo())){
                    accounts.add(x.getTo());
                }


            }
        }

        processData(accounts,tmpList);

        Log.d("HHHHH",accounts.size()+" Monthly Account Size");
    }


    private boolean exists(List<Account> accountList,Account account){
        for(Account x: accountList){
            if(x.get_id().equals(account.get_id())){
                return true;
            }
        }

        return false;
    }

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
