package com.forbitbd.financrr.ui.finance.report;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;


import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.androidutils.utils.ViewPagerAdapter;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.FinanceResponce;
import com.forbitbd.financrr.models.TransactionResponse;
import com.forbitbd.financrr.ui.finance.cashFlow.CashFlowFragment;
import com.forbitbd.financrr.ui.finance.report.dailyTransaction.DailyTransactionFragment;
import com.forbitbd.financrr.ui.finance.report.monthlyTransaction.MonthlyTransactionFragment;
import com.forbitbd.financrr.ui.finance.report.trialBalance.TrialBalanceFragment;

import java.util.List;

public class FinanceReportActivity extends PrebaseActivity
        implements FinanceReportContract.View, View.OnClickListener {

    private FinanceReportPresenter mPresenter;

    private TextView tvStatus;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;

    private List<TransactionResponse> transactionList;
    private List<Account> accountList;

    private Project project;

    TextView tvPrev,tvNext;

    private String[] titleArray= {"Trial Balance","Daily Transactions","Monthly Transactions","Cash Flow"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_report);
        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);
        mPresenter = new FinanceReportPresenter(this);

        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);
        getSupportActionBar().setTitle("Finance Report");

         tvPrev = findViewById(R.id.prev);
         tvNext = findViewById(R.id.next);
        tvStatus = findViewById(R.id.status);


        tvPrev.setOnClickListener(this);
        tvNext.setOnClickListener(this);

        mPresenter.getFinanceData(project.get_id());


    }

    private void setupViewPager(ViewPager viewPager) {
        if(pagerAdapter==null){
            pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        }
        pagerAdapter.addFragment(new TrialBalanceFragment(), "Trial Balance");
        pagerAdapter.addFragment(new DailyTransactionFragment(), "Daily Transaction");
        pagerAdapter.addFragment(new MonthlyTransactionFragment(), "Monthly Transaction");
        pagerAdapter.addFragment(new CashFlowFragment(), "Cash Flow");
        //pagerAdapter.addFragment(new TransactionFragment(), "Transactions");

        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onClick(View view) {

        if(view==tvPrev){
            if(viewPager.getCurrentItem()!=0){
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
            }
        }else if(view==tvNext){
            if(viewPager.getCurrentItem()!=titleArray.length-1){
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }

        }
    }

    @Override
    public void initList(FinanceResponce financeResponce) {
        this.accountList = financeResponce.getAccounts();
        this.transactionList = financeResponce.getTransactions();

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvStatus.setText(titleArray[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tvStatus.setText(titleArray[viewPager.getCurrentItem()]);
    }

    @Override
    public List<Account> getAccounts() {
        return this.accountList;
    }

    @Override
    public List<TransactionResponse> getTransactions() {
        return this.transactionList;
    }
}
