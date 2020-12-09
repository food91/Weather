package control;

import android.Manifest;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;


import java.util.List;

import acitivity.BaseActivity;

public class TaskNotificationManager {

    public static TaskNotificationManager instance=null;

    public static synchronized TaskNotificationManager getInstance(){
        if(instance==null){
            instance=new TaskNotificationManager();
        }
        return instance;
    }

    private TaskNotificationManager(){};

    public OnSetActivityListener onSetActivityListener;

    public void morningWeatherTipSwitch(boolean open){
        onSetActivityListener.OpenNotiTime(open);
    }

    public void OpenNotiTime(boolean open){
        onSetActivityListener.OpenNotiTime(open);
    }

    public void abnormalWeatherTip(boolean open){
        onSetActivityListener.abnormalWeatherTip(open);
    }
    public void nightStop(boolean open){
        onSetActivityListener.nightStop(open);
    }
    public void nightUpdate(boolean open){
        onSetActivityListener.nightUpdate(open);
    }

    public void WeatherVoice(boolean open){
        onSetActivityListener.WeatherVoice(open);
    }

    

    public void applyRight(BaseActivity activity,RequestCallback requestCallback){
        PermissionX.init(activity)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
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
                        scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限",
                                "我已明白",
                                "取消");
                    }
                })
                .request(requestCallback);
    }



}
