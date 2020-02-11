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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import control.UtilX;
import control.WeatherControl;
import control.WeatherGerHttp;


/**
 * The type Staggered grid adapter.
 */
public class StaggeredGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<WeatherData> weatherData;
    private int widthPixels=0;
    private final static int VIEW_ONE=101;
    private final static int VIEW_TWO=102;
    private final static int VIEW_THREE=103;
    public final static int DIV_NUM=11;
    private RecyclerView.ViewHolder holder;
    private int position;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }


    /**
     * Add weather data.
     *
     * @param weaterData the weater data
     */
    public void AddWeatherData(WeatherData weaterData){

        weatherData.add(weaterData);


    }

    public WeatherData GetWeaterData(int position){
        return weatherData.get(position);
    }

    public void ClearData(){
        weatherData.clear();
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
         if(position%DIV_NUM!=0){
            return VIEW_ONE;
        }else {
             return VIEW_TWO;
         }
    }

    /**
     * Instantiates a new Staggered grid adapter.
     * 加载Context
     * 获取屏幕宽
     *
     * @param mContext the m context
     */
    public StaggeredGridAdapter(Context mContext) {

        this.mContext = mContext;
        weatherData=new ArrayList<WeatherData>();
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        widthPixels = outMetrics.widthPixels;
        Logger.d("StaggeredGridAdapter");

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        try {

        int h= 450;
        int w= 300;
        int data= (int) (position+1)%30;
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
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onClick(position);
                }
            }
        });
        }catch (Exception e){
            Logger.d(e.getMessage());
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


    /**
     * The type Item view holder two.
     */
    class ItemViewHolder_two extends RecyclerView.ViewHolder{

        private TextView weatherTextView;

        /**
         * The Ciyt text.
         */
        @BindView(R.id.city_text_two)
         TextView ciytText;

        /**
         * The Data text view.
         */
        @BindView(R.id.wind_text)
         TextView dataTextView;
        private int data;
        /**
         * The M image view.
         */
        ImageView mImageView = (ImageView) itemView.findViewById(R.id.iv);

        /**
         * Instantiates a new Item view holder two.
         *
         * @param itemView the item view
         */
        public ItemViewHolder_two(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            weatherTextView=itemView.findViewById(R.id.weatherday_textview);

            data=-1;
        }
    }

    /**
     * The type Item view holder one.
     */
    class ItemViewHolder_One extends  RecyclerView.ViewHolder{

        /**
         * The Iv weather.
         */
        @BindView(R.id.iv_weather)
        ImageView iv_weather;

        /**
         * The Text view weathertip.
         */
        @BindView(R.id.tip_weather)
        TextView textView_weathertip;

        /**
         * The Text view city.
         */
        @BindView(R.id.city_text)
        TextView textView_city;

        /**
         * The Text view centigrade text.
         */
        @BindView(R.id.centigrade_text)
        TextView textView_centigrade_text;

        /**
         * Instantiates a new Item view holder one.
         *
         * @param itemView the item view
         */
        public ItemViewHolder_One(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
