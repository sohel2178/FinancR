package com.forbitbd.financrr.ui.newFinance.dashboard.info;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.forbitbd.androidutils.utils.AppPreference;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.Dashboard;
import com.forbitbd.financrr.ui.finance.report.FinanceReportActivity;
import com.forbitbd.financrr.ui.newFinance.NewFinanceActivity;
import com.forbitbd.financrr.ui.newFinance.TitleListener;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class InfoFragment extends Fragment implements InfoContract.View, View.OnClickListener ,EasyPermissions.PermissionCallbacks{

    private static final int READ_WRITE_PERMISSION =30000;


    private TitleListener listener;

    private InfoPresenter mPresenter;

    private TextView tvBalance,tvIncome,tvExpenses,tvNumberOfAccounts,tvNumberOfTransactions;
//    private LinearLayout lHint;

    private MaterialButton btnReport,btnDownload;



    public InfoFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = (TitleListener) getActivity();
        mPresenter = new InfoPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvBalance = view.findViewById(R.id.balance);
        tvIncome = view.findViewById(R.id.income);
        tvExpenses = view.findViewById(R.id.expenses);
        tvNumberOfAccounts = view.findViewById(R.id.accounts_count);
        tvNumberOfTransactions = view.findViewById(R.id.transactions_count);
//        lHint = view.findViewById(R.id.hint);

        btnReport = view.findViewById(R.id.report);
        btnDownload = view.findViewById(R.id.download);
        btnReport.setOnClickListener(this);
        btnDownload.setOnClickListener(this);

        if(listener!=null){
            mPresenter.getDashboardData(listener.getSharedProject().getProject().get_id());
        }

//        ObjectAnimator animation = ObjectAnimator.ofFloat(lHint, "translationX", 100f);
//        animation.setDuration(2000);
//        animation.start();




    }

    @Override
    public void bind(Dashboard dashboard) {
        tvBalance.setText(String.valueOf(dashboard.getBalance()));
        tvIncome.setText(String.valueOf(dashboard.getIncome()));
        tvExpenses.setText(String.valueOf(dashboard.getExpenses()));
        tvNumberOfAccounts.setText(String.valueOf(dashboard.getAccounts_count()).concat(" Nos"));
        tvNumberOfTransactions.setText(String.valueOf(dashboard.getTransactions_count()).concat(" Nos"));
    }

    @Override
    public void startReportActivity() {
        AppPreference.getInstance(getContext()).increaseCounter();

        Intent intent = new Intent(getContext(), FinanceReportActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,listener.getSharedProject().getProject());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showProgressDialog() {
        listener.showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        listener.hideProgressDialog();
    }

    @Override
    public String saveFile(ResponseBody responseBody) {
        return listener.saveTaskFile("Construction Manager",listener.getSharedProject().getProject().getName(),"Transactions","transaction.xlsx",responseBody);
    }

    @Override
    public void openFile(String path) {
        listener.openFile(path);
    }


    @Override
    public void onClick(View view) {
        if(view==btnDownload){
            requestFileAfterPermission();
        }else if(view==btnReport){
            mPresenter.startReportActivity();
        }
    }


    @AfterPermissionGranted(READ_WRITE_PERMISSION)
    private void requestFileAfterPermission() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            mPresenter.download(listener.getSharedProject().getProject());
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "App need to Permission for Read and Write",
                    READ_WRITE_PERMISSION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        mPresenter.download(listener.getSharedProject().getProject());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(getContext(), "Permission Not Granted", Toast.LENGTH_SHORT).show();
    }
}