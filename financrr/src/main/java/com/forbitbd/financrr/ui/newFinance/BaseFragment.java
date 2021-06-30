package com.forbitbd.financrr.ui.newFinance;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.financrr.ui.finance.FinanceActivity;

public abstract class BaseFragment extends Fragment {

    private NewFinanceActivity activity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof FinanceActivity){
            activity = (NewFinanceActivity) getActivity();
        }
    }


    public Project getProject(){
        return activity.getSharedProject().getProject();
    }

    public SharedProject getSharedProject(){
        return activity.getSharedProject();
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

    public NewFinanceActivity get_activity(){
        return this.activity;
    }


//    public void showTapTargetView(String title,String content){
//        activity.showTapTargetView(title,content);
//    }
}
