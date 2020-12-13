package service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
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

/**
 * The type Task manage notification service.
 */
public class TaskManageNotificationService extends Service implements OnSetActivityListener {


    private void TimerBroastSend(){
        AlarmManager alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int localHour=UtilX.getLocalTimeHour();
        //大于晚上7点，推送今天天气信息
        if(localHour>=Constant.NOTIFICATIONTIME2){
            long timeMills=System.currentTimeMillis();
            //小于晚7点，且大于早7点，推送今天消息
        }else(localHour>Constant.NOTIFICATIONTIME){

        }
    }


    private void reviceWeatherInfo(int day){
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
                  reviceWeatherInfo(0);
                }
                if(i==1){
                   reviceWeatherInfo(1);
                }
            }
        }
    }

    /**
     * Send chat msg.
     *
     * @param title the title
     * @param text  the text
     * @param time  the time
     */
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

    /**
     * The type Local binder.
     */
    public class LocalBinder extends Binder {
        /**
         * Gets service.
         *
         * @return the service
         */
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
