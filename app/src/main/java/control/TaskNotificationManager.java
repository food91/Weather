package control;

import android.Manifest;
import android.content.Context;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;


import java.util.List;

import Entity.TencentLocationBean;
import Entity.WeatherData;
import acitivity.BaseActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import mInterface.OnSetActivityListener;
import util.Constant;

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
        onSetActivityListener.OpenNotiTime(open, Constant.NOTIFICATIONTIME,
                Constant.NOTIFICATIONTIME2);
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

    public void getWeather(Context context,Observer<Object> objectObserver){
        //返回经纬度
        RequestRetrofit.getLocation(context, new TencentLocationListener() {
            @Override
            public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
                //接受到经纬度，经纬度,返回当前城市名
                RequestRetrofit.ReviceLocationMessage(tencentLocation, new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        TencentLocationBean tencentLocationBean= (TencentLocationBean) o;
                        String city=tencentLocationBean.getResult().getAddress_component().getCity();
                        city=city.replace("市","");
                        Observable<WeatherData> observable= RequestRetrofit.GetHttpData(city);
                        observable.observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(objectObserver);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }

            @Override
            public void onStatusUpdate(String s, int i, String s1) {

            }
        });
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
