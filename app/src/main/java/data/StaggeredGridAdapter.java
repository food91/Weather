package data;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.xiekun.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import control.UtilX;
import control.WeatherControl;
import control.WeatherGerHttp;


public class StaggeredGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private AdapterView.OnItemClickListener onItemClickListener;
    private ArrayList<WeaterData> weatherData;
    private int widthPixels=0;
    private final static int VIEW_ONE=101;
    private final static int VIEW_TWO=102;
    private final static int VIEW_THREE=103;
    private RecyclerView.ViewHolder holder;
    private int position;


    public void AddWeatherData(WeaterData weaterData){

        weatherData.add(weaterData);


    }


    @NonNull
    @Override
    public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=null;
        if(viewType==VIEW_TWO){
            view =LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.layout_staggere_goun_one_item,
                    parent,
                    false);
            return new ItemViewHolder_One(view);

        }else{
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.layout_staggere_grud_item,
                            parent,
                            false);
            return new ItemViewHolder_two(view);
        }

    }


    @Override
    public int getItemViewType(int position) {
         if(position%12!=0){
            return VIEW_ONE;
        }else {
             return VIEW_TWO;
         }
    }

    public StaggeredGridAdapter(Context mContext) {
        this.mContext = mContext;
        weatherData=new ArrayList<WeaterData>();
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        widthPixels = outMetrics.widthPixels;


    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int h= (int) (Math.random()%800+450);
        int w= (int) (Math.random()%widthPixels/2+300);
        int data= (int) (position+1)%30;
        ViewGroup.LayoutParams parm = holder.itemView.getLayoutParams();
        if(holder instanceof ItemViewHolder_One){
            w=widthPixels;
            ItemViewHolder_One itemViewHolder= (ItemViewHolder_One) holder;
            WeatherControl.GetImageViewHttpCacheStrategy(mContext,itemViewHolder.iv_weather,h,w,data);
            itemViewHolder.textView_city.setText(weatherData.get(position).getCity());
            int[] num=new int[3];
            num[0]= UtilX.CentigradeStringToInt(weatherData.get(position).getData().get(0).getTem());
            num[1]=UtilX.CentigradeStringToInt(weatherData.get(position).getData().get(0).getTem2());
            num[2]=UtilX.CentigradeStringToInt(weatherData.get(position).getData().get(0).getTem1());
            itemViewHolder.textView_centigrade_text.setText(UtilX.minint(num)+"--"+UtilX.maxint(num));
            itemViewHolder.textView_weathertip.setText(weatherData.get(position).getData().get(0).getAir_tips());

        }
        if(holder instanceof ItemViewHolder_two){
            ItemViewHolder_two itemViewHolder= (ItemViewHolder_two) holder;
            WeatherControl.GetImageViewHttpCacheStrategy(mContext,itemViewHolder.mImageView, h, w,data);
            int[] num=new int[3];
            num[0]= UtilX.CentigradeStringToInt(weatherData.get(position).getData().get(0).getTem());
            num[1]=UtilX.CentigradeStringToInt(weatherData.get(position).getData().get(0).getTem2());
            num[2]=UtilX.CentigradeStringToInt(weatherData.get(position).getData().get(0).getTem1());
            itemViewHolder.weatherTextView.setText(UtilX.minint(num)+"--"+UtilX.maxint(num));
            Logger.d("data=="+weatherData.get(position).getData().get(0).getAir_tips());
            itemViewHolder.ciytText.setText(weatherData.get(position).getCity());
            itemViewHolder.dataTextView.setText(weatherData.get(position).getData().get(0).getWea());
        }
    }



    @Override
    public int getItemCount() {
        int size=0;
        if(weatherData!= null) {
            size=weatherData.size();
        }
        return size;
    }


    class ItemViewHolder_two extends RecyclerView.ViewHolder{

        private TextView weatherTextView;

        @BindView(R.id.city_text_two)
         TextView ciytText;

        @BindView(R.id.wind_text)
         TextView dataTextView;
        private int data;
        ImageView mImageView = (ImageView) itemView.findViewById(R.id.iv);

        public ItemViewHolder_two(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            weatherTextView=itemView.findViewById(R.id.weatherday_textview);

            data=-1;
        }
    }

    class ItemViewHolder_One extends  RecyclerView.ViewHolder{

        @BindView(R.id.iv_weather)
        ImageView iv_weather;

        @BindView(R.id.tip_weather)
        TextView textView_weathertip;

        @BindView(R.id.city_text)
        TextView textView_city;

        @BindView(R.id.centigrade_text)
        TextView textView_centigrade_text;
        public ItemViewHolder_One(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
