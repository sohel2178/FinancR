package com.forbitbd.financrr.ui.newFinance;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.financrr.models.TransactionResponse;

import okhttp3.ResponseBody;

public interface TitleListener {

    void setTitle(String title);
    void showProgressDialog();
    void hideProgressDialog();
    void startZoomImageActivity(String url);
    SharedProject getSharedProject();
    String saveTaskFile(String appName, String projectName, String dirname, String fileName, ResponseBody responseBody);
    void openFile(String path);
    void invisibleMenu();
    void visibleMenu();
}
