package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiekun.myapplication.R;

import java.util.logging.Logger;

import Entity.WeatherData;
import util.StringUtils;
import util.UtilX;

public class DetailWeatherRVAdapter  extends RecyclerView.Adapter<DetailWeatherRVAdapter.detailWeatherRVAdapterVH> {

    private int num=23;
    private Context mcontext;

    public  static class detailWeatherRVAdapterVH extends RecyclerView.ViewHolder{

        public final TextView tv_hour,tv_temperatrue,tv_lv,tv_wind;
        public final ImageView iv_weather;

        public detailWeatherRVAdapterVH(@NonNull View itemView) {

            super(itemView);
            tv_hour=itemView.findViewById(R.id.tv_rv_adapter_detailweather_hours);
            tv_lv=itemView.findViewById(R.id.tv_rv_adapter_detailweather_lv);
            tv_temperatrue=itemView.findViewById(R.id.tv_rv_adapter_detailweather_temperature);
            tv_wind=itemView.findViewById(R.id.tv_rv_adapter_detailweather_wind);
            iv_weather=itemView.findViewById(R.id.iv_rv_adapter_detailweather_wea);

        }
    }

    private WeatherData weatherData;

    public DetailWeatherRVAdapter(Context mcontext, WeatherData weatherData) {
        this.mcontext = mcontext;
        this.weatherData = weatherData;
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(WeatherData weatherData) {
        this.weatherData = weatherData;
    }


    @NonNull
    @Override
    public detailWeatherRVAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_adapter_detailweatjer_activity
                ,null,false);
        return new detailWeatherRVAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull detailWeatherRVAdapterVH holder, int position) {

        if(weatherData!=null){
           int numday=position/(weatherData.getData().size());
           int numposition=position%(weatherData.getData().get(numday)
           .getHours().size());
            UtilX.LogX("numday=+"+numday+" numposition="+numposition+" num="+num);
           //时间
            holder.tv_hour.setText(weatherData.getData().get(numday).getHours().get(numposition).getDay());
//            温度
            holder.tv_temperatrue.setText(weatherData.getData().get(numday).getHours().get(numposition).getTem());
           /*风力*/
            holder.tv_wind.setText(weatherData.getData().get(numday).getHours().get(numposition).getWin_speed());
         /*天气*/
            holder.tv_lv.setText(weatherData.getData().get(numday).getHours().get(numposition)
            .getWin());
            Bitmap bitmap=UtilX.getweatherBitmap(weatherData,0,mcontext);
            /*图标*/
            holder.iv_weather.setImageBitmap(bitmap);
        }

    }

    @Override
    public int getItemCount() {
        return num;
    }
}
