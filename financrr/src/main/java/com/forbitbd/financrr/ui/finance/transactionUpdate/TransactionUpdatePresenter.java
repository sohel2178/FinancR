package com.forbitbd.financrr.ui.finance.transactionUpdate;


import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.financrr.api.ApiClient;
import com.forbitbd.financrr.api.ServiceGenerator;
import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.TransactionResponse;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionUpdatePresenter implements TransactionUpdateContract.Presenter {

    private TransactionUpdateContract.View mView;

    public TransactionUpdatePresenter(TransactionUpdateContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getProjectAccount(String projectID) {
        mView.showProgressDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.getProjectAccounts(projectID)
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
    public void bind(TransactionResponse transactionResponse) {
        mView.bind(transactionResponse);
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
    public boolean validate(TransactionResponse transaction) {
        mView.clearPreError();

        if(transaction.getDate()==null){
            mView.showToast("Please Select a Transaction Date");
            return false;
        }

        if(transaction.getFrom().get_id().equals(transaction.getTo().get_id())){
            mView.showToast("Transaction is not valid between Same Account !!!");
            return false;
        }

        if(transaction.getInvoice_no().equals("")){
            mView.showError("Empty Field is Not Allowed",1);
            return false;
        }

        if(transaction.getPurpose().equals("")){
            mView.showError("Empty Field is Not Allowed",2);
            return false;
        }

        if(transaction.getAmount()<=0){
            mView.showError("Transaction Amount Should be a Positive Value",3);
            return false;
        }

        return true;
    }

    @Override
    public void updateTransaction(TransactionResponse transactionResponse, byte[] bytes) {
        mView.showProgressDialog();

        MultipartBody.Part part=null;

        if(bytes!=null){
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes);
            part = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        }

        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), MyUtil.getStringDate(transactionResponse.getDate()));
        RequestBody from = RequestBody.create(MediaType.parse("text/plain"), transactionResponse.getFrom().get_id());
        RequestBody to = RequestBody.create(MediaType.parse("text/plain"), transactionResponse.getTo().get_id());
        RequestBody invoice_no = RequestBody.create(MediaType.parse("text/plain"), transactionResponse.getInvoice_no());
        RequestBody purpose = RequestBody.create(MediaType.parse("text/plain"), transactionResponse.getPurpose());
        RequestBody project = RequestBody.create(MediaType.parse("text/plain"), transactionResponse.getProject());
        RequestBody amount = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(transactionResponse.getAmount()));

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("date", date);
        map.put("from", from);
        map.put("to", to);
        map.put("invoice_no", invoice_no);
        map.put("purpose", purpose);
        map.put("project", project);
        map.put("amount", amount);

        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.updateTransaction(transactionResponse.getProject(),transactionResponse.get_id(),part,map)
                .enqueue(new Callback<TransactionResponse>() {
                    @Override
                    public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.updateTransaction(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<TransactionResponse> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }

    @Override
    public void deleteTransaction(TransactionResponse transactionResponse) {
        mView.showProgressDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.deleteTransaction(transactionResponse.getProject(),transactionResponse.get_id())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.deleteFromAdapter();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }
}
