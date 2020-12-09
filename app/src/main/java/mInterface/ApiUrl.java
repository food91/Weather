package mInterface;

import Entity.TencentLocationBean;
import io.reactivex.Observable;
import retrofit2.http.GET;

public class ApiUrl {

    public interface IPLocation{
        @GET("https://apis.map.qq.com/ws/geocoder/v1/?location=")
        Observable<TencentLocationBean> getCity();
    }

}
