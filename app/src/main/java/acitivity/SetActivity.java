package acitivity;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;


import com.jaeger.library.StatusBarUtil;
import com.permissionx.guolindev.callback.RequestCallback;
import com.xiekun.myapplication.R;

import java.util.List;

import Entity.UserData;
import butterknife.BindView;
import butterknife.ButterKnife;
import control.TaskNotificationManager;
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
        xsSetAcTip.setOpened(UserData.getUserData().getSetActivityBean().isSetAc_WeatherReport());
        xsSetAbnormalTip.setOpened(UserData.getUserData().getSetActivityBean().isSetAc_WeatherAbnormal());
        xsSetAcNightupdate.setOpened(UserData.getUserData().getSetActivityBean().isSetAc_NightUpdate());
        xsSetAcWarm.setOpened(UserData.getUserData().getSetActivityBean().isSetAc_WeatherDamage());
        xsSetAcWeathervocie.setOpened(UserData.getUserData().getSetActivityBean().isWeatherVoice());
        xsSetNightTip.setOpened(UserData.getUserData().getSetActivityBean().isSetAc_NightUpdate());
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
        //7-19天气提醒
        xsSetAcTip.setOnStateChangedListener(new XSwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(XSwitchView view) {
                //申请权限
                UtilX.applyRight(SetActivity.this, new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if(allGranted){
                            //获得了权限，开启开关，启动service
                            view.setOpened(true);
                            taskNotificationManager.onSetActivityListener.OpenWeatherTip(true);
                            UserData.getUserData().getSetActivityBean().setSetAc_WeatherReport(true);
                        }else{
                            view.setOpened(true);
                            view.setOpened(false);
                            taskNotificationManager.onSetActivityListener.OpenWeatherTip(false);
                            UserData.getUserData().getSetActivityBean().setSetAc_WeatherReport(false);
                        }
                    }
                });
            }
            @Override
            public void toggleToOff(XSwitchView view) {
                taskNotificationManager.onSetActivityListener.OpenWeatherTip(false);
                UserData.getUserData().getSetActivityBean().setSetAc_WeatherReport(false);
                view.setOpened(false);
            }
        });
        //天气预警提醒
        xsSetAcWarm.setOnStateChangedListener(new XSwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(XSwitchView view) {
                UtilX.applyRight(this, new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if(allGranted){
                            view.setOpened(true);
                            taskNotificationManager.onSetActivityListener.OpenWeatherDamage(true);
                            UserData.getUserData().getSetActivityBean().setSetAc_WeatherDamage(true);
                        }else{
                            view.setOpened(true);
                            view.setOpened(false);
                            taskNotificationManager.onSetActivityListener.OpenWeatherDamage(false);
                            UserData.getUserData().getSetActivityBean().setSetAc_WeatherDamage(false);
                        }
                    }
                });

            }
            @Override
            public void toggleToOff(XSwitchView view) {
                view.setOpened(false);
                taskNotificationManager.onSetActivityListener.OpenWeatherDamage(false);
                UserData.getUserData().getSetActivityBean().setSetAc_WeatherDamage(false);
            }
        });
        //天气异常提醒
        xsSetAbnormalTip.setOnStateChangedListener(new XSwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(XSwitchView view) {
                UtilX.applyRight(this, new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if(allGranted){
                            view.setOpened(true);
                            taskNotificationManager.onSetActivityListener.abnormalWeatherTip(true);
                            UserData.getUserData().getSetActivityBean().setSetAc_WeatherAbnormal(true);
                        }else{
                            view.setOpened(true);
                            view.setOpened(false);
                            taskNotificationManager.onSetActivityListener.abnormalWeatherTip(false);
                            UserData.getUserData().getSetActivityBean().setSetAc_WeatherAbnormal(false);
                        }
                    }
                });

            }
            @Override
            public void toggleToOff(XSwitchView view) {
                view.setOpened(false);
                taskNotificationManager.onSetActivityListener.abnormalWeatherTip(false);
                UserData.getUserData().getSetActivityBean().setSetAc_WeatherAbnormal(false);
            }
        });
        //夜间免打搅
        xsSetNightTip.setOnStateChangedListener(new XSwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(XSwitchView view) {
                UtilX.applyRight(this, new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if(allGranted){
                            view.setOpened(true);
                            taskNotificationManager.onSetActivityListener.nightStop(true);
                            UserData.getUserData().getSetActivityBean().setSetAc_NightStop(true);
                        }else{
                            view.setOpened(true);
                            view.setOpened(false);
                            taskNotificationManager.onSetActivityListener.nightStop(false);
                            UserData.getUserData().getSetActivityBean().setSetAc_NightStop(false);
                        }
                    }
                });

            }
            @Override
            public void toggleToOff(XSwitchView view) {
                view.setOpened(false);
                taskNotificationManager.onSetActivityListener.nightStop(false);
                UserData.getUserData().getSetActivityBean().setSetAc_NightStop(false);
            }
        });
        //夜间自动更新
        xsSetAcNightupdate.setOnStateChangedListener(new XSwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(XSwitchView view) {
                UtilX.applyRight(this, new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if(allGranted){
                            view.setOpened(true);
                            taskNotificationManager.onSetActivityListener.nightUpdate(true);
                            UserData.getUserData().getSetActivityBean().setSetAc_NightUpdate(true);
                        }else{
                            view.setOpened(true);
                            view.setOpened(false);
                            taskNotificationManager.onSetActivityListener.nightUpdate(false);
                            UserData.getUserData().getSetActivityBean().setSetAc_NightUpdate(false);
                        }
                    }
                });

            }
            @Override
            public void toggleToOff(XSwitchView view) {
                view.setOpened(false);
                taskNotificationManager.onSetActivityListener.nightUpdate(false);
                UserData.getUserData().getSetActivityBean().setSetAc_NightUpdate(false);
            }
        });
        //天气音效
        xsSetAcWarm.setOnStateChangedListener(new XSwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(XSwitchView view) {
                UtilX.applyRight(this, new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if(allGranted){
                            view.setOpened(true);
                            taskNotificationManager.onSetActivityListener.WeatherVoice(true);
                            UserData.getUserData().getSetActivityBean().setWeatherVoice(true);
                        }else{
                            view.setOpened(true);
                            view.setOpened(false);
                            taskNotificationManager.onSetActivityListener.WeatherVoice(false);
                            UserData.getUserData().getSetActivityBean().setWeatherVoice(false);
                        }
                    }
                });

            }
            @Override
            public void toggleToOff(XSwitchView view) {
                view.setOpened(false);
                taskNotificationManager.onSetActivityListener.WeatherVoice(false);
                UserData.getUserData().getSetActivityBean().setWeatherVoice(false);
            }
        });
    }

    @Override
    protected void onDestroy() {
        //保存配置文件
        UtilX.saveSetFile();
        super.onDestroy();
    }
}
