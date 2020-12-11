package control;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import Entity.TencentLocationBean;
import Entity.WeatherData;
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
import util.Constant;

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

        Retrofit.Builder retrofitBuilder=new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
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
             OkHttpClient client = new OkHttpClient.Builder()//okhttp设置部分，此处还可再设置网络参数
                     .addInterceptor(loggingInterceptor)
                     .build();
             retrofitBuilder.client(client);
         }
         return  retrofitBuilder .build();
    }

    public static void ReviceTencentCity(){

    }

    public static void sendNetMessage(Map<String,String> map, Retrofit retrofit
    , Observer<Object> observer){

        Observable<TencentLocationBean> observable=retrofit.create(
                ApiUrl.IPLocation.class).
                getCity(Constant.TencentApiUrlpath,map
                );

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void ReviceLocationMessage(TencentLocation tencentLocation
    ,Observer<Object> observer){
        Retrofit retrofit=RequestRetrofit.createRetrofit(Constant.TencentApiUrl);
        Map<String,String> map=new HashMap<>();
        map.put("location",tencentLocation.getLatitude()+","+tencentLocation.getLongitude());
        map.put("key",Constant.TencentApiKey);
        sendNetMessage(map,retrofit,observer);
    }

    public static void getLocation( Context context,
                                    TencentLocationListener tencentLocationListener
                            ){
        TencentLocationManager mLocationManager = TencentLocationManager.getInstance(context);
        mLocationManager.requestSingleFreshLocation(null,
                tencentLocationListener, Looper.getMainLooper());
    }

    public static Observable<WeatherData> GetHttpData(String cityname){

        Retrofit retrofit =RequestRetrofit.createRetrofit(Constant.WEATHERURL);
        ApiUrl.GetRuest_Interface request = retrofit.create(ApiUrl.GetRuest_Interface.class);
        Observable<WeatherData> call = request.get(Constant.WEATHERID,
                Constant.WEATHERPASSWORD,
                Constant.VERSION,cityname);

        return call;
    }

    /**
     * Get http data.
     * 请求7天天气信息
     * @return
     */
    public static Observable<WeatherData> GetHttpData(String cityname,Long time){
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


        Retrofit retrofit = RequestRetrofit.createRetrofit(Constant.WEATHERURL);
        ApiUrl.GetRuest_Interface request = retrofit.create(ApiUrl.GetRuest_Interface.class);
        Observable<WeatherData> call = request.get(Constant.WEATHERID,
                Constant.WEATHERPASSWORD,
                Constant.VERSION,cityname)
                .delay(time, TimeUnit.SECONDS);
        return call;
    }


}
