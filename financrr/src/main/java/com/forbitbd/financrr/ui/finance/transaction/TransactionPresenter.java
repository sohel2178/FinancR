package com.forbitbd.financrr.ui.finance.transaction;


import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.financrr.api.ApiClient;
import com.forbitbd.financrr.models.TransactionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionPresenter implements TransactionContract.Presenter {

    private TransactionContract.View mView;

    public TransactionPresenter(TransactionContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getProjectTransactions(String projectID) {
        mView.showDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.getProjectTransactions(projectID)
                .enqueue(new Callback<List<TransactionResponse>>() {
                    @Override
                    public void onResponse(Call<List<TransactionResponse>> call, Response<List<TransactionResponse>> response) {
                        mView.hideDialog();

                        if(response.isSuccessful()){
                            mView.renderAdapter(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TransactionResponse>> call, Throwable t) {
                        mView.hideDialog();
                    }
                });


    }
}
