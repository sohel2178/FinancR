package com.forbitbd.financrr.ui.finance.transactionUpdate;

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
import com.forbitbd.androidutils.dialog.delete.DeleteDialog;
import com.forbitbd.androidutils.dialog.delete.DialogClickListener;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.Account;
import com.forbitbd.financrr.models.TransactionResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class TransactionUpdateActivity extends PrebaseActivity implements TransactionUpdateContract.View, View.OnClickListener {
    
    
    private TransactionResponse transactionResponse;
    private Project project;

    private TransactionUpdatePresenter mPresenter;


    private TextInputLayout tiDate,tiInvoice,tiPurpose,tiAmount,tiFrom,tiTo;
    private EditText etDate,etInvoice,etPurpose,etAmount;

    private AppCompatAutoCompleteTextView etFrom,etTo;

    private ImageView ivImage;

    private ArrayAdapter<Account> fromAdapter;
    private ArrayAdapter<Account> toAdapter;

    private MaterialButton btnBrowse,btnSave,btnDelete;

    private int toPosition =-1;
    private int fromPosition =-1;


    private byte[] bytes;

    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_update);
        
        transactionResponse = (TransactionResponse) getIntent().getSerializableExtra(Constant.TRANSACTION);
        project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);

        mPresenter = new TransactionUpdatePresenter(this);
        
        initView();


    }

    private void initView() {
        setupToolbar(R.id.toolbar);
        getSupportActionBar().setTitle("Transaction Update or Delete");

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
        etTo = findViewById(R.id.et_to);
        etFrom = findViewById(R.id.et_from);

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


        ivImage = findViewById(R.id.image);


        fromAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        toAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);

        etTo.setAdapter(toAdapter);
        etFrom.setAdapter(fromAdapter);

        mPresenter.getProjectAccount(project.get_id());

        btnBrowse = findViewById(R.id.browse);
        btnSave = findViewById(R.id.save);
        btnDelete = findViewById(R.id.delete);

        etDate.setOnClickListener(this);
        btnBrowse.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view==etDate){
            mPresenter.openCalendar();
        }else if(view==btnBrowse){
            mPresenter.browseClick();
        }else if(view==btnSave){
            transactionResponse.setProject(project.get_id());
            transactionResponse.setDate(date);

            Account to = null;
            Account from = null;

            if(fromPosition !=-1){
                from = fromAdapter.getItem(fromPosition);
                transactionResponse.setFrom(from);
            }

            if(toPosition!=-1){
                to = toAdapter.getItem(toPosition);
                transactionResponse.setTo(to);
            }
            transactionResponse.setInvoice_no(etInvoice.getText().toString().trim());
            transactionResponse.setPurpose(etPurpose.getText().toString().trim());

            try {
                transactionResponse.setAmount(Double.parseDouble(etAmount.getText().toString().trim()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            boolean valid = mPresenter.validate(transactionResponse);

            if (!valid) {
                return;
            }

            mPresenter.updateTransaction(transactionResponse, bytes);

        }else if(view==btnDelete){
            DeleteDialog deleteDialog = new DeleteDialog();
            deleteDialog.setCancelable(false);
            Bundle bundle = new Bundle();
            bundle.putString(Constant.CONTENT,"Do you really want to delete this transaction??");
            deleteDialog.setArguments(bundle);
            deleteDialog.setListener(new DialogClickListener() {
                @Override
                public void positiveButtonClick() {
                    mPresenter.deleteTransaction(transactionResponse);
                }
            });
            deleteDialog.show(getSupportFragmentManager(),"HJHHJHJ");

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
    public void updateSpinnerAdapter(List<Account> accountList) {
        fromAdapter.addAll(accountList);
        toAdapter.addAll(accountList);
        mPresenter.bind(transactionResponse);
    }

    @Override
    public void bind(TransactionResponse transactionResponse) {
        date = transactionResponse.getDate();
        etDate.setText(MyUtil.getStringDate(date));

        etInvoice.setText(transactionResponse.getInvoice_no());
        etPurpose.setText(transactionResponse.getPurpose());
        etAmount.setText(String.valueOf(transactionResponse.getAmount()));

        etFrom.setText(transactionResponse.getFrom().getName());
        etTo.setText(transactionResponse.getTo().getName());

        Picasso.with(this)
                .load(transactionResponse.getImage())
                .into(ivImage);

    }

    @Override
    public void updateTransaction(TransactionResponse transactionResponse) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.TRANSACTION,transactionResponse);
        bundle.putString(Constant.STATUS,Constant.UPDATE);

        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void deleteFromAdapter() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.TRANSACTION,transactionResponse);
        bundle.putString(Constant.STATUS,Constant.DELETE);

        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode,resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());

                    if(bitmap.getWidth()<576){
                        showToast("Should Select Larger Image !");
                        return;
                    }

//                    Bitmap scaledBitMap = MyUtil.getScaledBitmap(bitmap,200,200);
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
