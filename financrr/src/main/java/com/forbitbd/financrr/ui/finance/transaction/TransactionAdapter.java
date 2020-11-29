package com.forbitbd.financrr.ui.finance.transaction;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;


import com.forbitbd.androidutils.baseAdapter.BaseAdapter;
import com.forbitbd.androidutils.utils.AppPreference;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.TransactionResponse;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends BaseAdapter<TransactionResponse, TransactionListener, TransactionHolder> implements Filterable {

    private List<TransactionResponse> originalList;

    public TransactionAdapter(Context context, TransactionListener listener) {
        super(context, listener);
    }

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionHolder(inflate(R.layout.item_transaction,parent),getListener());
    }

    public void update(TransactionResponse transactionResponse){
        if(getPosition(transactionResponse)!=-1){
            update(transactionResponse,getPosition(transactionResponse));
        }
    }

    public void remove(TransactionResponse transactionResponse){
        int position = getPosition(transactionResponse);
        remove(position);
    }


    public void removeRelatedTransactions(Account account){

        List<TransactionResponse> tmpList = new ArrayList<>();
        for (TransactionResponse x: getItems()){
            if(x.getFrom().get_id().equals(account.get_id()) || x.getTo().get_id().equals(account.get_id())){

            }else{
                tmpList.add(x);
            }
        }

        setItems(tmpList);
    }




    private int getPosition(TransactionResponse transactionResponse){
        for (TransactionResponse x: getItems()){
            if(x.get_id().equals(transactionResponse.get_id())){
                return getItems().indexOf(x);
            }
        }

        return -1;
    }

    @Override
    public Filter getFilter() {
        if(originalList==null){
            this.originalList = getItems();
        }
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                List<TransactionResponse> filteredList = new ArrayList<>();

                if(charString.isEmpty()){
                    filteredList = originalList;
                }else{
                    List<TransactionResponse> tmpList = new ArrayList<>();
                    for (TransactionResponse x: originalList){
                        if(
                                x.getPurpose().toLowerCase().contains(charString.toLowerCase())
                                || x.getInvoice_no().toLowerCase().contains(charString.toLowerCase())
                                || x.getTo().getName().toLowerCase().contains(charString.toLowerCase())
                                || x.getFrom().getName().toLowerCase().contains(charString.toLowerCase())
                        ){
                            tmpList.add(x);
                        }
                    }

                    filteredList = tmpList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values=filteredList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                setItems((ArrayList<TransactionResponse>) filterResults.values);
            }
        };
    }
}
