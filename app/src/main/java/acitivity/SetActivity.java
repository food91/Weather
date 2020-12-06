package acitivity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.xiekun.myapplication.R;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import service.TaskManageNotificationService;
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

    public static String CHANNEL_1 = "channel1";

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


    private void applyRight(){
        PermissionX.init(this)
                .permissions(Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS,
                        Manifest.permission.CALL_PHONE)
                .onExplainRequestReason(new ExplainReasonCallbackWithBeforeParam() {
                    @Override
                    public void onExplainReason(ExplainScope scope, List<String> deniedList, boolean beforeRequest) {
                        scope.showRequestReasonDialog(deniedList,
                                "即将申请的权限是程序必须依赖的权限",
                                "我已明白"
                        ,"取消");
                    }
                })
                .onForwardToSettings(new ForwardToSettingsCallback() {
                    @Override
                    public void onForwardToSettings(ForwardScope scope, List<String> deniedList) {
                        scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白",
                                "取消");
                    }
                })
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted) {
                            Toast.makeText(SetActivity.this, "所有申请的权限都已通过", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SetActivity.this, "您拒绝了如下权限：" + deniedList, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getLocation(){
        applyRight();
        TencentLocationManager mLocationManager = TencentLocationManager.getInstance(this);
        mLocationManager.requestSingleFreshLocation(null,
                new TencentLocationListener() {
                    @Override
                        public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
                        UtilX.LogX("s==="+s+"l=="+tencentLocation.getLatitude()
                        +"d =="+tencentLocation.getLongitude()
                        +"--i=="+i);
                        String str="s==="+s+"l=="+tencentLocation.getLatitude()
                                +"d =="+tencentLocation.getLongitude()
                                +"--i=="+i;
                        Toast.makeText(SetActivity.this,str,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStatusUpdate(String s, int i, String s1) {

                    }
                }, Looper.getMainLooper());
    }

    private void onclick(){
        xsSetAcTip.setOnStateChangedListener(new XSwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(XSwitchView view) {
                getLocation();
                view.setOpened(true);
            }

            @Override
            public void toggleToOff(XSwitchView view) {
                view.setOpened(false);
            }
        });
        xsSetAcWarm.setOnStateChangedListener(new XSwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(XSwitchView view) {
                sendChatMsg();
             //   TaskManageNotificationService.getTime();
                view.setOpened(true);
                UtilX.LogX("toggleToOn");
            }

            @Override
            public void toggleToOff(XSwitchView view) {
                view.setOpened(false);
            }
        });
    }

    public void sendChatMsg() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1)
                .setContentTitle("收到一条聊天消息")
                .setContentText("老八问候你吃了吗")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                .setAutoCancel(true)
                .setNumber(2)
                .build();
        manager.notify(1, notification);
    }
}
