package com.forbitbd.financrr.ui.finance.account;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.utils.AppPreference;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.Account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountHolder> implements Filterable {

    private AccountContract.View mView;
    private List<Account> accountList;
    private List<Account> originalList;
    private SharedProject.Permission financePermission;

    public AccountAdapter(AccountContract.View  mView,SharedProject.Permission financePermission) {
        this.mView = mView;
        this.financePermission = financePermission;
        this.accountList = new ArrayList<>();
    }





    @NonNull
    @Override
    public AccountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_account,parent,false);
        return new AccountHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountHolder holder, int position) {
        Account account = accountList.get(position);
        holder.bind(account);
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public void clear(){
        accountList.clear();
        notifyDataSetChanged();
    }

    public void addAccount(Account account){
        accountList.add(account);
        int position = accountList.indexOf(account);
        notifyItemInserted(position);
    }


    public void addAccountInPosition(Account account){
        accountList.add(account);

        Collections.sort(accountList, new Comparator<Account>() {
            @Override
            public int compare(Account account, Account t1) {
                return account.getName().toLowerCase().compareTo(t1.getName().toLowerCase());
            }
        });

        notifyDataSetChanged();


//        int position = getPosition(account);
//        accountList.add(position,account);
//        notifyItemInserted(position);
    }

    private int getPosition(Account account){
        for (Account x: accountList){
            if(account.getName().compareTo(x.getName())>0){
                return accountList.indexOf(x)+1;
            }
        }
        return 0;
    }

    private int getCurrentPosition(Account account){
        for (Account x: accountList){
            if(account.get_id().equals(x.get_id())){
                return accountList.indexOf(x);
            }
        }
        return -1;
    }

    public void updateAccount(Account account){
        int currentPosition = getCurrentPosition(account);
        if(currentPosition != -1){
            accountList.remove(currentPosition);
            notifyItemRemoved(currentPosition);
            addAccountInPosition(account);
        }
    }

    public void removeAccount(Account account){
        int position = getCurrentPosition(account);
        accountList.remove(position);
        notifyItemRemoved(position);
    }






    public List<Account> getAccountList(){
        return this.accountList;
    }

    @Override
    public Filter getFilter() {
        if(originalList==null){
            originalList = accountList;
        }
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                List<Account> filteredList = new ArrayList<>();

                if(charString.isEmpty()){
                    filteredList = originalList;
                }else{
                    List<Account> tmpList = new ArrayList<>();
                    for (Account x: originalList){
                        if(x.getName().toLowerCase().contains(charString.toLowerCase())){
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
                accountList = (List<Account>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



    class AccountHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvType,tvName;
        ImageView ivEdit,ivDelete;

        public AccountHolder(@NonNull View itemView) {
            super(itemView);

            tvType = itemView.findViewById(R.id.type);
            tvName = itemView.findViewById(R.id.name);
            ivEdit = itemView.findViewById(R.id.edit);
            ivDelete = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(this);

            ivEdit.setOnClickListener(this);
            ivDelete.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            if(view==itemView){
                AppPreference.getInstance(view.getContext()).increaseCounter();
                mView.startAccountDetailActivity(accountList.get(getAdapterPosition()));
            }else if(view==ivEdit){
                AppPreference.getInstance(view.getContext()).increaseCounter();
                mView.editAccountRequest(accountList.get(getAdapterPosition()));
            }else if(view== ivDelete){
                AppPreference.getInstance(view.getContext()).increaseCounter();
                mView.showDeleteDialog(accountList.get(getAdapterPosition()));
            }

        }

        public void bind(Account account){
            tvType.setText(itemView.getResources().getStringArray(R.array.account_type)[account.getType()]);
            tvName.setText(account.getName());

            if(account.getName().equals("Cash")){
                ivDelete.setVisibility(View.INVISIBLE);
                ivEdit.setVisibility(View.INVISIBLE);
            }else {
                // Permission Base Visibility
                if(financePermission.isUpdate()){
                    ivEdit.setVisibility(View.VISIBLE);
                }else {
                    ivEdit.setVisibility(View.GONE);
                }

                if(financePermission.isDelete()){
                    ivDelete.setVisibility(View.VISIBLE);
                }else {
                    ivDelete.setVisibility(View.GONE);
                }
            }
        }
    }
}
