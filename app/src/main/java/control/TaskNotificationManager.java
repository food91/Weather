package control;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;


import Entity.SetActivityBean;
import Entity.TencentLocationBean;
import Entity.UserData;
import Entity.WeatherData;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import mBroadcast.NotificationReceiverBroadcast;
import mInterface.OnSetActivityListener;
import service.TaskManageNotificationService;
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

    public OnSetActivityListener onSetActivityListener=new OnSetActivityListener() {
        @Override
        public void OpenWeatherTip(boolean open, int... h) {
            onSetActivityListener.OpenWeatherTip(open, Constant.NOTIFICATIONTIME,
                    Constant.NOTIFICATIONTIME2);
        }

        @Override
        public void OpenWeatherDamage(boolean open) {
            onSetActivityListener.OpenWeatherDamage(open);
        }

        @Override
        public void abnormalWeatherTip(boolean open) {
            onSetActivityListener.abnormalWeatherTip(open);
        }

        @Override
        public void nightStop(boolean open) {
            onSetActivityListener.nightStop(open);
        }

        @Override
        public void nightUpdate(boolean open) {
            onSetActivityListener.nightUpdate(open);
        }

        @Override
        public void WeatherVoice(boolean open) {
            onSetActivityListener.WeatherVoice(open);
        }
    };


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

    public void startSetActivity(){
        if(UserData.getUserData()==null||UserData.getUserData().getSetActivityBean()==null){
            UtilX.LogX("UserDay is null");
            return;
        }
        SetActivityBean setActivityBean=UserData.getUserData().getSetActivityBean();
        onSetActivityListener.OpenWeatherTip(setActivityBean.isSetAc_WeatherReport());
        onSetActivityListener.OpenWeatherDamage(setActivityBean.isSetAc_WeatherReport());
        onSetActivityListener.abnormalWeatherTip(setActivityBean.isSetAc_WeatherAbnormal());
        onSetActivityListener.nightStop(setActivityBean.isSetAc_NightStop());
        onSetActivityListener.nightUpdate(setActivityBean.isSetAc_NightUpdate());
        onSetActivityListener.WeatherVoice(setActivityBean.isWeatherVoice());
    }

    public void recevierWeatherInfo(Context context,int day){
        TaskNotificationManager.getInstance().getWeather(context,
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
                            text="明日温度";
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
        PendingIntent contentIntent = PendingIntent.getActivity( context , 0 ,intent, 0 );
        manager.setInexactRepeating(type, triggerAtMillis, intervalMillis, contentIntent);
    }

    public static void GoService(Context constant,ServiceConnection serviceConnection){
        Intent intent=new Intent(constant, TaskManageNotificationService.class);

        constant.bindService(intent, new ServiceConnection() {
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
        constant.startService(intent);
    }
}
