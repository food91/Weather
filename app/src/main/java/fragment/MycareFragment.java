package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.orhanobut.logger.Logger;
import com.xiekun.myapplication.R;

import Entity.MycareViewModel;
import Entity.UserEntity;
import adapter.MycareRecyclerviewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import control.WeatherControl;


public class MycareFragment extends Fragment {

    @BindView(R.id.recyclerview_care)
    RecyclerView recyclerviewCare;
    private MycareViewModel mycareViewModel;
    private Unbinder unbinder;
    private MycareRecyclerviewAdapter mycareRecyclerviewAdapter;
    private WeatherControl weatherControl;
    private Toolbar toolbar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mycare, container, false);
        unbinder = ButterKnife.bind(this, root);
        update();
        return root;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            mycareViewModel.update();
        }
    }

    public void update(){
        try {
            mycareRecyclerviewAdapter = new MycareRecyclerviewAdapter(getContext());
            recyclerviewCare.setLayoutManager(new LinearLayoutManager(getContext()));//这里用线性显示 类似于listview
            recyclerviewCare.setLayoutManager(new GridLayoutManager(getContext(), 1));//这里用线性宫格显示 类似于gridview
            recyclerviewCare.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
            recyclerviewCare.setAdapter(mycareRecyclerviewAdapter);
            mycareViewModel =
                    ViewModelProviders.of(this).get(MycareViewModel.class);
            weatherControl = new WeatherControl().buildadapter(mycareRecyclerviewAdapter);
            mycareViewModel.getIdEntity().observe(this, new Observer<UserEntity>() {
                @Override
                public void onChanged(UserEntity userEntity) {
                    Logger.d("on Changed---" + getClass().getName());
                    weatherControl.GetCityInfor(new Runnable() {
                        @Override
                        public void run() {
                            if (userEntity == null || userEntity.favoritecity == null || userEntity.favoritecity.size() == 0)
                                return;
                            for (int i = 0; i < userEntity.favoritecity.size(); i++) {
                                weatherControl.UpdataWeatherViewFromHttp(userEntity.favoritecity.get(i)
                                        , mycareRecyclerviewAdapter);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}