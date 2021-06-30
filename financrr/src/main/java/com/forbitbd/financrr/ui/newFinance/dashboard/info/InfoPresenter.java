package com.forbitbd.financrr.ui.newFinance.dashboard.info;

import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.financrr.api.ApiClient;
import com.forbitbd.financrr.models.Dashboard;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoPresenter implements InfoContract.Presenter {

    private InfoContract.View mView;

    public InfoPresenter(InfoContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getDashboardData(String projectID) {
        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.getDashboard(projectID)
                .enqueue(new Callback<Dashboard>() {
                    @Override
                    public void onResponse(Call<Dashboard> call, Response<Dashboard> response) {
                        if(response.isSuccessful()){
                            mView.bind(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Dashboard> call, Throwable t) {

                    }
                });
    }

    @Override
    public void startReportActivity() {
        mView.startReportActivity();
    }

    @Override
    public void download(Project project) {
//        mView.download();
        mView.showProgressDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.getDownloadFile(project.get_id())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            String path = mView.saveFile(response.body());
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
}
