package com.forbitbd.financrr.ui.finance.transaction;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.TransactionResponse;
import com.forbitbd.financrr.ui.finance.FinanceActivity;
import com.forbitbd.financrr.ui.finance.FinanceBaseFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends FinanceBaseFragment
        implements TransactionContract.View,TransactionListener {


    private TransactionAdapter adapter;
    private TransactionPresenter mPresenter;

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new TransactionPresenter(this);
        adapter = new TransactionAdapter(getContext(),this);
    }

    @Override
    public void filter(String query) {
        adapter.getFilter().filter(query);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        mPresenter.getProjectTransactions(getProject().get_id());

    }

    public int getTransactionCount(){
        return this.adapter.getItemCount();
    }





    @Override
    public void renderAdapter(List<TransactionResponse> transactionList) {
        adapter.clear();
        for (TransactionResponse x: transactionList){
            adapter.add(x);
        }
    }

    @Override
    public void addTransaction(TransactionResponse transactionResponse) {
        adapter.add(transactionResponse);
    }

    @Override
    public void update(TransactionResponse transactionResponse) {
        adapter.update(transactionResponse);
    }

    @Override
    public void remove(TransactionResponse transactionResponse) {
        adapter.remove(transactionResponse);
    }

    @Override
    public void removeRelatedTransactions(Account account) {
        adapter.removeRelatedTransactions(account);
    }

    @Override
    public void onItemClick(int position) {
        if(getActivity() instanceof FinanceActivity){
            FinanceActivity fa = (FinanceActivity) getActivity();
            fa.startUpdateOrDeleteActivity(adapter.getItem(position));
        }

    }

    @Override
    public void onItemUpdate(int position) {


    }

    @Override
    public void onItemRemove(int position) {

    }


    @Override
    public void onImageClick(int position) {
        if(adapter.getItem(position).getImage()==null){
            Toast.makeText(getContext(), "No Attachment Found", Toast.LENGTH_SHORT).show();
        }else{
            startZoomImageActivity(adapter.getItem(position).getImage());
        }

    }
}
