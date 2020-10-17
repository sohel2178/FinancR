package com.forbitbd.financrr.ui.finance.account;




import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.financrr.api.ApiClient;
import com.forbitbd.financrr.models.Account;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountPresenter implements AccountContract.Presenter {

    private AccountContract.View mView;

    public AccountPresenter(AccountContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getProjectAccounts(String projectId) {
        mView.showDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.getProjectAccounts(projectId)
                .enqueue(new Callback<List<Account>>() {
                    @Override
                    public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                        mView.hideDialog();

                        if(response.isSuccessful()){
                            mView.renderAdapter(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Account>> call, Throwable t) {
                        mView.hideDialog();
                    }
                });
    }

    @Override
    public void deleteAccountFromDatabase(final Account account) {
        mView.showDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.deleteAccount(account.getProject(),account.get_id())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        mView.hideDialog();

                        if(response.isSuccessful()){
                            mView.removeAccountFromAdapter(account);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        mView.hideDialog();
                    }
                });
    }
}
