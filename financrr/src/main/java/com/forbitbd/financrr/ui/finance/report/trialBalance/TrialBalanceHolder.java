package com.forbitbd.financrr.ui.finance.report.trialBalance;

import android.view.View;
import android.widget.TextView;

import com.forbitbd.androidutils.baseAdapter.BaseHolder;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.TrialBalance;


public class TrialBalanceHolder extends BaseHolder<TrialBalance,TrialBalanceListener> {

    TextView tvAccountName,tvDebit,tvCredit;


    public TrialBalanceHolder(View itemView, TrialBalanceListener listener) {
        super(itemView, listener);
        tvAccountName = itemView.findViewById(R.id.name);
        tvDebit = itemView.findViewById(R.id.debit);
        tvCredit = itemView.findViewById(R.id.credit);
    }

    @Override
    public void bind(TrialBalance trialBalance) {
        tvAccountName.setText(trialBalance.getAccount().getName());
        tvDebit.setText(String.valueOf(trialBalance.getDebit()));
        tvCredit.setText(String.valueOf(trialBalance.getCredit()));
    }
}
