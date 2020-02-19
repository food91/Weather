package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.xiekun.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import AndroidDAO.CityRoomDatabase;
import Entity.PopItemBean;
import Entity.UserData;
import Entity.UserEntity;
import Entity.WeatherData;
import acitivity.DetailWeatherActivity;
import control.WeatherControl;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import util.UtilX;

public class MycareRecyclerviewAdapter extends RecyclerView.Adapter implements WeatherBaseAdapter{


    private List<WeatherData> weatherDataList;
    private Context mContext;
    private int screen_w;
    private List<String> strtext_view_onlongclick;
    private List<Integer> intview_view_onlongclick;
    private List<PopItemBean> popItemBean;
    private     QMUIPopup mNormalPopup;
    public MycareRecyclerviewAdapter(Context mcontext) {
        this.mContext = mcontext;
        screen_w=QMUIDisplayHelper.getScreenWidth(mcontext);
        weatherDataList=new ArrayList<WeatherData>();
        init();
    }

    private void init(){
        strtext_view_onlongclick=new ArrayList<String>();
        strtext_view_onlongclick.add(mContext.getResources().getString(R.string.view_pop_favior_cancel));
        strtext_view_onlongclick.add(mContext.getResources().getString(R.string.view_pop_share));
        strtext_view_onlongclick.add(mContext.getResources().getString(R.string.view_pop_remark));
        strtext_view_onlongclick.add(mContext.getResources().getString(R.string.view_pop_about));

        intview_view_onlongclick=new ArrayList<Integer>();
        intview_view_onlongclick.add(R.drawable.ic_favorite_border_black_24dp);
        intview_view_onlongclick.add(R.drawable.ic_view_share);
        intview_view_onlongclick.add(R.drawable.ic_remarks);
        intview_view_onlongclick.add(R.drawable.ic_about);
        popItemBean=new ArrayList<PopItemBean>();
        for(int i=0;i<strtext_view_onlongclick.size();i++){
            popItemBean.add(new PopItemBean(intview_view_onlongclick.get(i),
                    strtext_view_onlongclick.get(i)));
        }
        Logger.d("init------------------");
    }


    public void setUserEntityList(List<WeatherData> list){
        weatherDataList=list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.favourite_cardview,
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
            WeatherControl.GetImageViewHttpCacheStrategy(mContext,favourite_cardview.iv_city,data);
            favourite_cardview.tv_city.setText(weatherDataList.get(position).getCity());
            favourite_cardview.tv_tip.setText(weatherDataList.get(position).getData().get(0).getAir_tips());
            Logger.d("centigred---"+UtilX.CentigradeStringToInt(
                    weatherDataList.get(position).getData().get(0).getTem()));
            int[] num=new int[3];
            num[0]= UtilX.CentigradeStringToInt(weatherDataList.get(position).getData().get(0).getTem());
            num[1]=UtilX.CentigradeStringToInt(weatherDataList.get(position).getData().get(0).getTem2());
            num[2]=UtilX.CentigradeStringToInt(weatherDataList.get(position).getData().get(0).getTem1());
            favourite_cardview.centigrade.setText(
                    UtilX.minint(num)+"--"+UtilX.maxint(num)
                    );
            String waes=weatherDataList.get(position).getData().get(0).getWea_img();
            WeatherControl.LoadLocalWeatherIcon(mContext,favourite_cardview.iv_wea,waes);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                   initPopQMUI(position,v);

                    return false;
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, DetailWeatherActivity.class);
                    WeatherData tweatherData=weatherDataList.get(position);
                    intent.putExtra(WeatherData.DATANAME,tweatherData);
                    mContext.startActivity(intent);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void care(int i){
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                UserEntity userEntity= CityRoomDatabase.getDatabase(mContext).
                        wordDao().getUser(UserData.
                        getUserData().getName());
                Boolean IsNoExistCity=userEntity.RemoveCityfavorite(weatherDataList.get(i).getCity());
                if(IsNoExistCity){
                    weatherDataList.remove(i);
                    CityRoomDatabase.getDatabase(mContext).wordDao().updateUsers(userEntity);
                }
                emitter.onNext(IsNoExistCity);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if(aBoolean){
                            notifyDataSetChanged();
                            Toast.makeText(mContext,
                                    mContext.getResources().getString(R.string.view_pop_faviored_cancel),
                                    Toast.LENGTH_LONG).show();
                            notifyDataSetChanged();
                        }else{
                            Toast.makeText(mContext,
                                    mContext.getResources().getString(R.string.view_pop_favior_fail_cancel_fail),
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                        Toast.makeText(mContext,
                                mContext.getResources().getString(R.string.view_pop_favior_fail),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    private void initPopQMUI(int p,View v){
        WeatherViewClcikLong weatherViewClcikLong=new WeatherViewClcikLong(popItemBean,mContext);

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Logger.d("i==="+i);
                if(i==0){
                    care(p);
                }
                if (mNormalPopup != null) {
                    mNormalPopup.dismiss();
                }
            }
        };
         mNormalPopup = QMUIPopups.listPopup(mContext,
                QMUIDisplayHelper.dp2px(mContext, 120),
                QMUIDisplayHelper.dp2px(mContext, 160)
                ,weatherViewClcikLong
                ,onItemClickListener)
                .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
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


        ImageView iv_city;


        TextView tv_city;


        TextView centigrade;


        TextView tv_tip;
        ImageView iv_wea;


        public Favourite_cardview(@NonNull View itemView) {
            super(itemView);
            iv_city=itemView.findViewById(R.id.iv_careweather_adapter);
            tv_city=itemView.findViewById(R.id.citycare_text_adapter);
            centigrade=itemView.findViewById(R.id.centigradecare_text_adapter);
            tv_tip=itemView.findViewById(R.id.tipcare_weather_adapter);
            iv_wea=itemView.findViewById(R.id.iv_wind_adapter);
        }
    }
}
