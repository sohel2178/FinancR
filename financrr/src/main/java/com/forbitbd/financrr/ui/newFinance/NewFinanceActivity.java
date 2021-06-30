package com.forbitbd.financrr.ui.newFinance;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.ui.newFinance.accounts.AccountsFragment;
import com.forbitbd.financrr.ui.newFinance.dashboard.DashboardFragment;
import com.forbitbd.financrr.ui.newFinance.transactions.TransactionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class NewFinanceActivity extends PrebaseActivity implements NewFinanceContract.View,TitleListener {

//    private static final int READ_WRITE_PERMISSION=10000;
//    private static final int TRANSACTION_ADD=8000;
//    private static final int TRANSACTION_UPDATE_DELETE=10000;

    private SharedProject sharedProject;
    private NewFinancePresenter mPresenter;
    private SearchView searchView;
    private int loadedFragmentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_finance);
        this.sharedProject = (SharedProject) getIntent().getSerializableExtra(Constant.PROJECT);
        this.mPresenter = new NewFinancePresenter(this);

        setupToolbar(R.id.toolbar);

        initView();
    }

    private void initView() {

        setupBannerAd(R.id.adView);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.dashboard) {
                    loadFragment(new DashboardFragment());
                    return true;
                } else if (id == R.id.accounts) {
                    loadFragment(new AccountsFragment());
                    return true;
                }else if (id == R.id.transactions) {
                    loadFragment(new TransactionFragment());
                    return true;
                }
                return false;
            }
        });

        loadFragment(new DashboardFragment());
    }


    public void setTitle(String title){
        getSupportActionBar().setTitle(sharedProject.getProject().getName()+" "+title);
    }


    public void loadFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,sharedProject);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, "CURRENT_FRAGMENT");
        transaction.commit();
    }


    public SharedProject getSharedProject(){
        return this.sharedProject;
    }

    @Override
    public void invisibleMenu() {
        loadedFragmentPosition =0;
        invalidateOptionsMenu();
    }

    @Override
    public void visibleMenu() {
        loadedFragmentPosition =1;
        invalidateOptionsMenu();
    }

    public Project getProject(){
        return this.sharedProject.getProject();
    }




    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchMenuItem =  menu.findItem(R.id.search);
        if(loadedFragmentPosition==0){
            searchMenuItem.setVisible(false);
        }else {
            searchMenuItem.setVisible(true);
            Drawable drawable = searchMenuItem.getIcon();
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this,android.R.color.white));
            searchMenuItem.setIcon(drawable);
            searchView = (SearchView) searchMenuItem.getActionView();

            if(getSupportFragmentManager().findFragmentByTag("CURRENT_FRAGMENT") instanceof AccountsFragment){
                searchView.setQueryHint("Search Account by Name");
            }else{
                searchView.setQueryHint("Search by Account, Invoice or Purpose");
            }
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mPresenter.filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mPresenter.filter(newText);
                    return false;
                }
            });
        }

        return true;
    }

    @Override
    public void filter(String query) {

        if(getSupportFragmentManager().findFragmentByTag("CURRENT_FRAGMENT") instanceof AccountsFragment){
            AccountsFragment af = (AccountsFragment) getSupportFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
            af.filter(query);
        }else if(getSupportFragmentManager().findFragmentByTag("CURRENT_FRAGMENT") instanceof TransactionFragment){
            TransactionFragment tf = (TransactionFragment) getSupportFragmentManager().findFragmentByTag("CURRENT_FRAGMENT");
            tf.filter(query);
        }

    }
}