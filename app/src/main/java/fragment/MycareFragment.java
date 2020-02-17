package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.xiekun.myapplication.R;

import Entity.UserEntity;
import adapter.MycareRecyclerviewAdapter;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import control.WeatherControl;
import data.MycareViewModel;


public class MycareFragment extends Fragment {

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
        mycareRecyclerviewAdapter=new MycareRecyclerviewAdapter(getContext());
        try {
            weatherControl=new WeatherControl().buildadapter(mycareRecyclerviewAdapter);
            mycareViewModel.getIdEntity().observe(this, new Observer<UserEntity>() {
                @Override
                public void onChanged(UserEntity userEntity) {
                    weatherControl.GetCityInfor(new Runnable() {
                        @Override
                        public void run() {
                            if(userEntity==null||userEntity.favoritecity==null||userEntity.favoritecity.size()==0)
                                return;
                            for(int i=0;i<userEntity.favoritecity.size();i++){
                                weatherControl.UpdataWeatherViewFromHttp(userEntity.favoritecity.get(i)
                                        ,mycareRecyclerviewAdapter);
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