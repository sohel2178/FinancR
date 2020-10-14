package com.forbitbd.financrr.ui.finance.transactionAdd;



import android.util.Log;

import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.financrr.api.ApiClient;
import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.Transaction;
import com.forbitbd.financrr.models.TransactionResponse;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionAddPresenter implements TransactionAddContract.Presenter {

    private TransactionAddContract.View mView;

    public TransactionAddPresenter(TransactionAddContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getProjectAccount(String projectId) {
        mView.showProgressDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.getProjectAccounts(projectId)
                .enqueue(new Callback<List<Account>>() {
                    @Override
                    public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.updateSpinnerAdapter(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Account>> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }

    @Override
    public void openCalendar() {
        mView.openCalendar();
    }

    @Override
    public void browseClick() {
        mView.openCameraActivity();
    }

    @Override
    public boolean validate(Transaction transaction) {
        mView.clearPreError();

        if(transaction.getDate()==null){
            mView.showToast("Please Select a Transaction Date");
            return false;
        }

        if( transaction.getFrom()==null || transaction.getFrom().equals("")){
            Log.d("Call","Call");
            mView.showError("Choose an Account from Dropdown",1);
            return false;
        }

        if(transaction.getTo()==null || transaction.getTo().equals("")){
            mView.showError("Choose an Account from Dropdown",2);
            return false;
        }



        if(transaction.getFrom().equals(transaction.getTo())){
            mView.showError("Transaction is not valid between Same Account !!!",3);
            return false;
        }

        if(transaction.getInvoice_no().equals("")){
            mView.showError("Empty Field is Not Allowed",4);
            return false;
        }

        if(transaction.getPurpose().equals("")){
            mView.showError("Empty Field is Not Allowed",5);
            return false;
        }

        if(transaction.getAmount()<=0){
            mView.showError("Transaction Amount Should be a Positive Value",6);
            return false;
        }

        return true;
    }

    @Override
    public void saveTransaction(Transaction transaction, byte[] bytes) {
        mView.showProgressDialog();

        MultipartBody.Part part=null;

        if(bytes!=null){
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes);
            part = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        }

        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), MyUtil.getStringDate(transaction.getDate()));
        RequestBody from = RequestBody.create(MediaType.parse("text/plain"), transaction.getFrom());
        RequestBody to = RequestBody.create(MediaType.parse("text/plain"), transaction.getTo());
        RequestBody invoice_no = RequestBody.create(MediaType.parse("text/plain"), transaction.getInvoice_no());
        RequestBody purpose = RequestBody.create(MediaType.parse("text/plain"), transaction.getPurpose());
        RequestBody project = RequestBody.create(MediaType.parse("text/plain"), transaction.getProject());
        RequestBody amount = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(transaction.getAmount()));

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("date", date);
        map.put("from", from);
        map.put("to", to);
        map.put("invoice_no", invoice_no);
        map.put("purpose", purpose);
        map.put("project", project);
        map.put("amount", amount);



        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.saveTransaction(transaction.getProject(),part,map)
                .enqueue(new Callback<TransactionResponse>() {
                    @Override
                    public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.complete(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<TransactionResponse> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });





    }
}
