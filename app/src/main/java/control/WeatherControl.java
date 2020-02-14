package control;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.xiekun.myapplication.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import acitivity.DetailWeatherActivity;
import adapter.StaggeredGridAdapter;
import data.CityBean;
import data.WeatherData;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * The type Weather control.
 */
public class WeatherControl {

    private StaggeredGridAdapter staggeredGridAdapter;
    private WeatherGerHttp weatherGerHttp;
    private List<CityBean> cityId=new ArrayList<>() ;
    /**
     * The Swipe refresh layout.
     */
    SwipeRefreshLayout swipeRefreshLayout;
    private WeatherData weatherData;
    private int[] cityp;
    /**
     * The constant CITYNUM.
     */
    public static final String CITYNUM="citynum";


    /**
     * Instantiates a new Weather control.
     *
     * @param staggeredGridAdapter the staggered grid adapter
     * @param context              the context
     * @throws IOException the io exception
     */
    public WeatherControl(StaggeredGridAdapter staggeredGridAdapter,Context context) throws IOException {
        this.staggeredGridAdapter = staggeredGridAdapter;
        weatherGerHttp=new WeatherGerHttp();
        GetAllCity(context);
    }





    /**
     * Set oncli adapter.单击跳转到详细界面
     *
     * @param context the context
     */
    public void setOncliAdapter(Context context){
        staggeredGridAdapter.setOnItemClickListener(new StaggeredGridAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(context, DetailWeatherActivity.class);
                WeatherData tweatherData=staggeredGridAdapter.GetWeaterData(position);
                intent.putExtra(WeatherData.DATANAME,tweatherData);
                intent.putExtra(WeatherControl.CITYNUM,cityp[position]);
                context.startActivity(intent);

            }
        });
    }


    /**
     * Setweather control.
     *
     * @param swipeRefreshLayout the swipe refresh layout
     */
    public void SetweatherControl(SwipeRefreshLayout swipeRefreshLayout){
        this.swipeRefreshLayout=swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               GetCityInfo();
                //这里获取数据的逻辑

            }
        });
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

    /**
     * Get city info.
     */
    public  void GetCityInfo(){
        staggeredGridAdapter.ClearData();
        cityp= new int[100];
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,5,1, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(50));
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int num= 0;
                int i=0,t=0;
                while (i<cityId.size()){
                    num= Math.abs(new Random().nextInt()%50);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    UpdataWeatherViewFromHttp(i);
                    cityp[t]=i;
                    t++;
                    i+=num;
                }
            }
        });
    }


    /**
     * Get image view http cache strategy.
     *
     * @param context   the context
     * @param imageView the image view
     * @param h         the h
     * @param w         the w
     * @param rondom    the rondom
     */
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
                        .apply(new RequestOptions()
                        .placeholder(R.mipmap.loadgif)//图片加载出来前，显示的图片
                        .error(R.mipmap.fillload))//图片加载失败后，显示的图片
                         .override(w,h)
                        .fitCenter()
                        .into(imageView);



    }





    private void UpdataWeatherViewFromHttp(int num){
        Logger.d("num===="+num);
        Observable call=WeatherGerHttp.GetHttpData(cityId.get(num).getName());
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeatherData weaterData) {
                        if(weaterData.getCity()==null){
                            Logger.d("城市名空=="+cityId.get(num).getName());
                            return;
                        }
                        Logger.d("onNext=="+weaterData.toString());
                        staggeredGridAdapter.AddWeatherData(weaterData);
                        staggeredGridAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        weatherData=weaterData;
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
