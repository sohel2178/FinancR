package com.forbitbd.financrr.ui.newFinance.dashboard.topfive;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.TransactionResponse;
import com.forbitbd.financrr.ui.finance.account.accountDetail.transactionDetail.TransactionDetailFragment;

import java.util.List;


public class TopFiveFragment extends Fragment implements TopFiveContract.View, TrAdapter.TrListener {

    private SharedProject sharedProject;
    private TrAdapter adapter;

    private TopFivePresenter mPresenter;


    public TopFiveFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sharedProject = (SharedProject) getArguments().getSerializable(Constant.PROJECT);
        adapter = new TrAdapter(this);
        mPresenter = new TopFivePresenter(this);

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

        mPresenter.getTopFiveTransaction(sharedProject.getProject().get_id());
    }


    @Override
    public void render(List<TransactionResponse> transactionResponses) {
        adapter.clear();
        for (TransactionResponse x: transactionResponses){
            adapter.add(x);
        }
    }

    @Override
    public void onItemClick(TransactionResponse transactionResponse) {
        TransactionDetailFragment tdf = new TransactionDetailFragment();
        tdf.setCancelable(false);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.TRANSACTION,transactionResponse);
        tdf.setArguments(bundle);

        tdf.show(getChildFragmentManager(),"HHHH");
    }
}