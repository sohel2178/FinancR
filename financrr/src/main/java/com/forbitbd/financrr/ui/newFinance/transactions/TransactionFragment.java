package com.forbitbd.financrr.ui.newFinance.transactions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.utils.AppPreference;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.TransactionResponse;
import com.forbitbd.financrr.ui.finance.transaction.TransactionAdapter;
import com.forbitbd.financrr.ui.finance.transaction.TransactionContract;
import com.forbitbd.financrr.ui.finance.transaction.TransactionListener;
import com.forbitbd.financrr.ui.finance.transaction.TransactionPresenter;
import com.forbitbd.financrr.ui.finance.transactionAdd.TransactionAddActivity;
import com.forbitbd.financrr.ui.finance.transactionUpdate.TransactionUpdateActivity;
import com.forbitbd.financrr.ui.newFinance.TitleListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TransactionFragment extends Fragment implements TransactionContract.View, TransactionListener , View.OnClickListener {

    private static final int TRANSACTION_ADD=5000;
    private static final int TRANSACTION_UPDATE_DELETE=6000;
    private TransactionAdapter adapter;
    private TransactionPresenter mPresenter;
    private SharedProject sharedProject;
    private TitleListener listener;

    private FloatingActionButton fabAddTransaction;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.listener = (TitleListener) getActivity();
        mPresenter = new TransactionPresenter(this);
        adapter = new TransactionAdapter(getContext(),this);
        this.sharedProject = (SharedProject) getArguments().getSerializable(Constant.PROJECT);
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

        fabAddTransaction = view.findViewById(R.id.fab_add_transaction);
        fabAddTransaction.setOnClickListener(this);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        mPresenter.getProjectTransactions(sharedProject.getProject().get_id());

    }

    @Override
    public void onResume() {
        super.onResume();
        listener.setTitle("Transactions");
        listener.visibleMenu();
    }

    @Override
    public void onItemClick(int position) {
        AppPreference.getInstance(getContext()).increaseCounter();

        Intent intent = new Intent(getContext(), TransactionUpdateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,sharedProject.getProject());
        bundle.putSerializable("PERMISSION",sharedProject.getFinance());
        bundle.putSerializable(Constant.TRANSACTION,adapter.getItem(position));
        intent.putExtras(bundle);
        startActivityForResult(intent,TRANSACTION_UPDATE_DELETE);
    }

    @Override
    public void onItemUpdate(int position) {

    }

    @Override
    public void onItemRemove(int position) {

    }

    @Override
    public void showDialog() {
        listener.showProgressDialog();
    }

    @Override
    public void hideDialog() {
        listener.hideProgressDialog();
    }

    @Override
    public void renderAdapter(List<TransactionResponse> transactionList) {
        adapter.clear();
        for (TransactionResponse x: transactionList){
            adapter.add(x);
        }
    }

    public void filter(String query) {
        adapter.getFilter().filter(query);
    }

    @Override
    public void addTransaction(TransactionResponse transactionResponse) {
        adapter.addToFirst(transactionResponse);
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
    public void onImageClick(int position) {
        if(adapter.getItem(position).getImage()==null){
            Toast.makeText(getContext(), "No Attachment Found", Toast.LENGTH_SHORT).show();
        }else{
            listener.startZoomImageActivity(adapter.getItem(position).getImage());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("HHHHH","onActivityResult");

        if(requestCode==TRANSACTION_ADD && resultCode== Activity.RESULT_OK){
            TransactionResponse transaction = (TransactionResponse) data.getSerializableExtra(Constant.TRANSACTION);
            if(transaction!=null){
                Log.d("HHHHH","OK");
                adapter.addToFirst(transaction);
            }
        }else if(requestCode==TRANSACTION_UPDATE_DELETE && resultCode== Activity.RESULT_OK){
            TransactionResponse transaction = (TransactionResponse) data.getSerializableExtra(Constant.TRANSACTION);

            String status = data.getStringExtra(Constant.STATUS);

            if(status.equals(Constant.UPDATE)){
                adapter.update(transaction);
            }else if(status.equals(Constant.DELETE)){
                adapter.remove(transaction);
            }

        }
    }

    @Override
    public void onClick(View view) {
        AppPreference.getInstance(getContext()).increaseCounter();
        startAddTransactionActivity();
    }

    private void startAddTransactionActivity(){
        Intent intent = new Intent(getContext(), TransactionAddActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,sharedProject.getProject());
        intent.putExtras(bundle);
        startActivityForResult(intent,TRANSACTION_ADD);
    }


}
