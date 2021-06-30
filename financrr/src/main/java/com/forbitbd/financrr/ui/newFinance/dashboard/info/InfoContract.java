package com.forbitbd.financrr.ui.newFinance.dashboard.info;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.financrr.models.Dashboard;

import okhttp3.ResponseBody;

public interface InfoContract {

    interface Presenter{
        void getDashboardData(String projectID);
        void startReportActivity();
        void download(Project project);
    }

    interface View{
        void bind(Dashboard dashboard);
        void startReportActivity();
        void showProgressDialog();
        void hideProgressDialog();
        String saveFile(ResponseBody responseBody);
        void openFile(String path);
    }
}
