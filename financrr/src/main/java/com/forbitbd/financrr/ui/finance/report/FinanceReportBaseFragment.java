package com.forbitbd.financrr.ui.finance.report;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.TransactionResponse;

import java.util.List;

public class FinanceReportBaseFragment extends Fragment {

    private FinanceReportActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof FinanceReportActivity){
            activity = (FinanceReportActivity) getActivity();
        }
    }


    public List<Account> getAccounts(){
        return activity.getAccounts();
    }

    public List<TransactionResponse> getTransactions(){
        return activity.getTransactions();
    }

    public void startZoomImageActivity(String path){
        activity.startZoomImageActivity(path);
    }
}
