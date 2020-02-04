package control;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.xiekun.myapplication.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import data.CityBean;
import data.StaggeredGridAdapter;
import data.WeaterData;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static javax.xml.transform.OutputKeys.ENCODING;


public class WeatherControl {
    private StaggeredGridAdapter staggeredGridAdapter;
    private WeatherGerHttp weatherGerHttp;
    private List<CityBean> cityId=new ArrayList<>() ;

    public WeatherControl(StaggeredGridAdapter staggeredGridAdapter,Context context) throws IOException {
        this.staggeredGridAdapter = staggeredGridAdapter;
        weatherGerHttp=new WeatherGerHttp();
        GetAllCity(context);
    }


    private void GetAllCity(Context context) throws IOException {
        StringBuffer sb = new StringBuffer();
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String str = "";
        Resources resources = context.getResources();
        try {
            is = resources.openRawResource(R.raw.cityid); // 读取相应的章节
            isr = new InputStreamReader(is, "UTF-8");// 这里添加了UTF-8，解决乱码问题
            br = new BufferedReader(isr);
            while ((str = br.readLine()) != null) {
                sb.append(str);
                sb.append('\n');
            }
            br.close();
            isr.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson=new Gson();
        cityId=gson.fromJson(sb.toString(),new TypeToken<List<CityBean>>(){}.getType());
    }

    public  void GetCityInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int num= (int) (Math.random()%100)+50;
                int i=0;
                while (i<cityId.size()){
                    UpdataWeatherViewFromHttp(i);
                    i+=num;
                }
            }
        }).start();
    }

    public static void GetImageViewHttpCacheStrategy(final Context context, final ImageView imageView, final int h, final int w,int rondom){

        String connect;
        if(rondom<10){
            connect="0"+String.valueOf(rondom);
        }else{
            connect=String.valueOf(rondom);
        }
        String ImageView="http://cdn.mrabit.com/1920.2020-01-"+connect+".jpg";
        Logger.d("iv=="+ImageView);
        Glide.with(context)
                        .load(ImageView)
                        .placeholder(R.mipmap.loadgif)//图片加载出来前，显示的图片
                        .error(R.mipmap.fillload)//图片加载失败后，显示的图片
                        .override(w, h)
                        .fitCenter()
                        .into(imageView);

    }





    private void UpdataWeatherViewFromHttp(int num){
       Observable call=WeatherGerHttp.GetHttpData(cityId.get(num).getName());
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeaterData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeaterData weaterData) {
                        Logger.d("onNext=="+weaterData.toString());
                        staggeredGridAdapter.AddWeatherData(weaterData);
                        staggeredGridAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
