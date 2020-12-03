package acitivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import com.jaeger.library.StatusBarUtil;
import com.xiekun.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import view.XSwitchView;

public class SetActivity extends BaseActivity {


    @BindView(R.id.tv_set_ac_tip)
    TextView tvSetAcTip;
    @BindView(R.id.tv_set_ac_tip_detail)
    TextView tvSetAcTipDetail;
    @BindView(R.id.xs_set_ac_tip)
    XSwitchView xsSetAcTip;
    @BindView(R.id.toolbar_set_ac)
    Toolbar toolbarSetAc;
    @BindView(R.id.tv_set_ac_warm)
    TextView tvSetAcWarm;
    @BindView(R.id.tv_set_ac_warm_detail)
    TextView tvSetAcWarmDetail;
    @BindView(R.id.xs_set_ac_warm)
    XSwitchView xsSetAcWarm;
    @BindView(R.id.tv_set_ac_abnormal)
    TextView tvSetAcAbnormal;
    @BindView(R.id.tv_set_ac_abnormal_detail)
    TextView tvSetAcAbnormalDetail;
    @BindView(R.id.xs_set_abnormal_tip)
    XSwitchView xsSetAbnormalTip;
    @BindView(R.id.tv_set_night_tip)
    TextView tvSetNightTip;
    @BindView(R.id.tv_set_ac_night_detail)
    TextView tvSetAcNightDetail;
    @BindView(R.id.xs_set_night_tip)
    XSwitchView xsSetNightTip;
    @BindView(R.id.tv_set_ac_tep)
    TextView tvSetAcTep;
    @BindView(R.id.tv_set_ac_wind)
    TextView tvSetAcWind;
    @BindView(R.id.tv_set_ac_nightupdate)
    TextView tvSetAcNightupdate;
    @BindView(R.id.tv_set_ac_nightupdate_detail)
    TextView tvSetAcNightupdateDetail;
    @BindView(R.id.xs_set_ac_nightupdate)
    XSwitchView xsSetAcNightupdate;
    @BindView(R.id.tv_set_ac_weathervocie)
    TextView tvSetAcWeathervocie;
    @BindView(R.id.tv_set_ac_weathervocie_detail)
    TextView tvSetAcWeathervocieDetail;
    @BindView(R.id.xs_set_ac_weathervocie)
    XSwitchView xsSetAcWeathervocie;



    protected void init() {
        showContent();
        StatusBarUtil.setColor(this, Color.parseColor("#ff00ddff"),0);
        setSupportActionBar(toolbarSetAc);
        toolbarSetAc.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void setContentViewLayout(int... layout) {
        super.setContentViewLayout(R.layout.activity_set);
    }

    @Override
    protected void initView() {
        showContent();
        ButterKnife.bind(this);
        init();
        onclick();
    }

    private void onclick(){
        xsSetAcTip.setOnStateChangedListener(new XSwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(XSwitchView view) {

            }

            @Override
            public void toggleToOff(XSwitchView view) {

            }
        });
        xsSetAcWarm.setOnStateChangedListener(new XSwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(XSwitchView view) {
                Notification notification = new NotificationCompat.Builder(SetActivity.this,
                        "1").
                        build();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(SetActivity.this,
                        "default");
                builder.setSmallIcon(R.drawable.icon_network_error);
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_about));
                builder.setAutoCancel(true);
                builder.setContentTitle("普通通知标题");
                builder.setContentInfo("这是一条通知信息，请查收");
                builder.setContentText("这是一条通知文本，请阅读");
                manager.notify(1,builder.build());
                view.setOpened(true);
            }

            @Override
            public void toggleToOff(XSwitchView view) {

            }
        });
    }
}
