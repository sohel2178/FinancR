package com.forbitbd.financrr.ui.newFinance.dashboard.topfive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.TransactionResponse;

import java.util.ArrayList;
import java.util.List;

public class TrAdapter extends RecyclerView.Adapter<TrAdapter.TrHolder> {

    private List<TransactionResponse> transactionResponseList;
    private TrListener listener;


    public TrAdapter(TrListener listener) {
        this.listener = listener;
        this.transactionResponseList = new ArrayList<>();
    }

    @NonNull
    @Override
    public TrHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_plain_transaction,parent,false);
        return new TrHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrHolder holder, int position) {
        TransactionResponse transactionResponse = transactionResponseList.get(position);
        holder.bind(transactionResponse);
    }

    @Override
    public int getItemCount() {
        return transactionResponseList.size();
    }

    public void add(TransactionResponse transactionResponse){
        transactionResponseList.add(transactionResponse);
        int position = transactionResponseList.indexOf(transactionResponse);
        notifyItemInserted(position);
    }

    public void clear(){
        transactionResponseList.clear();
        notifyDataSetChanged();
    }


    class TrHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvDate,tvAccount,tvPurpose,tvAmount,tvMonthYear;

        public TrHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.date);
            tvMonthYear = itemView.findViewById(R.id.month_year);
            tvAccount = itemView.findViewById(R.id.account);
            tvPurpose = itemView.findViewById(R.id.purpose);
            tvAmount = itemView.findViewById(R.id.amount);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(transactionResponseList.get(getAdapterPosition()));
        }

        public void bind(TransactionResponse transactionResponse){
            String date = MyUtil.getStringDate(transactionResponse.getDate());

            String[] dateArr = date.split("-");

            tvDate.setText(dateArr[0]);
            tvMonthYear.setText(dateArr[1].concat(" - ").concat(dateArr[2]));
            tvAccount.setText("From ".concat(transactionResponse.getFrom().getName())
                    .concat(" to ").concat(transactionResponse.getTo().getName()));

            tvPurpose.setText(transactionResponse.getPurpose());
            tvAmount.setText(String.valueOf(transactionResponse.getAmount()));
        }
    }

    public interface TrListener{
        void onItemClick(TransactionResponse transactionResponse);
    }
}
