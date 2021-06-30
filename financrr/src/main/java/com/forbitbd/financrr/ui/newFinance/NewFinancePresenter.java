package com.forbitbd.financrr.ui.newFinance;

public class NewFinancePresenter implements NewFinanceContract.Presenter {

    private NewFinanceContract.View mView;


    public NewFinancePresenter(NewFinanceContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void filter(String query) {
        mView.filter(query);
    }
}
