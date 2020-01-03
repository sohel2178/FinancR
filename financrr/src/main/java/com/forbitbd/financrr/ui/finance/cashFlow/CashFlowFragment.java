package com.forbitbd.financrr.ui.finance.cashFlow;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.financrr.R;
import com.forbitbd.financrr.models.CashFlow;
import com.forbitbd.financrr.ui.finance.report.FinanceReportBaseFragment;
import com.forbitbd.financrr.utils.MyBarDataSet;
import com.forbitbd.financrr.utils.MyLabelFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CashFlowFragment extends FinanceReportBaseFragment implements CashFlowContract.View {

    private BarChart barChart;

    private CashFlowPresenter mPresenter;


    public CashFlowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new CashFlowPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cash_flow, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        barChart = view.findViewById(R.id.bar_chart);
        mPresenter.filterDailyCashAccountData(getTransactions());
    }

    @Override
    public void renderChart(List<CashFlow> cashFlowList) {

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(true);

        barChart.setDrawGridBackground(false);
        barChart.getLegend().setEnabled(false);


        List<String> xAxisLabels = new ArrayList<>();
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (CashFlow x: cashFlowList) {
            yVals1.add(new BarEntry(cashFlowList.indexOf(x), (float) x.getAmount()));
            xAxisLabels.add(MyUtil.getStringDate(x.getDate()));
        }

        IAxisValueFormatter xAxisFormatter = new MyLabelFormatter(xAxisLabels);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setLabelRotationAngle(90f);
        xAxis.setPosition(XAxis.XAxisPosition.TOP);

        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawLabels(false); // no axis labels
        leftAxis.setDrawAxisLine(false); // no axis line
        leftAxis.setDrawGridLines(false); // no grid lines
        leftAxis.setDrawZeroLine(true); // draw a zero line
        leftAxis.setEnabled(false);
        barChart.getAxisRight().setEnabled(false); // no right axis

        BarDataSet dataSet = new MyBarDataSet(yVals1,getString(R.string.cash_flow));
        dataSet.setColors(new int[]{ContextCompat.getColor(getContext(), R.color.green),
                ContextCompat.getColor(getContext(), R.color.red)});


        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(dataSet);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(12f);
        data.setValueTypeface(ResourcesCompat.getFont(getContext(), R.font.arima_madurai_medium));
        data.setBarWidth(0.9f);
        //data.setValueFormatter(new MyCurrencyFormatter(userLocalStore.getCurrency()));
        barChart.setData(data);
        barChart.animateXY(1000,1000);
        barChart.setVisibleXRangeMaximum(10f);
        barChart.invalidate();

    }
}
