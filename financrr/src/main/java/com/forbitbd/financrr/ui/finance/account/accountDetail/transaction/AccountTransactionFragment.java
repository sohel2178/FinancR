package com.forbitbd.financrr.ui.finance.account.accountDetail.transaction;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.TransactionResponse;
import com.forbitbd.financrr.ui.finance.account.accountDetail.AccountDetailActivity;
import com.forbitbd.financrr.ui.finance.account.accountDetail.transactionDetail.TransactionDetailFragment;
import com.forbitbd.financrr.ui.finance.transaction.TransactionAdapter;
import com.forbitbd.financrr.ui.finance.transaction.TransactionListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountTransactionFragment extends Fragment
        implements TransactionListener {


    private TransactionAdapter adapter;


    public AccountTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TransactionAdapter(getContext(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_transaction, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }



    public void render(List<TransactionResponse> transactionResponseList) {
        adapter.clear();
        for (TransactionResponse x:transactionResponseList){
            adapter.add(x);
        }
    }

    @Override
    public void onItemClick(int position) {
        TransactionDetailFragment tdf = new TransactionDetailFragment();
        tdf.setCancelable(false);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.TRANSACTION,adapter.getItem(position));
        tdf.setArguments(bundle);

        tdf.show(getChildFragmentManager(),"HHHH");
    }

    @Override
    public void onItemUpdate(int position) {

    }

    @Override
    public void onItemRemove(int position) {

    }

    @Override
    public void onImageClick(int position) {
        if(getActivity() instanceof AccountDetailActivity){
            AccountDetailActivity activity = (AccountDetailActivity) getActivity();
            activity.startZoomImageActivity(adapter.getItem(position).getImage());
        }
    }
}
