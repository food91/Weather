package mInterface;

import java.util.Map;

import Entity.TencentLocationBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public class ApiUrl {

    //腾讯地图API
    public static String TencentApiUrl="https://apis.map.qq.com/";
    public static String TencentApiUrlpath="ws/geocoder/v1/";
    public static String TencentApiKey="ZLFBZ-3MK6S-I2ZOK-6IQKU-XL2B7-6VBOE";

    public interface IPLocation{
        @GET
        Observable<TencentLocationBean> getCity(@Url  String path,
                                                @QueryMap(encoded = true) Map<String,String> options);
    }

}
