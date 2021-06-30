package com.forbitbd.financrr.ui.newFinance;

public interface NewFinanceContract {

    interface Presenter{
        void filter(String query);
    }

    interface View{
        void filter(String query);

//        void showProgressDialog();
//        void showProgressDialog();

        //void showTapTargetView(String title,String content);

    }
}
