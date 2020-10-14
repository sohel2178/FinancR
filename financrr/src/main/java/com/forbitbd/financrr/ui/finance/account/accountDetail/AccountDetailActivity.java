package com.forbitbd.financrr.ui.finance.account.accountDetail;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.androidutils.utils.ViewPagerAdapter;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.TransactionResponse;
import com.forbitbd.financrr.ui.finance.account.accountDetail.transaction.AccountTransactionFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class AccountDetailActivity extends PrebaseActivity implements AccountDetailContract.View, View.OnClickListener {

    private AccountDetailPresenter mPresenter;

    private Account account;
    private Project project;

    private List<TransactionResponse> transactionList;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private RelativeLayout mContainer;
    private LinearLayout mHideContainer;
    private ImageView ivIndicator;
    private int height;
    private boolean isExpand;
    private TextView tvTitle,tvTransactionCount,tvDebit,tvCredit,tvBalanceText,tvBalance,tvOpeningBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);
        this.account = (Account) getIntent().getSerializableExtra(Constant.ACCOUNT);

        mPresenter = new AccountDetailPresenter(this);

        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);
        getSupportActionBar().setTitle(account.getName().concat(" Details"));

        mContainer = findViewById(R.id.top_container);
        mHideContainer = findViewById(R.id.hide_container);
        ivIndicator = findViewById(R.id.arrow);

        mHideContainer.post(new Runnable() {
            @Override
            public void run() {
                mHideContainer.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                height = mHideContainer.getMeasuredHeight();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mHideContainer.getLayoutParams();
                params.height = 1;

                mHideContainer.setLayoutParams(params);
                mHideContainer.setVisibility(View.VISIBLE);
            }
        });

        mContainer.setOnClickListener(this);

        tvTitle = findViewById(R.id.info);
        tvTransactionCount = findViewById(R.id.transaction_count);
        tvDebit = findViewById(R.id.debit);
        tvCredit = findViewById(R.id.credit);
        tvBalanceText = findViewById(R.id.balance_txt);
        tvBalance = findViewById(R.id.balance);
        tvOpeningBalance = findViewById(R.id.opening_balance);




        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPresenter.filterTransaction(account,transactionList,position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mPresenter.getAllTransactionsForAccount(account);
    }

    @Override
    public void initialize(List<TransactionResponse> transactionResponseList) {
        this.transactionList = transactionResponseList;
        mPresenter.updateGeneralInfo(account,transactionList);

        // Render Call For the First Time
        renderTransactionList(transactionList);
    }

    @Override
    public void renderInfo(int count, double debit, double credit, double balance, String text) {
        tvTitle.setText("General Info of ".concat(account.getName()));
        tvTransactionCount.setText(String.valueOf(count));
        tvOpeningBalance.setText(String.valueOf(account.getOpening_balance()));
        tvDebit.setText(String.valueOf(debit));
        tvCredit.setText(String.valueOf(credit));
        tvBalance.setText(String.valueOf(account.getOpening_balance()+debit-credit));
        tvBalanceText.setText(text);
    }

    @Override
    public void measureHeightAndAnimate() {
        this.height = MyUtil.getFullHeight(mHideContainer);
        animate();
    }

    @Override
    public void renderTransactionList(List<TransactionResponse> transactionResponseList) {
        AccountTransactionFragment atf = (AccountTransactionFragment) adapter.getItem(viewPager.getCurrentItem());
        atf.render(transactionResponseList);
    }

    @Override
    public void onClick(View view) {
        mPresenter.toggle();
    }

    private void animate(){
        ValueAnimator animator = ValueAnimator.ofFloat(1,height);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(300);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                int value;
                int rotation;

                if (isExpand) {
                    value = (int) (height * (1 - valueAnimator.getAnimatedFraction()));
                    rotation = (int) ((1 - valueAnimator.getAnimatedFraction()) * 180);
                } else {
                    value = (int) (height * valueAnimator.getAnimatedFraction());
                    rotation = (int) (180 * valueAnimator.getAnimatedFraction());
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, value);
                mHideContainer.setLayoutParams(params);
                ivIndicator.setRotation(rotation);
                mHideContainer.requestLayout();

                if (valueAnimator.getAnimatedFraction() == 1) {
                    isExpand = !isExpand;
                }

            }
        });

        animator.start();



    }

    private void setupViewPager(ViewPager viewPager) {
        if(adapter==null){
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
        }
        adapter.addFragment(new AccountTransactionFragment(), "ALL");
        adapter.addFragment(new AccountTransactionFragment(), "DEBIT");
        adapter.addFragment(new AccountTransactionFragment(), "CREDIT");
        //adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }
}
