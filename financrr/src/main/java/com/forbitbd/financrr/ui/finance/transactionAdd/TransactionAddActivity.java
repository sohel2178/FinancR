package com.forbitbd.financrr.ui.finance.transactionAdd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.forbitbd.androidutils.dialog.DatePickerListener;
import com.forbitbd.androidutils.dialog.MyDatePickerFragment;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.AppPreference;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.Transaction;
import com.forbitbd.financrr.models.TransactionResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class TransactionAddActivity extends PrebaseActivity
        implements TransactionAddContract.View, View.OnClickListener {

    private TransactionAddPresenter mPresenter;

    private TextInputLayout tiDate,tiInvoice,tiPurpose,tiAmount,tiFrom,tiTo;
    private EditText etDate,etInvoice,etPurpose,etAmount;

    private ImageView ivImage;

    private AppCompatAutoCompleteTextView etFrom,etTo;

    private ArrayAdapter<Account> fromAdapter;
    private ArrayAdapter<Account> toAdapter;
    private int toPosition =-1;
    private int fromPosition =-1;

    private Project project;

    private byte[] bytes;

    private Date date;

    private MaterialButton btnBrowse,btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_add);
        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);
        mPresenter = new TransactionAddPresenter(this);
        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);
        getSupportActionBar().setTitle("Transaction Entry Form");

        setupBannerAd(R.id.adView);



        tiDate = findViewById(R.id.ti_date);
        tiInvoice = findViewById(R.id.ti_invoice);
        tiPurpose = findViewById(R.id.ti_purpose);
        tiAmount = findViewById(R.id.ti_amount);
        tiFrom = findViewById(R.id.ti_from);
        tiTo = findViewById(R.id.ti_to);

        etDate = findViewById(R.id.date);
        etInvoice = findViewById(R.id.invoice);
        etPurpose = findViewById(R.id.purpose);
        etAmount = findViewById(R.id.amount);
        etFrom = findViewById(R.id.et_from);
        etTo = findViewById(R.id.et_to);

        etTo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toPosition=i;
            }
        });

        etFrom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fromPosition = i;
            }
        });

        date = new Date();
        etDate.setText(MyUtil.getStringDate(date));

        ivImage = findViewById(R.id.image);

        fromAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        toAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);

        etTo.setAdapter(toAdapter);
        etFrom.setAdapter(fromAdapter);

        mPresenter.getProjectAccount(project.get_id());

        btnBrowse = findViewById(R.id.browse);
        btnSave = findViewById(R.id.save);

        etDate.setOnClickListener(this);
        btnBrowse.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AppPreference.getInstance(this).getCounter()>Constant.COUNTER){
            showInterAd();
        }
    }

    @Override
    public void clearPreError() {
        tiDate.setErrorEnabled(false);
        tiPurpose.setErrorEnabled(false);
        tiInvoice.setErrorEnabled(false);
        tiPurpose.setErrorEnabled(false);
        tiFrom.setErrorEnabled(false);
        tiTo.setErrorEnabled(false);
        tiAmount.setErrorEnabled(false);
    }

    @Override
    public void showError(String message, int fieldId) {
        switch (fieldId){
            case 1:
                tiFrom.setError(message);
                etFrom.requestFocus();
                break;

            case 2:
                tiTo.setError(message);
                etTo.requestFocus();
                break;

            case 3:
                tiFrom.setError(message);
                tiTo.setError(message);
                etFrom.requestFocus();
                break;
            case 4:
                tiInvoice.setError(message);
                etInvoice.requestFocus();
                break;

            case 5:
                tiPurpose.setError(message);
                etPurpose.requestFocus();
                break;

            case 6:
                tiAmount.setError(message);
                etAmount.requestFocus();
                break;
        }
    }

    @Override
    public void updateSpinnerAdapter(List<Account> accountList) {
        for (Account x:accountList){
            if(x.getType()==0){
                toAdapter.add(x);
            }else if(x.getType()==1){
                fromAdapter.add(x);
            }else {
                toAdapter.add(x);
                fromAdapter.add(x);
            }
        }
//        fromAdapter.addAll(accountList);
//        toAdapter.addAll(accountList);
    }

    @Override
    public void openCalendar() {
        MyDatePickerFragment myDateDialog = new MyDatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE,getString(R.string.select_transaction_date));
        myDateDialog.setArguments(bundle);
        myDateDialog.setCancelable(false);
        myDateDialog.setDatePickerListener(new DatePickerListener() {
            @Override
            public void onDatePick(long time) {
                date = new Date(time);
                etDate.setText(MyUtil.getStringDate(date));
            }
        });
        myDateDialog.show(getSupportFragmentManager(),"FFFF");
    }

    @Override
    public void openCameraActivity() {
        openCropImageActivity(9,16);
    }

    @Override
    public void complete(TransactionResponse transaction) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.TRANSACTION,transaction);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view==etDate){
            mPresenter.openCalendar();
        }else if(view==btnBrowse){
            mPresenter.browseClick();
        }else if(view==btnSave){

            Transaction transaction = new Transaction();
            transaction.setProject(project.get_id());
            transaction.setDate(date);
            Account to = null;
            Account from = null;

            if(fromPosition !=-1){
                from = fromAdapter.getItem(fromPosition);
                transaction.setFrom(from.get_id());
            }

            if(toPosition!=-1){
                to = toAdapter.getItem(toPosition);
                transaction.setTo(to.get_id());
            }


            transaction.setInvoice_no(etInvoice.getText().toString().trim());
            transaction.setPurpose(etPurpose.getText().toString().trim());

            try {
                transaction.setAmount(Double.parseDouble(etAmount.getText().toString().trim()));
            }catch (Exception e){
                e.printStackTrace();
            }

            boolean valid =mPresenter.validate(transaction);

            if(!valid){
                return;
            }

            mPresenter.saveTransaction(transaction,bytes);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());

                    if(bitmap.getWidth()<576){
                        showToast("Should Select Larger Image !");
                        return;
                    }

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80 /*ignored for PNG*/, bos);
                    bytes = bos.toByteArray();
                    ivImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
