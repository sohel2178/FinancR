package com.forbitbd.financrr.ui.finance.accountAdd;


import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.financrr.api.ApiClient;
import com.forbitbd.financrr.models.Account;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAccountPresenter implements AddAccountContract.Presenter {

    private AddAccountContract.View mView;

    public AddAccountPresenter(AddAccountContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void bind(Account account) {
        mView.bind(account);
    }

    @Override
    public boolean validate(Account account) {
        mView.clearPreError();

        if(account.getName().equals("")){
            mView.showToast("Account Name is Empty");
            return false;
        }

        return true;
    }

    @Override
    public void saveAccount(Account account) {
        mView.showDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.saveAccount(account.getProject(),account)
                .enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
                        mView.hideDialog();

                        if(response.isSuccessful()){
                            mView.complete(response.body());
                        }else if(response.code()==300){
                            mView.showToast("Account Already Exist");
                        }else{
                            mView.showToast("Error Happened in Saving Account");
                        }
                    }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {
                        mView.hideDialog();

                    }
                });


    }

    @Override
    public void updateAccount(Account account) {
        mView.showDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.updateAccount(account.getProject(),account.get_id(),account)
                .enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
                        mView.hideDialog();
                        if(response.isSuccessful()){
                            mView.updateAccountInAdapter(response.body());
                        }else if(response.code()==300){
                            mView.showToast("Account Already Exist");
                        }else{
                            mView.showToast("Error Happened in Saving Account");
                        }
                    }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {
                        mView.hideDialog();
                    }
                });
    }
}
