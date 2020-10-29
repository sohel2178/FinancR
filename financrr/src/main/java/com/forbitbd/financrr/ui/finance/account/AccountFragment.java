package com.forbitbd.financrr.ui.finance.account;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.androidutils.dialog.delete.DeleteDialog;
import com.forbitbd.androidutils.dialog.delete.DialogClickListener;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.ui.finance.FinanceBaseFragment;
import com.forbitbd.financrr.ui.finance.account.accountDetail.AccountDetailActivity;
import com.forbitbd.financrr.ui.finance.accountAdd.AddAccountFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends FinanceBaseFragment implements AccountContract.View{

    private AccountAdapter adapter;
    private AccountPresenter mPresenter;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AccountPresenter(this);
        adapter = new AccountAdapter(this,getSharedProject().getFinance());
    }

    @Override
    public void filter(String query) {
        adapter.getFilter().filter(query);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {


        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        mPresenter.getProjectAccounts(getProject().get_id());
    }



    @Override
    public void renderAdapter(List<Account> accountList) {
        adapter.clear();

        for (Account x: accountList){
            adapter.addAccount(x);
        }
    }

    @Override
    public void addAccount(Account account) {
        adapter.addAccountInPosition(account);
    }

    @Override
    public void editAccountRequest(Account account) {
        AddAccountFragment addAccountFragment = new AddAccountFragment();
        addAccountFragment.setCancelable(false);

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,getProject());
        bundle.putSerializable(Constant.ACCOUNT, account);
        addAccountFragment.setArguments(bundle);

        addAccountFragment.show(getChildFragmentManager(),"JJJJ");

    }

    @Override
    public void updateAccountInAdapter(Account account) {
        adapter.updateAccount(account);
    }

    @Override
    public void startAccountDetailActivity(Account account) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,getProject());
        bundle.putSerializable(Constant.ACCOUNT, account);

        Intent intent = new Intent(getContext(), AccountDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showDeleteDialog(final Account account) {
        final DeleteDialog deleteDialog = new DeleteDialog();
        deleteDialog.setCancelable(false);
        deleteDialog.setListener(new DialogClickListener() {
            @Override
            public void positiveButtonClick() {
                deleteDialog.dismiss();
                mPresenter.deleteAccountFromDatabase(account);
            }
        });

        Bundle bundle = new Bundle();
        bundle.putString(Constant.CONTENT,"Delete Account Also Remove All Related Transactions??");
        deleteDialog.setArguments(bundle);
        deleteDialog.show(getChildFragmentManager(),"DELETE");
    }

    @Override
    public void removeAccountFromAdapter(Account account) {
        adapter.removeAccount(account);
        get_activity().removeRelatedTransactions(account);
    }
}
