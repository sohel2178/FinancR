package com.forbitbd.financrr.ui.finance.report.monthlyTransaction;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.TransactionResponse;
import com.forbitbd.financrr.ui.finance.account.accountDetail.transactionDetail.TransactionDetailFragment;
import com.forbitbd.financrr.ui.finance.report.FinanceReportBaseFragment;
import com.forbitbd.financrr.ui.finance.report.monthlyTransaction.all.AllFragment;
import com.forbitbd.financrr.ui.finance.report.monthlyTransaction.summery.SummeryFragment;
import com.forbitbd.financrr.ui.finance.transaction.TransactionAdapter;
import com.forbitbd.financrr.ui.finance.transaction.TransactionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthlyTransactionFragment extends FinanceReportBaseFragment
        implements MonthlyTransactionContract.View, View.OnClickListener, TransactionListener {


    private MonthlyTransactionPresenter mPresenter;

    private TextView tvStatus;

    private int currentMonth,currentYear;

    private TextView tvPrev,tvNext;

    private BottomNavigationView bottomNavigationView;


    public MonthlyTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MonthlyTransactionPresenter(this);
        this.currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        this.currentYear = Calendar.getInstance().get(Calendar.YEAR);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monthly_transaction, container, false);
        initView(view);
        return view;

    }

    private void initView(View view) {

        tvPrev = view.findViewById(R.id.prev);
        tvNext = view.findViewById(R.id.next);
        tvStatus = view.findViewById(R.id.status);
        tvStatus.setText(getStringDate());




        tvPrev.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        mPresenter.processTransactions(getTransactions(),currentYear,currentMonth);

        bottomNavigationView = view.findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id==R.id.summery){
                    loadFragment(new SummeryFragment());
                    return true;
                }else if(id==R.id.transactions){
                    loadFragment(new AllFragment());
                    return true;
                }
                return false;
            }
        });

        loadFragment(new SummeryFragment());
    }

    public void loadFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.TRANSACTION, (Serializable) getTransactions());
        bundle.putInt("YEAR",currentYear);
        bundle.putInt("MONTH",currentMonth);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.monthly_container, fragment, "CURRENT_TRANSACTION");
        transaction.commit();
    }

    @Override
    public void onClick(View view) {

        if(view==tvPrev){
            decrease();
//            mPresenter.processTransactions(getTransactions(),currentYear,currentMonth);

        }else if(view==tvNext){
            increase();
//            mPresenter.processTransactions(getTransactions(),currentYear,currentMonth);
        }

        update();

    }


    private void update(){
        if(getChildFragmentManager().findFragmentByTag("CURRENT_TRANSACTION") instanceof AllFragment){
            AllFragment allF = (AllFragment) getChildFragmentManager().findFragmentByTag("CURRENT_TRANSACTION");
            allF.update(currentYear,currentMonth);
        }else if(getChildFragmentManager().findFragmentByTag("CURRENT_TRANSACTION") instanceof SummeryFragment){
            SummeryFragment allF = (SummeryFragment) getChildFragmentManager().findFragmentByTag("CURRENT_TRANSACTION");
            allF.update(currentYear,currentMonth);
        }
        tvStatus.setText(getStringDate());
    }

    @Override
    public void onImageClick(int position) {
//        startZoomImageActivity(adapter.getItem(position).getImage());
    }

    @Override
    public void onItemClick(int position) {
//        TransactionDetailFragment tdf = new TransactionDetailFragment();
//        tdf.setCancelable(false);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(Constant.TRANSACTION,adapter.getItem(position));
//        tdf.setArguments(bundle);
//
//        tdf.show(getChildFragmentManager(),"HHHH");

    }

    @Override
    public void onItemUpdate(int position) {

    }

    @Override
    public void onItemRemove(int position) {

    }

    private String getStringDate(){
        String month = getResources().getStringArray(R.array.month_array)[currentMonth];
        return month+" - "+currentYear;
    }

    private void increase(){
        currentMonth++;
        if(currentMonth>11){
            currentYear++;
            currentMonth= currentMonth%12;
        }
    }

    private void decrease(){
        currentMonth--;
        if(currentMonth<0){
            currentYear--;
            currentMonth= currentMonth+12;
        }
    }

    @Override
    public void renderAdapter(List<TransactionResponse> transactionList) {
        tvStatus.setText(getStringDate());
//        adapter.setItems(transactionList);
    }
}
