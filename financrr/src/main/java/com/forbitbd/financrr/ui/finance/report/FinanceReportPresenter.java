package com.forbitbd.financrr.ui.finance.report;


import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.financrr.api.ApiClient;
import com.forbitbd.financrr.models.FinanceResponce;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinanceReportPresenter implements FinanceReportContract.Presenter {

    private FinanceReportContract.View mView;

    public FinanceReportPresenter(FinanceReportContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getFinanceData(String projectID) {
        mView.showProgressDialog();
        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.getFinanceData(projectID)
                .enqueue(new Callback<FinanceResponce>() {
                    @Override
                    public void onResponse(Call<FinanceResponce> call, Response<FinanceResponce> response) {
                        mView.hideProgressDialog();
                        if(response.isSuccessful()){
                            mView.initList(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<FinanceResponce> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }
}
