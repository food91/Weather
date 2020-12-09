package service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.xiekun.myapplication.R;


import control.OnSetActivityListener;
import util.Constant;
import util.UtilX;

public class TaskManageNotificationService extends Service implements OnSetActivityListener {

    private void showNotification(){

    }

    private void getLocation(){

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
                    sendChatMsg("今日天气",getTodayWeather());
                }
                if(i==1){
                    sendChatMsg("明日天气预报",getTomorrowWeather());
                }
            }
        }
    }

    private String getTomorrowWeather(){

    }

    private String getTodayWeather(){

    }

    public void sendChatMsg(String title ,String text) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
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

    private void getLocation(Activity activity){
        TencentLocationManager mLocationManager = TencentLocationManager.getInstance(activity);
        mLocationManager.requestSingleFreshLocation(null,
                new TencentLocationListener() {
                    @Override
                    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {

                        Toast.makeText(activity,str,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStatusUpdate(String s, int i, String s1) {

                    }
                }, Looper.getMainLooper());
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
