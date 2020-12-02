package acitivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.jaeger.library.StatusBarUtil;
import com.xiekun.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import view.XSwitchView;

public class SetActivity extends BaseActivity {

    @BindView(R.id.tv_set_ac_tip)
    TextView tvSetAcTip;
    @BindView(R.id.tv_set_ac_tip_detail)
    TextView tvSetAcTipDetail;
    @BindView(R.id.xs_set_ac_tip)
    XSwitchView xsSetAcTip;
    @BindView(R.id.toolbar_set_ac)
    Toolbar toolbarSetAc;
    @BindView(R.id.tv_set_ac_warm)
    TextView tvSetAcWarm;
    @BindView(R.id.tv_set_ac_warm_detail)
    TextView tvSetAcWarmDetail;
    @BindView(R.id.xs_set_ac_warm)
    XSwitchView xsSetAcWarm;
    @BindView(R.id.tv_set_ac_abnormal)
    TextView tvSetAcAbnormal;
    @BindView(R.id.tv_set_ac_abnormal_detail)
    TextView tvSetAcAbnormalDetail;
    @BindView(R.id.xs_set_abnormal_tip)
    XSwitchView xsSetAbnormalTip;
    @BindView(R.id.tv_set_night_tip)
    TextView tvSetNightTip;
    @BindView(R.id.tv_set_ac_night_detail)
    TextView tvSetAcNightDetail;
    @BindView(R.id.xs_set_night_tip)
    XSwitchView xsSetNightTip;
    @BindView(R.id.tv_set_ac_tep)
    TextView tvSetAcTep;
    @BindView(R.id.tv_set_ac_wind)
    TextView tvSetAcWind;
    @BindView(R.id.tv_set_ac_nightupdate)
    TextView tvSetAcNightupdate;
    @BindView(R.id.tv_set_ac_nightupdate_detail)
    TextView tvSetAcNightupdateDetail;
    @BindView(R.id.xs_set_ac_nightupdate)
    XSwitchView xsSetAcNightupdate;
    @BindView(R.id.tv_set_ac_weathervocie)
    TextView tvSetAcWeathervocie;
    @BindView(R.id.tv_set_ac_weathervocie_detail)
    TextView tvSetAcWeathervocieDetail;
    @BindView(R.id.xs_set_ac_weathervocie)
    XSwitchView xsSetAcWeathervocie;





    protected void init() {
        showContent();
        StatusBarUtil.setColor(this,0);
        setSupportActionBar(toolbarSetAc);
        toolbarSetAc.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void setContentViewLayout(int... layout) {
        super.setContentViewLayout(R.layout.activity_set);
    }

    @Override
    protected void initView() {
        showContent();
        ButterKnife.bind(this);
        init();
    }
}
