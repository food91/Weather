package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.xiekun.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import data.AboutViewModel;
import util.UtilX;


public class AboutFragment extends Fragment {

    @BindView(R.id.iv_cv_img_activity)
    ImageView ivCvImgActivity;
    @BindView(R.id.cv_img_about)
    CardView cvImgAbout;
    @BindView(R.id.about_app_version)
    TextView aboutAppVersion;
    @BindView(R.id.about_app_name)
    TextView aboutAppName;
    private AboutViewModel toolsViewModel;

    private Unbinder unbinder;
    private static final String url="https://cn.bing.com/th?id=OHR.WanderingAlbatross_ZH-CN3609426361_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(AboutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        unbinder = ButterKnife.bind(this, root);
        Glide.with(getContext())
                .load(url)
                .into(ivCvImgActivity);
        aboutAppVersion.setText("版本号:"+getString(R.string.app_version));
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}