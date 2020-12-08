package acitivity;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import control.TaskNotificationManager;
import service.TaskManageNotificationService;
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

    }

}
