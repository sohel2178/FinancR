package com.forbitbd.financrr.ui.finance.account.accountDetail.transactionDetail;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.TransactionResponse;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionDetailFragment extends DialogFragment {

    private TransactionResponse transactionResponse;


    public TransactionDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.transactionResponse = (TransactionResponse) getArguments().getSerializable(Constant.TRANSACTION);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_transaction_detail, null);
        initView(view);


        AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.MyDialog).create();

        //AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.ThemeOverlay_AppCompat_Dialog);
        alertDialog.setView(view);
        return alertDialog;
    }

    private void initView(View view) {
        TextView tvTitle = view.findViewById(R.id.title);
        TextView tvDate = view.findViewById(R.id.date);
        TextView tvInvoice = view.findViewById(R.id.invoice);
        TextView tvPurpose = view.findViewById(R.id.purpose);
        TextView tvAmount = view.findViewById(R.id.amount);
        MaterialButton btnOk = view.findViewById(R.id.ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        if(transactionResponse !=null){
            tvTitle.setText("From ".concat(transactionResponse.getFrom().getName())
            .concat(" to ").concat(transactionResponse.getTo().getName()));

            tvInvoice.setText(transactionResponse.getInvoice_no());
            tvPurpose.setText(transactionResponse.getPurpose());
            tvDate.setText(MyUtil.getStringDate(transactionResponse.getDate()));
            tvAmount.setText(String.valueOf(transactionResponse.getAmount()));
        }


    }



}
