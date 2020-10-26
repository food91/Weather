package acitivity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
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
import com.jaeger.library.StatusBarUtil;
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
import view.WeatherDetailsView;


public class DetailWeatherActivity extends AppCompatActivity {

    private static final int Y_MAX = 10;
    private static final int Y_ADD = 10;
    private static final int Y_START = -10;

    WeatherDetailsView weatherDetailsView;

    View mViewNeedOffset;


    @BindView(R.id.weather_toolbar)
     Toolbar mToolbar;
    WeatherData weatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather);
        ButterKnife.bind(this);
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolbar();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mViewNeedOffset = findViewById(R.id.view_need_offset);
        StatusBarUtil.setTranslucentForImageView(this,0, mViewNeedOffset);
    }

    protected void init() {

        getData();
        setSupportActionBar(mToolbar);
        weatherDetailsView=findViewById(R.id.weatherd_view_wdv);
        setviewdata(weatherData);
    }

    private void setviewdata(WeatherData weatherData){
        if(weatherData!=null&&weatherDetailsView!=null)
            weatherDetailsView.getData(weatherData);
    }

    private void setToolbar(){
        mToolbar.setTitle(weatherData.getCity());
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData(){
        Logger.d("getData");
        weatherData= (WeatherData) getIntent().getSerializableExtra(WeatherData.DATANAME);
        if(weatherData!=null){

            Logger.d(weatherData.toString());
        }else{
            Logger.d("weaterData is  null");
        }


    }


}
