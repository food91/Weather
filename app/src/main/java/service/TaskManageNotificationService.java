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

    private void SendTimeNotication(){
        int localHour=UtilX.getLocalTimeHour();
        //大于晚上7点，推送明天天气信息，并且设置时间，明天早上推送
        if(localHour>=Constant.NOTIFICATIONTIME2){
          //推送明天消息
            TaskNotificationManager.getInstance().recevierWeatherInfo(this,1);
        }else if(localHour>Constant.NOTIFICATIONTIME){
            //推送今天消息
            TaskNotificationManager.getInstance().recevierWeatherInfo(this,0);
        }
    }

    @Override
    public void OpenWeatherTip(boolean open) {
        if(!open){
            return;
        }
        SendTimeNotication();
        TaskNotificationManager.getInstance().openNotication(this,0);
        TaskNotificationManager.getInstance().openNotication(this,1);
    }


    @Override
    public void OpenWeatherDamage(boolean open) {
        if(!open){
            return;
        }
    }

    @Override
    public void abnormalWeatherTip(boolean open) {
        if(!open){
            return;
        }
    }

    @Override
    public void nightStop(boolean open) {
        if(!open){
            return;
        }
    }

    @Override
    public void nightUpdate(boolean open) {
        if(!open){
            return;
        }
    }

    @Override
    public void WeatherVoice(boolean open) {
        if(!open){
            return;
        }
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
    //    TaskNotificationManager.getInstance().startSetActivity();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
