package fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.xiekun.myapplication.R;

import Entity.WeatherData;
import acitivity.DetailWeatherActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import control.RequestRetrofit;
import control.WeatherControl;
import data.SearchViewModel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class SearchFragment extends Fragment {


    @BindView(R.id.intv_search)
    TextInputEditText intvSearch;
    @BindView(R.id.fragment_btn_search)
    QMUIRoundButton fragmentBtnSearch;

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
               Observable observable= RequestRetrofit.GetHttpData(city, (long) 1.5);
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

    ProgressDialog progressBar;

    private void stopgif(){

     progressBar.dismiss();

    }

    private void loadinggif(){
        progressBar= ProgressDialog.show(getContext(), "提示", "正在搜索中");
        RequestOptions options = new RequestOptions()
                .centerCrop()
                //.placeholder(R.mipmap.ic_launcher_round)
                .error(android.R.drawable.stat_notify_error)
                .priority(Priority.HIGH)
                //.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

  
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}