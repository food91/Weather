package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.xiekun.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import Entity.WeatherData;
import butterknife.BindView;
import butterknife.ButterKnife;
import control.WeatherControl;

public class MycareRecyclerviewAdapter extends RecyclerView.Adapter implements WeatherBaseAdapter{


    private List<WeatherData> weatherDataList;
    private Context mContext;
    private int screen_w;

    public MycareRecyclerviewAdapter(Context mcontext) {
        this.mContext = mcontext;
        screen_w=QMUIDisplayHelper.getScreenWidth(mcontext);
        weatherDataList=new ArrayList<WeatherData>();
    }

    public void setUserEntityList(List<WeatherData> list){
        weatherDataList=list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_staggere_goun_one_item,
                        parent,
                        false);
        return new Favourite_cardview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        try{
            int data= (int) (position+1)%30;
            Favourite_cardview favourite_cardview= (Favourite_cardview) holder;
            //city picture
            WeatherControl.GetImageViewHttpCacheStrategy(mContext,favourite_cardview.iv_city,450,screen_w,data);

            favourite_cardview.tv_city.setText(weatherDataList.get(position).getCity());
            favourite_cardview.tv_tip.setText(weatherDataList.get(position).getData().get(0).getAir_tips());


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        return weatherDataList.size();
    }

    @Override
    public void AddWeatherData(WeatherData weatherData) {
        weatherDataList.add(weatherData);
    }

    @Override
    public void updateView() {
        notifyDataSetChanged();
    }

    class Favourite_cardview extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_careweather)
        ImageView iv_city;

        @BindView(R.id.citycare_text)
        TextView tv_city;

        @BindView(R.id.centigradecare_text)
        TextView centigrade;

        @BindView(R.id.tipcare_weather)
        TextView tv_tip;


        public Favourite_cardview(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
