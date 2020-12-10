package acitivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;


import com.jaeger.library.StatusBarUtil;
import com.permissionx.guolindev.callback.RequestCallback;
import com.xiekun.myapplication.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import control.TaskNotificationManager;
import service.TaskManageNotificationService;
import util.Constant;
import util.UtilX;
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
    private TaskNotificationManager taskNotificationManager;

    protected void init() {
        //测试，完成后需撤销
        GoService();
        taskNotificationManager=TaskNotificationManager.getInstance();
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

    private void GoService(){
        Intent intent=new Intent(this, TaskManageNotificationService.class);
        startService(intent);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                TaskManageNotificationService.LocalBinder localBinder=
                        (TaskManageNotificationService.LocalBinder) iBinder;
                TaskNotificationManager.getInstance().onSetActivityListener=localBinder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        }, Context.BIND_AUTO_CREATE);
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
                //申请权限
                taskNotificationManager.applyRight(SetActivity.this, new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if(allGranted){
                            //获得了权限，开启开关，启动service
                            view.setOpened(true);
                            taskNotificationManager.OpenNotiTime(true);
                        }else{
                            view.setOpened(true);
                            view.setOpened(false);
                            taskNotificationManager.OpenNotiTime(false);
                        }
                    }
                });
            }
            @Override
            public void toggleToOff(XSwitchView view) {
                view.setOpened(false);
            }
        });
        xsSetAcWarm.setOnStateChangedListener(new XSwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(XSwitchView view) {
                view.setOpened(true);
                UtilX.LogX("toggleToOn");
            }
            @Override
            public void toggleToOff(XSwitchView view) {
                view.setOpened(false);
            }
        });
    }


}
