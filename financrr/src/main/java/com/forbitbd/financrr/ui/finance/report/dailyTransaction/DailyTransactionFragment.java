package com.forbitbd.financrr.ui.finance.report.dailyTransaction;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.androidutils.dialog.DatePickerListener;
import com.forbitbd.androidutils.dialog.MyDatePickerFragment;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.TransactionResponse;
import com.forbitbd.financrr.ui.finance.account.accountDetail.transactionDetail.TransactionDetailFragment;
import com.forbitbd.financrr.ui.finance.report.FinanceReportBaseFragment;
import com.forbitbd.financrr.ui.finance.transaction.TransactionAdapter;
import com.forbitbd.financrr.ui.finance.transaction.TransactionListener;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyTransactionFragment extends FinanceReportBaseFragment
        implements View.OnClickListener,
        DailyTransactionContract.View, TransactionListener {

    private TextView tvDate;

    private Date date;

    private DailyTransactionPresenter mPresenter;

    private TransactionAdapter adapter;


    public DailyTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DailyTransactionPresenter(this);
        date = new Date();
        adapter = new TransactionAdapter(getContext(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_transaction, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvDate = view.findViewById(R.id.date);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);



        ImageView ivCalendar = view.findViewById(R.id.fab_calender);
        ivCalendar.setOnClickListener(this);

        tvDate.setText("Transaction On ".concat(MyUtil.getStringDate(date)));
        mPresenter.filterData(getTransactions(),date);
    }

    @Override
    public void onClick(View view) {
        mPresenter.openCalender();
    }

    @Override
    public void openCalender() {

        MyDatePickerFragment myDateDialog = new MyDatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE,getString(R.string.select_transaction_date));
        myDateDialog.setArguments(bundle);
        myDateDialog.setCancelable(false);
        myDateDialog.setDatePickerListener(new DatePickerListener() {
            @Override
            public void onDatePick(long time) {
                date = new Date(time);
                tvDate.setText("Transaction On ".concat(MyUtil.getStringDate(date)));
                mPresenter.filterData(DailyTransactionFragment.this.getTransactions(), date);
            }
        });
        myDateDialog.show(getChildFragmentManager(),"FFFF");

    }

    @Override
    public void renderAdapter(List<TransactionResponse> transactionList) {
        adapter.setItems(transactionList);
    }

    @Override
    public void onImageClick(int position) {
        startZoomImageActivity(adapter.getItem(position).getImage());
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
}
