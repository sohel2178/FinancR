package com.forbitbd.financrr.ui.finance.transaction;


import com.forbitbd.androidutils.baseAdapter.BaseListener;
import com.forbitbd.financrr.models.TransactionResponse;

public interface TransactionListener extends BaseListener<TransactionResponse> {
    void onImageClick(int position);
}
