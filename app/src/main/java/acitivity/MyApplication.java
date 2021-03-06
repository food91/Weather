package acitivity;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.google.gson.Gson;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.mmkv.MMKV;
import com.xiekun.myapplication.R;

import java.lang.reflect.Type;

import Entity.SetActivityBean;
import Entity.UserData;
import Entity.UserEntity;
import adapter.Gloading;
import adapter.GlobalAdapter;
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
        Gloading.initDefault(new GlobalAdapter());
        String rootDir = MMKV.initialize(this);
    }

}
