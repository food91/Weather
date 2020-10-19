package acitivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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


public class DetailWeatherActivity extends Xactivity {

    private static final int Y_MAX = 10;
    private static final int Y_ADD = 10;
    private static final int Y_START = -10;

    WeatherData weatherData;
    int cityp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   //     StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_detail_weather);
        ButterKnife.bind(this);
        init();

    }

    @Override
    protected void init() {

        Intent intent=getIntent();
        weatherData= (WeatherData) intent.getSerializableExtra(WeatherData.DATANAME);
        cityp=  intent.getIntExtra(WeatherControl.CITYNUM, 0);
        Log.d("sss",weatherData.toString());
        Log.d("sss",cityp+"");


    }


}
