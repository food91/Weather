package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orhanobut.logger.Logger;
import com.xiekun.myapplication.R;

import java.io.IOException;
import java.util.Objects;

import adapter.StaggeredGridAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import control.WeatherControl;

public class WeatherFragment extends Fragment {

    @BindView(R.id.swipe_refresh_layout)
     SwipeRefreshLayout swipeRefreshLayout;
    private Unbinder unbinder;
    @BindView(R.id.weather_recyclerview)
     RecyclerView recyclerView;
     WeatherControl weatherControl;
     StaggeredGridAdapter staggeredGridAdapter;
     GridLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Logger.d("onCreateView");
        View root = inflater.inflate(R.layout.fragment_weather, container, false);
        unbinder = ButterKnife.bind(this, root);
        staggeredGridAdapter=new StaggeredGridAdapter(Objects.requireNonNull(getActivity()));
        recyclerView.setAdapter(staggeredGridAdapter);
        try {
            mLayoutManager = new GridLayoutManager(Objects.requireNonNull(getActivity()), 2);
            mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(position%StaggeredGridAdapter.DIV_NUM!=0){
                        return 1;
                    }else {
                        return 2;
                    }
                }
            });
            recyclerView.setLayoutManager(mLayoutManager);
            weatherControl=new WeatherControl(staggeredGridAdapter,Objects.requireNonNull(getActivity()));
            weatherControl.GetCityInfo();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.d("-----------------"+e.getMessage());
        }
        weatherControl.SetweatherControl(swipeRefreshLayout);
        weatherControl.setOncliAdapter(getContext());
        return root;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
