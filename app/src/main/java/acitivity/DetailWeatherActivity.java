package acitivity;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.xiekun.myapplication.R;

import Entity.WeatherData;
import adapter.DetailWeatherRVAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import util.UtilX;
import view.TextViewRidus;
import view.WeatherDetailsView;


public class DetailWeatherActivity extends AppCompatActivity {

    private static final int Y_MAX = 10;
    private static final int Y_ADD = 10;
    private static final int Y_START = -10;

    WeatherDetailsView weatherDetailsView;
    TextViewRidus mTextViewRidus;
    Bitmap bitmap;
    View mViewNeedOffset;


    @BindView(R.id.weather_toolbar)
    Toolbar mToolbar;
    WeatherData weatherData;

    @BindView(R.id.md_wap)
    TextViewRidus mdWap;
    @BindView(R.id.tv_thisday)
    TextView tvThisday;
    @BindView(R.id.tv_thisday_ce)
    TextView tvThisdayCe;
    @BindView(R.id.tv_tomoroday)
    TextView tvTomoroday;
    @BindView(R.id.tv_tomoroday_ce)
    TextView tvTomorodayCe;
    @BindView(R.id.tv_afterto)
    TextView tvAfterto;
    @BindView(R.id.tv_afterto_ce)
    TextView tvAftertoCe;
    @BindView(R.id.view_need_offset)
    CoordinatorLayout viewNeedOffset;
    @BindView(R.id.iv_thisday_wea)
    ImageView ivThisdayWea;
    @BindView(R.id.iv_today_wea)
    ImageView ivTodayWea;
    @BindView(R.id.iv_aftertoday_wea)
    ImageView ivAftertodayWea;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.weatherd_view_wdv)
    WeatherDetailsView weatherdViewWdv;
    
    @BindView(R.id.rv_detailweather_ac)
    RecyclerView rvDetailweatherAc;
    @BindView(R.id.tr_md_index_desc)
    TextViewRidus trMdIndexDesc;
    @BindView(R.id.md_index_desc2)
    TextViewRidus mdIndexDesc2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        bitmap.recycle();
        weatherDetailsView.destroy();
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
        mTextViewRidus = findViewById(R.id.md_wap);
        StatusBarUtil.setTranslucentForImageView(this, 0, mViewNeedOffset);
    }

    protected void init() {
        getData();
        setSupportActionBar(mToolbar);
        weatherDetailsView = findViewById(R.id.weatherd_view_wdv);
        setviewdata(weatherData);
    }

    private void setviewdata(WeatherData weatherData) {
        if (weatherData != null && weatherDetailsView != null)
            weatherDetailsView.getData(weatherData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvDetailweatherAc.setLayoutManager(layoutManager);
        DetailWeatherRVAdapter detailWeatherRVAdapter = new DetailWeatherRVAdapter(
                this, weatherData
        );

        rvDetailweatherAc.setAdapter(detailWeatherRVAdapter);
    }

    private void setToolbar() {
        mToolbar.setTitle(weatherData.getCity());
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void getData() {
        weatherData = (WeatherData) getIntent().getSerializableExtra(WeatherData.DATANAME);
        if (weatherData != null) {
            mTextViewRidus.setText(weatherData.getData().get(0).getAir_tips());
            tvThisday.setText("今天·" + weatherData.getData().get(0).getWea());
            tvThisdayCe.setText(weatherData.getData().get(0).getTem());
            tvTomoroday.setText("明天·" + weatherData.getData().get(1).getWea());
            tvTomorodayCe.setText(weatherData.getData().get(1).getTem());
            tvTomoroday.setText("后天·" + weatherData.getData().get(2).getWea());
            tvAftertoCe.setText(weatherData.getData().get(2).getTem());
            bitmap = UtilX.getweatherBitmap(weatherData, this);
            ivThisdayWea.setImageBitmap(bitmap);
            bitmap = UtilX.getweatherBitmap(weatherData, 1, this);
            ivTodayWea.setImageBitmap(bitmap);
            bitmap = UtilX.getweatherBitmap(weatherData, 2, this);
            ivAftertodayWea.setImageBitmap(bitmap);
            trMdIndexDesc.setText(weatherData.getData().get(0).getIndex().get(0).getDesc());
            mdIndexDesc2.setText(weatherData.getData().get(0).getIndex().get(1).getDesc());
            Logger.d("weather==" + weatherData.toString() + "--" + weatherData.getData().get(0).toString());

        } else {
            Logger.d("weaterData is  null");
        }

    }


}
