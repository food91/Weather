package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import Entity.UserEntity;
import adapter.MycareRecyclerviewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import control.WeatherControl;
import data.MycareViewModel;


public class MycareFragment extends Fragment {

    @BindView(R.id.recyclerview_care)
    RecyclerView recyclerviewCare;
    private MycareViewModel mycareViewModel;
    private Unbinder unbinder;
    private MycareRecyclerviewAdapter mycareRecyclerviewAdapter;
    private WeatherControl weatherControl;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mycareViewModel =
                ViewModelProviders.of(this).get(MycareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mycare, container, false);
        unbinder = ButterKnife.bind(this, root);
        mycareRecyclerviewAdapter = new MycareRecyclerviewAdapter(getContext());
        recyclerviewCare.setLayoutManager(new LinearLayoutManager(getContext()));//这里用线性显示 类似于listview
        recyclerviewCare.setLayoutManager(new GridLayoutManager(getContext(), 1));//这里用线性宫格显示 类似于gridview
        recyclerviewCare.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
        recyclerviewCare.setAdapter(mycareRecyclerviewAdapter);
        try {
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
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                weatherControl.UpdataWeatherViewFromHttp(userEntity.favoritecity.get(i)
                                        , mycareRecyclerviewAdapter);
                            }
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}