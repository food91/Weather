package fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.textfield.TextInputEditText;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.xiekun.myapplication.R;

import Entity.WeatherData;
import acitivity.DetailWeatherActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import control.WeatherControl;
import control.WeatherGerHttp;
import data.SearchViewModel;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class SearchFragment extends Fragment {


    @BindView(R.id.intv_search)
    TextInputEditText intvSearch;
    @BindView(R.id.fragment_btn_search)
    QMUIRoundButton fragmentBtnSearch;

    @BindView(R.id.iv_gif_loading)
    ImageView imageView_gif;
    private Unbinder unbinder;
    private SearchViewModel searchViewModel;
    private WeatherControl weatherControl;
    private ConstraintLayout constraintLayout;


    //获取随机图片
    private static final String url="http://img.xjh.me/random_img.php?type=bg&ctype=nature&return=302&device=mobile";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        constraintLayout=root.findViewById(R.id.searc_back_Constraintlayout);
        unbinder = ButterKnife.bind(this, root);
        weatherControl=new WeatherControl();
        fragmentBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loadinggif();
               String city=intvSearch.getText().toString()+"";
               Observable observable= WeatherGerHttp.GetHttpData(city, (long) 1.5);
               observable.subscribeOn(Schedulers.io()).
                       observeOn(AndroidSchedulers.mainThread())
                       .subscribe(new Observer<WeatherData>() {

                           @Override
                           public void onSubscribe(Disposable d) {

                           }

                           @Override
                           public void onNext(WeatherData weatherData) {
                               stopgif();
                               if(weatherData.getCity()==null){
                                   return;
                               }
                               Intent intent=new Intent(getContext(), DetailWeatherActivity.class);
                               intent.putExtra(WeatherData.DATANAME,weatherData);
                               getContext().startActivity(intent);
                           }

                           @Override
                           public void onError(Throwable e) {
                               stopgif();
                               Toast.makeText(getContext(),
                                       "未搜索到城市名",Toast.LENGTH_LONG).show();
                           }

                           @Override
                           public void onComplete() {
                               stopgif();
                           }
                       });
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void stopgif(){
        fragmentBtnSearch.setVisibility(View.VISIBLE);
        intvSearch.setVisibility(View.VISIBLE);
        imageView_gif.setVisibility(View.GONE);
    }

    private void loadinggif(){
        fragmentBtnSearch.setVisibility(View.GONE);
        intvSearch.setVisibility(View.GONE);
        imageView_gif.setVisibility(View.VISIBLE);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                //.placeholder(R.mipmap.ic_launcher_round)
                .error(android.R.drawable.stat_notify_error)
                .priority(Priority.HIGH)
                //.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

        Glide.with(this)
                .load(R.drawable.loadinggif)
                .apply(options)
                //.thumbnail(Glide.with(this).load(R.mipmap.ic_launcher))
                .into(imageView_gif);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}