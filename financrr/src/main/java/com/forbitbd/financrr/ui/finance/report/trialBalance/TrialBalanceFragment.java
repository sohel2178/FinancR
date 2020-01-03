package com.forbitbd.financrr.ui.finance.report.trialBalance;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.TrialBalance;
import com.forbitbd.financrr.ui.finance.report.FinanceReportBaseFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrialBalanceFragment extends FinanceReportBaseFragment implements TrialBalanceContract.View, TrialBalanceListener {

    private TrialBalanceAdapter adapter;
    private TrialBalancePresenter mPresenter;


    public TrialBalanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new TrialBalancePresenter(this);

        this.adapter = new TrialBalanceAdapter(getContext(),this);
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

        mPresenter.processData(getAccounts(),getTransactions());
    }


    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemUpdate(int position) {

    }

    @Override
    public void onItemRemove(int position) {

    }

    @Override
    public void renderAdapter(List<TrialBalance> trialBalanceList) {
        adapter.setItems(trialBalanceList);
    }
}
