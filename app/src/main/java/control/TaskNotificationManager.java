package control;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

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
import mBroadcast.NotificationReceiverBroadcast;
import mInterface.OnSetActivityListener;
import util.Constant;
import util.UtilX;

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



    public void recevierWeatherInfo(Context context,int day){
        TaskNotificationManager.getInstance().getWeather(this,
                new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        WeatherData weatherData= (WeatherData) o;
                        String title=weatherData.getCity();
                        String text="";
                        if(day==0)
                            text="今日温度";
                        else
                            text="明日温度"
                        text+=weatherData.getData().get(day).getTem();
                        NotificationManagerX.getInstance().sendChatMsg(context,title,text);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void openNotication(Context context,int day){
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int type = AlarmManager.RTC_WAKEUP;
        long triggerAtMillis = UtilX.getMillsTimeToday();
        //一天的毫秒数
        long intervalMillis = 86400000;
        Intent intent=new Intent(context, NotificationReceiverBroadcast.class);
        intent.setAction(Constant.ACTION_NOTIFICATION);
        intent.putExtra(Constant.BROADCAST_NOTIFICATION_DAY,day);
        PendingIntent contentIntent = PendingIntent.getActivity( this , 0 ,intent, 0 );
        manager.setInexactRepeating(type, triggerAtMillis, intervalMillis, contentIntent);
    }

}
