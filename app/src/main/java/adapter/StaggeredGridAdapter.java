package adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.popup.QMUIQuickAction;
import com.xiekun.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import control.UtilX;
import control.WeatherControl;
import data.WeatherData;


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

    private List<String> strtext_view_onlongclick;
    private List<Integer> intview_view_onlongclick;

    private void init(){
       strtext_view_onlongclick=new ArrayList<String>();
       strtext_view_onlongclick.add(mContext.getResources().getString(R.string.view_pop_favior));
       strtext_view_onlongclick.add(mContext.getResources().getString(R.string.view_pop_share));
       strtext_view_onlongclick.add(mContext.getResources().getString(R.string.view_pop_remark));
       strtext_view_onlongclick.add(mContext.getResources().getString(R.string.view_pop_about));

        intview_view_onlongclick=new ArrayList<Integer>();
        intview_view_onlongclick.add(R.drawable.ic_favorite_border_black_24dp);
        intview_view_onlongclick.add(R.drawable.ic_view_share);
        intview_view_onlongclick.add(R.drawable.ic_remarks);
        intview_view_onlongclick.add(R.drawable.ic_about);
    }


    public interface OnItemClickListener{
        void onClick(int position);
    }

    public interface OnItemLongClickListenerX{
        void onLongClick(int position);
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

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
        init();
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
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                QMUIPopup  mNormalPopup = QMUIPopups.popup(mContext, QMUIDisplayHelper.dp2px(mContext, 120),
                        QMUIDisplayHelper.dp2px(mContext,160))
                        .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                        .view(R.layout.view_onclicklong_weather)
                        .edgeProtection(QMUIDisplayHelper.dp2px(mContext, 20))
                        .offsetX(QMUIDisplayHelper.dp2px(mContext, 20))
                        .offsetYIfBottom(QMUIDisplayHelper.dp2px(mContext, 5))
                        .shadow(true)
                        .arrow(true)
                        .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                        .onDismiss(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {

                            }
                        })
                        .show(v);
                return true;
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
