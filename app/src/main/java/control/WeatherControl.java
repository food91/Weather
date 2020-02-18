package control;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
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

import Entity.CityBean;
import Entity.WeatherData;
import acitivity.DetailWeatherActivity;
import adapter.StaggeredGridAdapter;
import adapter.WeatherBaseAdapter;
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
    private WeatherBaseAdapter mweatherBaseAdapter;

    private static final String[] WEA_IMG={"xue","lei","shachen","wu","bingbao","yun","yu","yin","qing"};
    private static final int[] WEA_IMG_ID={R.mipmap.xue,R.mipmap.lei,R.mipmap.shachen,
    R.mipmap.wu,R.mipmap.mai,R.mipmap.yun,R.mipmap.yu,R.mipmap.yin,R.mipmap.qing};
    //存储所有城市的名
    private List<CityBean> cityId=new ArrayList<>() ;
    /**
     * The Swipe refresh layout.
     */
    SwipeRefreshLayout swipeRefreshLayout=null;

    //存储所有显示在VIEW上的城市在CITYID的下标
    //比如第一个VIEW城市是  Cityid[cityp[0]]
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
        this.staggeredGridAdapter = (StaggeredGridAdapter) staggeredGridAdapter;
        weatherGerHttp=new WeatherGerHttp();
        GetAllCity(context);
    }

    public WeatherControl() {
    }

    public WeatherControl buildadapter(WeatherBaseAdapter weatherBaseAdapter){
        this.mweatherBaseAdapter=weatherBaseAdapter;
        return this;
    }

    public static void LoadLocalWeatherIcon(Context context,ImageView iv,String wea){
        for(int i=0;i<WEA_IMG.length;i++){
            if(wea.equals(WEA_IMG[i])){
                Glide.with(context)
                        .load(WEA_IMG_ID[i])
                        .into(iv);
                return;
            }

        }

    }



    /**
     * Instantiates a new Weather control.
     *
     * @param context the context
     * @throws IOException the io exception
     */
    public WeatherControl(Context context) throws IOException {
        weatherGerHttp=new WeatherGerHttp();
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
               GetCityInfo_Weathe();
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

    Runnable GetAllweater_runnable=new Runnable() {
        @Override
        public void run() {
            int num= 0;
            int i=0,t=0;
            while (i<cityId.size()){
                //获得随机数字
                num= Math.abs(new Random().nextInt()%50);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //数字映射到城市数组中，访问随机城市
                UpdataWeatherViewFromHttp(cityId.get(i).getName(),staggeredGridAdapter);
                //将随机数字储存
                cityp[t]=i;
                t++;
                i+=num;
            }
        }
    };

    public void GetCityInfo_Weathe(){
        staggeredGridAdapter.ClearData();
        cityp= new int[100];
        GetCityInfor(GetAllweater_runnable);
    }
    ThreadPoolExecutor threadPoolExecutor;
    /**
     * Get city info.
     */
    public  void GetCityInfor(Runnable runnable){

        threadPoolExecutor = new ThreadPoolExecutor(4,5,1, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(50));
        //设置线程池回收
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        threadPoolExecutor.execute(runnable);
    }

    public void stopThreadPool(){
        try {
            threadPoolExecutor.shutdownNow();
            threadPoolExecutor=null;
        }catch (Exception e){

        }

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


    /**
     * Updata weather view from http.
     *
     * @param cityname the cityname
     * @param adapter  the adapter
     */
    public void UpdataWeatherViewFromHttp(String cityname, WeatherBaseAdapter adapter){

        Observable call=WeatherGerHttp.GetHttpData(cityname);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeatherData weaterData) {
                        if(weaterData.getCity()==null){

                            return;
                        }
                        Logger.d("onNext=="+weaterData.toString());
                        adapter.AddWeatherData(weaterData);
                        adapter.updateView();
                        if(swipeRefreshLayout!=null){
                            swipeRefreshLayout.setRefreshing(false);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if(swipeRefreshLayout!=null){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }



}
