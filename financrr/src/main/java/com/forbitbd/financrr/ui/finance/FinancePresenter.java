package com.forbitbd.financrr.ui.finance;

public class FinancePresenter implements FinanceContract.Presenter {

    private FinanceContract.View mView;

    public FinancePresenter(FinanceContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void filter(String query) {
        mView.filter(query);
    }

    @Override
    public void showAccountAddDialog() {
        mView.showAccountAddDialog();
    }

    @Override
    public void startAddTransactionActivity() {
        mView.startAddTransactionActivity();
    }

    @Override
    public void startFinanceReportActivity() {
        mView.startFinanceReportActivity();
    }
}
