package com.forbitbd.financrr.ui.finance.transaction;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.forbitbd.androidutils.baseAdapter.BaseHolder;
import com.forbitbd.androidutils.utils.AppPreference;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.TransactionResponse;


public class TransactionHolder extends BaseHolder<TransactionResponse, TransactionListener> implements View.OnClickListener {
    TextView tvDate,tvAccount,tvPurpose,tvAmount,tvMonthYear;
    ImageView ivAttach;

    private TransactionListener listener;

    public TransactionHolder(View itemView, TransactionListener listener) {
        super(itemView, listener);
        this.listener = listener;

        tvDate = itemView.findViewById(R.id.date);
        tvMonthYear = itemView.findViewById(R.id.month_year);
        tvAccount = itemView.findViewById(R.id.account);
        tvPurpose = itemView.findViewById(R.id.purpose);
        tvAmount = itemView.findViewById(R.id.amount);

        ivAttach = itemView.findViewById(R.id.attach);

        if(ivAttach!=null){
            ivAttach.setOnClickListener(this);
        }



        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AppPreference.getInstance(view.getContext()).increaseCounter();
        if(view==itemView){
            listener.onItemClick(getAdapterPosition());
        }else if(view==ivAttach){
            listener.onImageClick(getAdapterPosition());
        }
    }

    @Override
    public void bind(TransactionResponse transactionResponse) {

      /*  Picasso.with(itemView.getContext())
                .load(transactionResponse.getImage())
                .into(ivImage);*/

        String date = MyUtil.getStringDate(transactionResponse.getDate());

        String[] dateArr = date.split("-");

        if(tvDate!=null){
            tvDate.setText(dateArr[0]);
        }


        tvMonthYear.setText(dateArr[1].concat(" - ").concat(dateArr[2]));
        tvAccount.setText("From ".concat(transactionResponse.getFrom().getName())
        .concat(" to ").concat(transactionResponse.getTo().getName()));

        tvPurpose.setText(transactionResponse.getPurpose());
        tvAmount.setText(String.valueOf(transactionResponse.getAmount()));

    }
}
