package service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.xiekun.myapplication.R;


import Entity.WeatherData;
import control.RequestRetrofit;
import mInterface.OnSetActivityListener;
import control.TaskNotificationManager;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import util.Constant;
import util.UtilX;

public class TaskManageNotificationService extends Service implements OnSetActivityListener {

    @Override
    public void OpenNotiTime(boolean open, int ...h) {
        if(!open||h==null){
            return;
        }
        //获得时间
        int localHour=UtilX.getLocalTime();
        for(int i=0;i<h.length;i++){
            if(localHour==h[i]){
                if(i==0){
                    TaskNotificationManager.getInstance().getWeather(this,
                            new Observer<Object>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(Object o) {
                                    WeatherData weatherData= (WeatherData) o;
                                    String title=weatherData.getCity();
                                    String text="今日温度"+weatherData.getData().get(0).getTem();
                                    sendChatMsg(title,text);
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
                if(i==1){
                    TaskNotificationManager.getInstance().getWeather(this, new Observer<Object>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Object o) {
                            WeatherData weatherData= (WeatherData) o;
                            String title=weatherData.getCity();
                            String text="明日温度"+weatherData.getData().get(1).getTem();
                            sendChatMsg(title,text);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                }
            }
        }
       // sendChatMsg("明日天气预报",getTomorrowWeather());
        SendCityWeather();
    }

    private void SendCityWeather(){

    }


    private void getCityWeather(String city){
        Observable<WeatherData> weatherDataObservable= RequestRetrofit.GetHttpData(city);
        weatherDataObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeatherData weatherData) {
                        UtilX.LogX(weatherData.toString());
                        sendChatMsg(weatherData.getCity()+weatherData.getData().get(0).getDate()
                                ,weatherData.getData().get(0).getTem()+weatherData.getData().get(0).getAir_tips());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private String getTomorrowWeather(){
        String res="";
        return res;
    }

    public void sendChatMsg(String title ,String text,int ...time) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(Constant.CHANNEL_1, getString(R.string.app_name), NotificationManager.IMPORTANCE_LOW);
            mChannel.setDescription("notication channel");
            mChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
            manager .createNotificationChannel(mChannel);
        }
        Notification notification = new NotificationCompat.Builder(this, Constant.CHANNEL_1)
                .setContentTitle(title)
                .setContentText(text)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher_background))
                .setAutoCancel(true)
                .setNumber(2)
                .build();
        manager.notify(1, notification);
    }

    @Override
    public void weatherTip(boolean open) {

    }

    @Override
    public void abnormalWeatherTip(boolean open) {

    }

    @Override
    public void nightStop(boolean open) {

    }

    @Override
    public void nightUpdate(boolean open) {

    }

    @Override
    public void WeatherVoice(boolean open) {

    }




    private final IBinder mBinder = new LocalBinder();
    public class LocalBinder extends Binder {
       public TaskManageNotificationService getService() {
            // Return this instance of LocalService so clients can call public methods
            return TaskManageNotificationService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
