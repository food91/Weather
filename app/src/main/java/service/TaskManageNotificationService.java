package service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.xiekun.myapplication.R;


import java.util.HashMap;
import java.util.Map;

import Entity.TencentLocationBean;
import control.OnSetActivityListener;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import mInterface.ApiUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import util.Constant;
import util.UtilX;

public class TaskManageNotificationService extends Service implements OnSetActivityListener {

    private void showNotification(){

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
        getLocation(this);
    }

    private String getTomorrowWeather(){
        String res="";
        return res;
    }

    private String getTodayWeather(){
        String res="";
        return res;
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

    private void getLocation(Context context){
        TencentLocationManager mLocationManager = TencentLocationManager.getInstance(context);
        mLocationManager.requestSingleFreshLocation(null,
                new TencentLocationListener() {
                    @Override
                    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
                        //拦截器获取日志
                        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                            @Override
                            public void log(String message) {
                                //打印retrofit日志
                                Log.i("RetrofitLog","retrofitBack = "+message);
                            }
                        });
                        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        OkHttpClient client = new OkHttpClient.Builder()//okhttp设置部分，此处还可再设置网络参数
                                .addInterceptor(loggingInterceptor)
                                .build();

                        Retrofit retrofit=new Retrofit.Builder()
                                .baseUrl(ApiUrl.TencentApiUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .client(client)
                                .build();
                        Map<String,String> map=new HashMap<>();
                        map.put("location",tencentLocation.getLatitude()+","+tencentLocation.getLongitude());
                        map.put("key",ApiUrl.TencentApiKey);
                        Observable<TencentLocationBean> observable=retrofit.create(ApiUrl.IPLocation.class).
                                getCity(ApiUrl.TencentApiUrlpath,map
                                      );
                        observable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<TencentLocationBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(TencentLocationBean tencentLocationBean) {
                                            UtilX.LogX(tencentLocationBean.toString());
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
