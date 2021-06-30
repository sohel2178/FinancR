package com.forbitbd.financrr.ui.newFinance.dashboard;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.ui.newFinance.TitleListener;
import com.forbitbd.financrr.ui.newFinance.dashboard.recent.RecentFiveFragment;
import com.forbitbd.financrr.ui.newFinance.dashboard.topfive.TopFiveFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class DashboardFragment extends Fragment {


    private TitleListener listener;

    private BottomNavigationView bottomNavigationView;


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.listener = (TitleListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.top_five) {
                    loadFragment(new TopFiveFragment());
                    return true;
                } else if (id == R.id.recent) {
                    loadFragment(new RecentFiveFragment());
                    return true;
                }
                return false;
            }
        });

        loadFragment(new TopFiveFragment());

    }


    public void loadFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,listener.getSharedProject());
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.transaction_container, fragment, "CURRENT_TRANSACTION");
        transaction.commit();
    }


    @Override
    public void onResume() {
        super.onResume();
        listener.setTitle("Dashboard");
        listener.invisibleMenu();
    }
}