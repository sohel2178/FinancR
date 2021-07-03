package com.forbitbd.financrr.ui.finance.report.monthlyTransaction.all;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.TransactionResponse;
import com.forbitbd.financrr.ui.newFinance.dashboard.topfive.TrAdapter;

import java.util.List;


public class AllFragment extends Fragment implements TrAdapter.TrListener, AllContract.View {


    private TrAdapter adapter;
    private AllPresenter mPresenter;

    private List<TransactionResponse> transactionResponses;

    private int year,month;



    public AllFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AllPresenter(this);
        adapter = new TrAdapter(this);
        transactionResponses = (List<TransactionResponse>) getArguments().getSerializable(Constant.TRANSACTION);
        year = getArguments().getInt("YEAR");
        month = getArguments().getInt("MONTH");
        Log.d("HHHHH",transactionResponses.size()+"");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_five, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        mPresenter.processTransactions(transactionResponses,year,month);
    }

    public void update(int year,int month){
        mPresenter.processTransactions(transactionResponses,year,month);
    }

    @Override
    public void onItemClick(TransactionResponse transactionResponse) {

    }

    @Override
    public void renderAdapter(List<TransactionResponse> transactionList) {
        adapter.clear();
        for (TransactionResponse x: transactionList){
            adapter.add(x);
        }
    }
}