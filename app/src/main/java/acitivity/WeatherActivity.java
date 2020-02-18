package acitivity;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
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


import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.xiekun.myapplication.R;

import Entity.UserData;
import butterknife.BindView;
import butterknife.ButterKnife;
import fragment.AboutFragment;
import fragment.AboutFragment_ViewBinding;
import fragment.MycareFragment;
import fragment.SearchFragment;
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
    AboutFragment aboutFragment;
    MycareFragment mycareFragment;
    SearchFragment searchFragment;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView tv_head_id;
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @BindView(R.id.drawer_layout_ll_exit)
    LinearLayout linearLayoutexit;

    @BindView(R.id.drawer_layout_ll_set)
    LinearLayout linearLayoutset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout);
        ButterKnife.bind(this);
        init();
        initDefaultFragment();
        onclick();

    }

    public void initDefaultFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        weatherfragment=new WeatherFragment();
        aboutFragment = new AboutFragment();
        mycareFragment = new MycareFragment();
        searchFragment = new SearchFragment();
        fragmentTransaction.add(R.id.weather_fragment, weatherfragment);
        fragmentTransaction.add(R.id.weather_fragment, aboutFragment);
        fragmentTransaction.add(R.id.weather_fragment, mycareFragment);
        fragmentTransaction.add(R.id.weather_fragment, searchFragment);
        fragmentTransaction.commit();
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
                R.id.nav_mycare, R.id.nav_send,R.id.nav_share,R.id.nav_about,R.id.nav_searh
                )
                .setDrawerLayout(drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.weather_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        Logger.d("user=="+UserData.getUserData().toString());
        tv_head_id=navigationView.getHeaderView(0).findViewById(R.id.tv_nav_header_id);
        tv_head_id.setText(UserData.getUserData().getName());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_share:
                        allShare();
                        return false;
                    case R.id.nav_send:
                        showEditTextDialog();
                        return false;
                    case R.id.nav_mycare:
                        mycareFragment.sh
                        break;
                }

                return true;
            }
        });
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

    public void allShare(){
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        share_intent.setType("text/plain");//设置分享内容的类型
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "share");//添加分享内容标题
        share_intent.putExtra(Intent.EXTRA_TEXT, "分享:"+"android");//添加分享内容
        //创建分享的Dialog
        share_intent = Intent.createChooser(share_intent, "share");
        startActivity(share_intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.weather_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
