package acitivity;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.google.android.material.navigation.NavigationView;

import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.xiekun.myapplication.R;

import Entity.UserData;
import butterknife.BindView;
import butterknife.ButterKnife;
import fragment.AboutFragment;
import fragment.MycareFragment;
import fragment.SearchFragment;
import fragment.WeatherFragment;
import util.UtilX;


public class WeatherActivity extends BaseActivity {

    @BindView(R.id.weather_main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.footer_item_setting)
    TextView footerItemSetting;
    @BindView(R.id.footer_item_out)
    TextView footerItemOut;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    WeatherFragment weatherfragment;
    AboutFragment aboutFragment;
    MycareFragment mycareFragment;
    SearchFragment searchFragment;
    private View offsetview;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView tv_head_id;
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @BindView(R.id.drawer_layout_ll_exit)
    LinearLayout linearLayoutexit;

    @BindView(R.id.drawer_layout_ll_set)
    LinearLayout linearLayoutset;

    @Override
    protected void setContentViewLayout(int... i) {
        super.setContentViewLayout(R.layout.drawerlayout);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        init();
        initDefaultFragment();
        onclick();
    }


    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("MyAppX");
    }

    public void initDefaultFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        weatherfragment=new WeatherFragment();
        aboutFragment = new AboutFragment();
        mycareFragment = new MycareFragment();
        searchFragment = new SearchFragment();
        fragmentTransaction.add(R.id.weather_fragment, aboutFragment);
        fragmentTransaction.add(R.id.weather_fragment, mycareFragment);
        fragmentTransaction.add(R.id.weather_fragment, searchFragment);
        fragmentTransaction.add(R.id.weather_fragment, weatherfragment);
        fragmentTransaction.commit();
        fragmentTransaction.hide(searchFragment)
                .hide(mycareFragment)
                .hide(aboutFragment);
        fragmentTransaction.show(weatherfragment);
    }

    private void onclick() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        linearLayoutexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        linearLayoutset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WeatherActivity.this, SetActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setNavigationView(){
        NavController navController = Navigation.findNavController(this, R.id.weather_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        tv_head_id=navigationView.getHeaderView(0).findViewById(R.id.tv_nav_header_id);
        tv_head_id.setText(UserData.getUserData().getName());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            FragmentTransaction fragmentTransaction;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(Gravity.START);
                switch (item.getItemId()){
                    case R.id.nav_share:
                        UtilX.allShare(WeatherActivity.this);
                        break;
                    case R.id.nav_send:
                        showEditTextDialog();
                        break;
                    case R.id.nav_mycare:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.hide(searchFragment)
                                .hide(weatherfragment)
                                .hide(aboutFragment)
                                .remove(mycareFragment);
                        mycareFragment=new MycareFragment();
                        fragmentTransaction.add(R.id.weather_fragment,mycareFragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.nav_about:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.hide(mycareFragment)
                                .hide(weatherfragment)
                                .hide(searchFragment)
                                .show(aboutFragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.nav_searh:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.hide(mycareFragment)
                                .hide(weatherfragment)
                                .hide(aboutFragment)
                                .show(searchFragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.weather_fragment:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.hide(mycareFragment)
                                .hide(searchFragment)
                                .hide(aboutFragment)
                                .show(weatherfragment);
                        fragmentTransaction.commit();
                        break;
                }

                return true;
            }
        });
    }

    private void setDrawerLayout(){
        drawerLayout = findViewById(R.id.drawer_layout);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.weather_fragment,
                R.id.nav_mycare, R.id.nav_send,R.id.nav_share,R.id.nav_about,R.id.nav_searh
        )
                .setDrawerLayout(drawerLayout)
                .build();
    }

     public void init() {
        showContent();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_format_list_bulleted_black_24dp);
        setDrawerLayout();
        setNavigationView();
         StatusBarUtil.setTranslucentForDrawerLayout(this,
                 drawerLayout, 0);
     }

    private void showEditTextDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(WeatherActivity.this);
        builder.setTitle("建议")
                .setPlaceholder("在此输入您的建议")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        if (text != null && text.length() > 0) {
                            Toast.makeText(WeatherActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(WeatherActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .create(mCurrentDialogStyle).show();
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.weather_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



}
