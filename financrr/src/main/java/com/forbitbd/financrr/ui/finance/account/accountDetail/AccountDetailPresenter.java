package com.forbitbd.financrr.ui.finance.account.accountDetail;


import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.financrr.api.ApiClient;
import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.TransactionResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountDetailPresenter implements AccountDetailContract.Presenter {

    private AccountDetailContract.View mView;

    public AccountDetailPresenter(AccountDetailContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getAllTransactionsForAccount(Account account) {
        mView.showProgressDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.getAllTransactionForAccount(account.getProject(),account.get_id())
                .enqueue(new Callback<List<TransactionResponse>>() {
                    @Override
                    public void onResponse(Call<List<TransactionResponse>> call, Response<List<TransactionResponse>> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.initialize(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TransactionResponse>> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }

    @Override
    public void updateGeneralInfo(Account account, List<TransactionResponse> transactionResponseList) {

        double debit=0,credit=0;
        int count = transactionResponseList.size();

        for (TransactionResponse x: transactionResponseList){
            if(x.getFrom().get_id().equals(account.get_id())){
                credit=credit+x.getAmount();
            }else if(x.getTo().get_id().equals(account.get_id())){
                debit = debit+x.getAmount();
            }
        }

        String text ="";
        if(debit>credit){
            text = "Balance C/D on Credit";
        }else{
            text = "Balance C/D on Debit";
        }

        double balance = Math.abs(debit-credit);

        mView.renderInfo(count,debit,credit,balance,text);

    }

    @Override
    public void toggle() {
        mView.measureHeightAndAnimate();
    }

    @Override
    public void filterTransaction(Account account , List<TransactionResponse> transactionList, int position) {

        switch (position){
            case 0:
                mView.renderTransactionList(transactionList);
                break;

            case 1:
                mView.renderTransactionList(getDebitList(account,transactionList));
                break;

            case 2:
                mView.renderTransactionList(getCreditList(account,transactionList));
                break;


        }
    }

    private List<TransactionResponse> getCreditList(Account account, List<TransactionResponse> transactionList){
        List<TransactionResponse> temlList = new ArrayList<>();

        for (TransactionResponse x: transactionList){
            if(x.getFrom().get_id().equals(account.get_id())){
                temlList.add(x);
            }
        }
        return temlList;
    }

    private List<TransactionResponse> getDebitList(Account account, List<TransactionResponse> transactionList){
        List<TransactionResponse> temlList = new ArrayList<>();

        for (TransactionResponse x: transactionList){
            if(x.getTo().get_id().equals(account.get_id())){
                temlList.add(x);
            }
        }
        return temlList;
    }

//    private List<TransactionResponse> getFilteredList(List<TransactionResponse> transactionList, int position){
//        List<TransactionResponse> tempList=new ArrayList<>();
//    }
}
