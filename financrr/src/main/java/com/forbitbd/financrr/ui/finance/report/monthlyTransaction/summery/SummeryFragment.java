package com.forbitbd.financrr.ui.finance.report.monthlyTransaction.summery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.TransactionResponse;
import com.forbitbd.financrr.models.TrialBalance;
import com.forbitbd.financrr.ui.finance.report.trialBalance.TrialBalanceAdapter;

import java.util.List;

public class SummeryFragment extends Fragment implements SummeryContract.View {

    private List<TransactionResponse> transactionResponses;

    private SummeryPresenter mPresenter;
    private TrialBalanceAdapter adapter;

    private int year,month;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPresenter = new SummeryPresenter(this);
        transactionResponses = (List<TransactionResponse>) getArguments().getSerializable(Constant.TRANSACTION);
        year = getArguments().getInt("YEAR");
        month = getArguments().getInt("MONTH");
        this.adapter = new TrialBalanceAdapter(getContext(),null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trial_balance, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        mPresenter.processTransactions(transactionResponses,year,month);
    }

    @Override
    public void renderAdapter(List<TrialBalance> trialBalanceList) {
//        adapter.clear();
        adapter.setItems(trialBalanceList);
    }

    public void update(int year,int month){
        mPresenter.processTransactions(transactionResponses,year,month);
    }
}
