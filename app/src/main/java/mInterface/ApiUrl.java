package mInterface;

import java.util.Map;

import Entity.TencentLocationBean;
import Entity.WeatherData;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public class ApiUrl {



    public interface IPLocation{
        @GET
        Observable<TencentLocationBean> getCity(@Url  String path,
                                                @QueryMap(encoded = true) Map<String,String> options);
    }


    /**
     * 获取7日天气接口
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

}
