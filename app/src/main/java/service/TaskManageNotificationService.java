package service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.xiekun.myapplication.R;


import java.util.Date;

import Entity.WeatherData;
import control.RequestRetrofit;
import mBroadcast.NotificationReceiverBroadcast;
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


    private void openNotication(String title,String text){
        AlarmManager alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //getTime()：日期的该方法同样可以表示从1970年1月1日0点至今所经历的毫秒数
        int type = AlarmManager.RTC_WAKEUP;
        long triggerAtMillis =UtilX.getMillsTimeToday();
        //一天的毫秒数
        long intervalMillis = 86400000;
        Intent intent=new Intent(this, NotificationReceiverBroadcast.class);
        intent.putExtra(Constant.BROADCAST_NOTIFICATION_TITLE,title);
        intent.putExtra(Constant.BROADCAST_NOTIFICATION_TEXT,text);
        intent.setAction(Constant.ACTION_NOTIFICATION);
        PendingIntent contentIntent = PendingIntent.getActivity( this , 0 ,intent, 0 );
        manager.setInexactRepeating(type, triggerAtMillis, intervalMillis, contentIntent);
    }

    private void TimerBroastSend(){

        int localHour=UtilX.getLocalTimeHour();
        //大于晚上7点，推送明天天气信息，并且设置时间，明天早上推送
        if(localHour>=Constant.NOTIFICATIONTIME2){
          //推送明天消息
            reviceWeatherInfo(1);

        }else if(localHour>Constant.NOTIFICATIONTIME){
            //推送今天消息
            reviceWeatherInfo(0);
        }
    }

    private void sendNotication(long timeMills){

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
