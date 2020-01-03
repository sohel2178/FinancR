package com.forbitbd.financrr.ui.finance.report.trialBalance;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.forbitbd.androidutils.baseAdapter.BaseAdapter;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.TrialBalance;

public class TrialBalanceAdapter extends BaseAdapter<TrialBalance,TrialBalanceListener,TrialBalanceHolder> {

    public TrialBalanceAdapter(Context context, TrialBalanceListener listener) {
        super(context, listener);
    }

    @NonNull
    @Override
    public TrialBalanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflate(R.layout.item_trial_balance,parent);
        return new TrialBalanceHolder(view,getListener());
    }
}
