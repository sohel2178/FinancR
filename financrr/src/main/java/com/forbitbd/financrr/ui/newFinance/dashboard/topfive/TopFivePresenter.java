package com.forbitbd.financrr.ui.newFinance.dashboard.topfive;


import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.financrr.api.ApiClient;
import com.forbitbd.financrr.models.TransactionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopFivePresenter implements TopFiveContract.Presenter {


    private TopFiveContract.View mView;

    public TopFivePresenter(TopFiveContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getTopFiveTransaction(String projectID) {

        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.getProjectTopFiveTransactions(projectID)
                .enqueue(new Callback<List<TransactionResponse>>() {
                    @Override
                    public void onResponse(Call<List<TransactionResponse>> call, Response<List<TransactionResponse>> response) {
                        if(response.isSuccessful()){
                            mView.render(response.body());
                        }else{

                        }
                    }

                    @Override
                    public void onFailure(Call<List<TransactionResponse>> call, Throwable t) {

                    }
                });

    }

    @Override
    public void getRecentFiveTransaction(String projectID) {

        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.getProjectRecentTransactions(projectID)
                .enqueue(new Callback<List<TransactionResponse>>() {
                    @Override
                    public void onResponse(Call<List<TransactionResponse>> call, Response<List<TransactionResponse>> response) {
                        if(response.isSuccessful()){
                            mView.render(response.body());
                        }else{

                        }
                    }

                    @Override
                    public void onFailure(Call<List<TransactionResponse>> call, Throwable t) {
                    }
                });
    }
}
