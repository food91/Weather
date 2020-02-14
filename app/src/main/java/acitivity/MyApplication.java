package acitivity;

import android.app.Application;

import androidx.room.Room;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import AndroidDAO.CityRoomDatabase;
import control.UtilX;


public class MyApplication extends Application {

    private static MyApplication mApplication;

    public static synchronized MyApplication getApplicationInstance() {
        if (mApplication == null) {
            mApplication = new MyApplication();
        }
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.d("-----------init");
        UtilX.isDebug=true;
    }

}
