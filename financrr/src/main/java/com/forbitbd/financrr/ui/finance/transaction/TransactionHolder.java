package com.forbitbd.financrr.ui.finance.transaction;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.forbitbd.androidutils.baseAdapter.BaseHolder;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.TransactionResponse;
import com.squareup.picasso.Picasso;


public class TransactionHolder extends BaseHolder<TransactionResponse, TransactionListener> implements View.OnClickListener {
    TextView tvDate,tvAccount,tvPurpose,tvAmount,tvMonthYear;
    ImageView ivImage;

    private TransactionListener listener;

    public TransactionHolder(View itemView, TransactionListener listener) {
        super(itemView, listener);
        this.listener = listener;

        tvDate = itemView.findViewById(R.id.date);
        tvMonthYear = itemView.findViewById(R.id.month_year);
        tvAccount = itemView.findViewById(R.id.account);
        tvPurpose = itemView.findViewById(R.id.purpose);
        tvAmount = itemView.findViewById(R.id.amount);

        ivImage = itemView.findViewById(R.id.image);

        ivImage.setOnClickListener(this);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==itemView){
            listener.onItemClick(getAdapterPosition());
        }else if(view==ivImage){
            listener.onImageClick(getAdapterPosition());
        }
    }

    @Override
    public void bind(TransactionResponse transactionResponse) {

        Picasso.with(itemView.getContext())
                .load(transactionResponse.getImage())
                .into(ivImage);

        String date = MyUtil.getStringDate(transactionResponse.getDate());

        String[] dateArr = date.split("-");

        tvDate.setText(dateArr[0]);
        tvMonthYear.setText(dateArr[1].concat(" - ").concat(dateArr[2]));
        tvAccount.setText(transactionResponse.getFrom().getName()
        .concat(" to ").concat(transactionResponse.getTo().getName()));

        tvPurpose.setText(transactionResponse.getPurpose());
        tvAmount.setText(String.valueOf(transactionResponse.getAmount()));

    }
}
