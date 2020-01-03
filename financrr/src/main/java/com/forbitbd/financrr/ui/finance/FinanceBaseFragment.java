package com.forbitbd.financrr.ui.finance;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.forbitbd.androidutils.models.Project;


public abstract class FinanceBaseFragment extends Fragment {

    private FinanceActivity activity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof FinanceActivity){
            activity = (FinanceActivity) getActivity();
        }
    }


    public Project getProject(){
        return activity.getProject();
    }

    public void showDialog(){
        activity.showProgressDialog();
    }

    public void hideDialog(){
        activity.hideProgressDialog();
    }

    public abstract void filter(String query);

    public void startZoomImageActivity(String url){
        activity.startZoomImageActivity(url);
    }

    public FinanceActivity get_activity(){
        return this.activity;
    }

}
