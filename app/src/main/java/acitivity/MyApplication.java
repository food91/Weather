package acitivity;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;



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
    }

}
