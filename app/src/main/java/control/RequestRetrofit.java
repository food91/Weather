package control;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;

import java.util.HashMap;
import java.util.Map;

import Entity.TencentLocationBean;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import mInterface.ApiUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestRetrofit {

    public static RequestRetrofit instance=null;
    public static boolean HttpLog=false;
    public static synchronized RequestRetrofit getInstance(){
        if(instance==null){
            instance=new RequestRetrofit();
        }
        return instance;
    }

    public static Retrofit createRetrofit(String baseurl){

        //拦截器获取日志
         if(HttpLog){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("RetrofitLog","retrofitBack = "+message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

         }
        Retrofit.Builder retrofitBuilder=new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
                if(HttpLog){
                    OkHttpClient client = new OkHttpClient.Builder()//okhttp设置部分，此处还可再设置网络参数
                            .addInterceptor(loggingInterceptor)
                            .build();
                    retrofitBuilder.client(client)
                }
         return  retrofitBuilder .build();
    }

    public static void sendNetMessage(Map<String,String> map, Retrofit retrofit
    ,Observer<Tz> observer){
        Observable<TencentLocationBean> observable=retrofit.create(
                ApiUrl.IPLocation.class).
                getCity(ApiUrl.TencentApiUrlpath,map
                );
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer<T>);
    }


    private void getLocation(Observer<TencentLocationBean> observer,Context context){
        TencentLocationManager mLocationManager = TencentLocationManager.getInstance(context);
        mLocationManager.requestSingleFreshLocation(null,
                new TencentLocationListener() {
                    @Override
                    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {

                        Retrofit retrofit=RequestRetrofit.createRetrofit(ApiUrl.TencentApiUrl);
                        Map<String,String> map=new HashMap<>();
                        map.put("location",tencentLocation.getLatitude()+","+tencentLocation.getLongitude());
                        map.put("key",ApiUrl.TencentApiKey);
                        sendNetMessage(map, retrofit, new Observer<TencentLocationBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(TencentLocationBean tencentLocationBean) {

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

}
