package com.forbitbd.financrr.ui.finance;

import android.util.Log;

import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.financrr.api.ApiClient;
import com.forbitbd.financrr.models.ClosingResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinancePresenter implements FinanceContract.Presenter {

    private FinanceContract.View mView;

    public FinancePresenter(FinanceContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void filter(String query) {
        mView.filter(query);
    }

    @Override
    public void showAccountAddDialog() {
        mView.showAccountAddDialog();
    }

    @Override
    public void startAddTransactionActivity() {
        mView.startAddTransactionActivity();
    }

    @Override
    public void startFinanceReportActivity() {
        mView.startFinanceReportActivity();
    }

    @Override
    public void downloadFile(Project project) {
        Log.d("UUUUUUUU","hjdsfdshfdsf");
        mView.showProgressDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.getDownloadFile(project.get_id())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            String path = mView.saveFile(response.body());

                            Log.d("YYYYYY",path);

                            if(path!=null){
                                mView.openFile(path);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }

    @Override
    public void closing(Project project) {
        mView.showProgressDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.performClosing(project.get_id())
                .enqueue(new Callback<ClosingResponse>() {
                    @Override
                    public void onResponse(Call<ClosingResponse> call, Response<ClosingResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<ClosingResponse> call, Throwable t) {

                    }
                });
    }
}
