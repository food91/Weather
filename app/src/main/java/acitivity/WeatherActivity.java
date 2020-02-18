package acitivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.orhanobut.logger.Logger;
import com.xiekun.myapplication.R;

import Entity.UserData;
import butterknife.BindView;
import butterknife.ButterKnife;
import fragment.WeatherFragment;


public class WeatherActivity extends Xactivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.footer_item_setting)
    TextView footerItemSetting;
    @BindView(R.id.footer_item_out)
    TextView footerItemOut;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    WeatherFragment weatherfragment;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView tv_head_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout);
        ButterKnife.bind(this);
        init();
        onclick();
    }




/*    public void initDefaultFragment() {
        fragmentManager = getSupportFragmentManager();
        detailWeatherfragment = new DetailFragment();
        weatherfragment = new WeatherFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.weather_fragment, weatherfragment);
        fragmentTransaction.add(R.id.weather_fragment, detailWeatherfragment);
        fragmentTransaction.commit();
    }*/

    private void onclick() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


     public void init() {

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_format_list_bulleted_black_24dp);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setEnabled(true);
        drawerLayout = findViewById(R.id.drawer_layout);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.weather_fragment,
                R.id.nav_mycare, R.id.nav_send,R.id.nav_share,R.id.nav_tools,
                R.id.nav_slideshow)
                .setDrawerLayout(drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.weather_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        Logger.d("user=="+UserData.getUserData().toString());
        tv_head_id=navigationView.getHeaderView(0).findViewById(R.id.tv_nav_header_id);
        tv_head_id.setText(UserData.getUserData().getName());
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.weather_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
