package service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.GET;
import util.UtilX;

public class TaskManageNotificationService extends Service {


    public static  final String httpbaidu="http://open.baidu.com/";
    public interface TimeAPI{
        @GET("special/time/")
        Observable<String> getTime();
    }

    public static void getTime(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(httpbaidu)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
                .build())
                .build();
        TimeAPI Get_TimeAPI=retrofit.create(TimeAPI.class);

        Get_TimeAPI.getTime().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        UtilX.LogX("s="+s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });



    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
