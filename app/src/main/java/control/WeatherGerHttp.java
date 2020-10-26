package control;





import android.util.Log;


import java.util.concurrent.TimeUnit;

import Entity.WeatherData;
import io.reactivex.Observable;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * The type Weather ger http.
 */
public class WeatherGerHttp {

    /**
     * The constant WEATHERURL.
     */
    public final static String WEATHERURL="http://tianqiapi.com/";
    /**
     * The constant WEATHERID.
     */
    public final static String WEATHERID="87699471";
    /**
     * The constant WEATHERPASSWORD.
     */
    public final static String WEATHERPASSWORD="oSys9sgp";
    /**
     * The constant VERSION.
     */
    public final static String VERSION="v1";

    ;

    /**
     * The interface Get ruest interface.
     */
    public  interface GetRuest_Interface{
        /**
         * Get call.
         *
         * @param appid     the appid
         * @param appsecret the appsecret
         * @param version   the version
         * @return the call
         */
        @GET("api")
        Observable<WeatherData> get(@Query("appid") String appid,
                                   @Query("appsecret") String appsecret,
                                   @Query("version") String version,
                                   @Query("city") String city
                                  );
    }
    public static Observable<WeatherData> GetHttpData(String cityname){
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


        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求的 Base Url地址
                .baseUrl(WEATHERURL)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        GetRuest_Interface request = retrofit.create(GetRuest_Interface.class);
        Observable<WeatherData> call = request.get(WEATHERID,WEATHERPASSWORD,VERSION,cityname);

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


        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求的 Base Url地址
                .baseUrl(WEATHERURL)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        GetRuest_Interface request = retrofit.create(GetRuest_Interface.class);
        Observable<WeatherData> call = request.get(WEATHERID,WEATHERPASSWORD,VERSION,cityname)
                .delay(time,TimeUnit.SECONDS);
        return call;
    }

}
