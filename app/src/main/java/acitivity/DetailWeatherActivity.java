package acitivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUIFontFitTextView;
import com.xiekun.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import Entity.WeatherData;
import butterknife.BindView;
import butterknife.ButterKnife;
import util.UtilX;
import control.WeatherControl;
import view.DetailsMarkerView;


public class DetailWeatherActivity extends Xactivity {

    private static final int Y_MAX = 10;
    private static final int Y_ADD = 10;
    private static final int Y_START = -10;
    int pcity = 0;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    WeatherData weatherData;
    @BindView(R.id.line)
    LineChart line;

    private static final int Ynum = 9;
    @BindView(R.id.tv_weater_tip)
    QMUIFontFitTextView tvWeaterTip;
    @BindView(R.id.card_linechart)
    CardView cardLinechart;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.iv_toolbar)
    ImageView ivToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather);
        ButterKnife.bind(this);
        init();
        onclick();
    }

    private void onclick() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();

    }

    @Override
    protected void init() {

        Logger.d("-----"+getClass().getName());
        Intent intent = getIntent();
        if (intent != null) {
            weatherData = (WeatherData) intent.getSerializableExtra(WeatherData.DATANAME);
            pcity = intent.getIntExtra(WeatherControl.CITYNUM, 0);
            Logger.d("w===" + weatherData.toString() + "--i=" + pcity);
        }
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.qmui_icon_topbar_back);
        collapsingToolbarLayout.setTitle(weatherData.getCity());
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < weatherData.getData().size(); i++) {
            Entry entry = new Entry(i,
                    UtilX.CentigradeStringToInt(weatherData.getData().get(i).getTem()));
            entry.setData(weatherData.getData().get(i).getWea());
            entries.add(entry);
        }
        LineDataSet dataSet = new LineDataSet(entries, "");
        dataSet.setColor(Color.BLUE);//线条颜色
        dataSet.setCircleColor(Color.GREEN);//圆点颜色
        dataSet.setLineWidth(1f);//线条宽度
        line.setDrawBorders(false);
        line.setBackgroundColor(Color.WHITE);
        line.setDrawGridBackground(false);
        setY();
        LineData lineData = new LineData(dataSet);
        line.setData(lineData);
        setX();
        setView();
        tvWeaterTip.setText("温馨提示:  " + weatherData.getData().get(0).getAir_tips());

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void setView() {

        MarkerView markerView = new DetailsMarkerView(this, R.layout.detailmarker_layout, weatherData);
        markerView.setChartView(line);
        line.setMarker(markerView);
        line.getViewPortHandler().setMinimumScaleX(1.0f);


        line.getViewPortHandler().setMinimumScaleY(1.0f);

    }

    private void setY() {
        YAxis rightAxis = line.getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        rightAxis.setTextSize(getResources().getDimension(R.dimen.view_text));
        rightAxis.setDrawGridLines(false);
        YAxis leftAxis = line.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        int y = Y_START;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < weatherData.getData().size(); i++) {
            list.add(y + "℃");
            y += Y_ADD;
        }
        leftAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return value + "℃";
            }
        });
        leftAxis.setTextSize(getResources().getDimension(R.dimen.view_text));
        Description description = new Description();
        description.setEnabled(false);
        line.setDescription(description);

        //设置图表左边的y轴禁用
        //   leftAxis.setEnabled(false);
    }

    private void setX() {
        XAxis xAxis = line.getXAxis();

        //设置x轴
        xAxis.setTextColor(Color.parseColor("#333333"));
        xAxis.setTextSize(11f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setGranularity(1f);//禁止放大后x轴标签重绘
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(getResources().getDimension(R.dimen.view_text));
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                int i = (int) value;
                return UtilX.DataFormat(weatherData.getData().get(i).getDate());
            }
        });
    }


}
