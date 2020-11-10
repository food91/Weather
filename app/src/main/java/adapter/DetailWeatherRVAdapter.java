package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiekun.myapplication.R;

import Entity.WeatherData;

public class DetailWeatherRVAdapter  extends RecyclerView.Adapter<DetailWeatherRVAdapter.detailWeatherRVAdapterVH> {

    private int num=23;

    public  static class detailWeatherRVAdapterVH extends RecyclerView.ViewHolder{

        public final TextView tv_hour,tv_temptrue,tv_lv,tv_wind;
        public final ImageView iv_weather;

        public detailWeatherRVAdapterVH(@NonNull View itemView) {

            super(itemView);
            tv_hour=itemView.findViewById(R.id.tv_rv_adapter_detailweather_hours);
            tv_lv=itemView.findViewById(R.id.tv_rv_adapter_detailweather_lv);
            tv_temptrue=itemView.findViewById(R.id.tv_rv_adapter_detailweather_temperature);
            tv_wind=itemView.findViewById(R.id.tv_rv_adapter_detailweather_wind);
            iv_weather=itemView.findViewById(R.id.iv_rv_adapter_detailweather_wea);

        }
    }

    private WeatherData weatherData;

    public DetailWeatherRVAdapter(WeatherData weatherData) {
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
                ,parent,false);
        return new detailWeatherRVAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull detailWeatherRVAdapterVH holder, int position) {

        if(weatherData!=null){

        }

    }

    @Override
    public int getItemCount() {
        return num;
    }
}
