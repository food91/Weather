package acitivity;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.xiekun.myapplication.R;

import control.TaskNotificationManager;
import service.TaskManageNotificationService;
import util.Constant;
import util.UtilX;


public class MyApplication extends Application {

    private static MyApplication mApplication=null;

    public static  MyApplication getApplicationInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        mApplication=this;
    }

    private void init(){
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.d("-----------init");
        UtilX.isDebug=true;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(Constant.CHANNEL_1, getString(R.string.app_name), NotificationManager.IMPORTANCE_LOW);
            mChannel.setDescription("notication channel");
            mChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
            notificationManager .createNotificationChannel(mChannel);
        }
    }

}
