package com.forbitbd.financrr.ui.finance.accountAdd;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.ui.finance.FinanceActivity;
import com.forbitbd.financrr.ui.finance.account.AccountFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAccountFragment extends DialogFragment implements AddAccountContract.View, View.OnClickListener {

    private EditText etName;
    private TextInputLayout tiName;
    private AppCompatSpinner spType;

    private AddAccountPresenter mPresenter;

    TextView tvTitle;

    MaterialButton btnSave,btnCancel;



    private Account account;
    private Project project;



    public AddAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AddAccountPresenter(this);
        this.account = (Account) getArguments().getSerializable(Constant.ACCOUNT);
        this.project = (Project) getArguments().getSerializable(Constant.PROJECT);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_add_account, null);
        initView(view);


        AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.MyDialog).create();

        //AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.ThemeOverlay_AppCompat_Dialog);
        alertDialog.setView(view);
        return alertDialog;
    }

    private void initView(View view) {
        btnCancel = view.findViewById(R.id.cancel);
        btnSave = view.findViewById(R.id.save);
        tvTitle = view.findViewById(R.id.title);

        etName = view.findViewById(R.id.account_name);
        tiName = view.findViewById(R.id.ti_account_name);
        spType = view.findViewById(R.id.sp_types);
        spType.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.account_type)));

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        if(account!=null){
            mPresenter.bind(account);
        }
    }

    @Override
    public void onClick(View view) {

        if(view==btnSave){
            String name = etName.getText().toString().trim();

            if(account==null){
                account = new Account();
            }

            account.setProject(project.get_id());
            account.setName(name);
            account.setType(spType.getSelectedItemPosition());

            boolean valid = mPresenter.validate(account);

            if(!valid){
                return;
            }

            if(account.get_id()!=null){
                mPresenter.updateAccount(account);
            }else{
                mPresenter.saveAccount(account);
            }

        }else if(view==btnCancel){
            dismiss();
        }

    }

    @Override
    public void bind(Account account) {
        etName.setText(account.getName());
        spType.setSelection(account.getType());

        tvTitle.setText("Account Update Form");
        btnSave.setText("Update");
    }

    @Override
    public void showToast(String message) {
        //Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        etName.requestFocus();
        tiName.setError(message);
    }

    @Override
    public void clearPreError() {
        tiName.setErrorEnabled(false);
    }

    @Override
    public void showDialog() {
        if(getParentFragment() instanceof AccountFragment){
            AccountFragment af = (AccountFragment) getParentFragment();
            af.showDialog();
        }

    }

    @Override
    public void hideDialog() {
        if(getParentFragment() instanceof AccountFragment){
            AccountFragment af = (AccountFragment) getParentFragment();
            af.hideDialog();
        }
    }

    @Override
    public void complete(Account account) {
        dismiss();

        if(getActivity() instanceof FinanceActivity){
            FinanceActivity fa = (FinanceActivity) getActivity();
            fa.addAccount(account);
        }
    }

    @Override
    public void updateAccountInAdapter(Account account) {
        dismiss();

        if(getParentFragment() instanceof AccountFragment){
            AccountFragment af = (AccountFragment) getParentFragment();
            af.updateAccountInAdapter(account);
        }
    }
}
