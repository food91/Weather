package acitivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiekun.myapplication.R;

import control.OnNetworkListener;
import control.OnRetryListener;
import control.StateLayoutManager;
import util.UtilX;

public abstract class BaseActivity extends AppCompatActivity {

    protected StateLayoutManager statusLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_base_view);
        initStatusLayout();
        initBaseView();
        initView();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_view);
        UtilX.LogX("base oncreate----------after");
        setContentViewLayout();
        initBaseView();
        initView();
    }

    protected  void setContentViewLayout(int...i)
    {
        initStatusLayout((int)i[0]);
    }

    private void initStatusLayout(@LayoutRes int  layout){
        statusLayoutManager = StateLayoutManager.newBuilder(this)
                .contentView(layout)
                .emptyDataView(R.layout.activity_emptydata)
                .errorView(R.layout.activity_error)
                .loadingView(R.layout.activity_showloading)
                .netWorkErrorView(R.layout.activity_networkerror)
                //设置空数据页面图片控件id
                .emptyDataIconImageId(R.id.image)
                //设置空数据页面文本控件id
                .emptyDataTextTipId(R.id.tv_content)
                //设置异常页面图片id
                .errorIconImageId(R.id.image)
                //设置异常页面文本id
                .errorTextTipId(R.id.tv_content)
                .onRetryListener(new OnRetryListener() {
                    @Override
                    public void onRetry() {
                        //点击重试
                        // showContent();
                    }
                })
                .onNetworkListener(new OnNetworkListener() {
                    @Override
                    public void onNetwork() {
                        //网络异常，点击重试
                        // showLoading();
                    }
                })
                .build();

    }



    protected abstract void initView();

    private   void initBaseView(){
        LinearLayout linearLayout=findViewById(R.id.ll_base_activity_main);
        linearLayout.addView(statusLayoutManager.getRootLayout());
    }

    protected void showContent(){
        UtilX.LogX("show Content");
        try {
            statusLayoutManager.showContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showEmptyData(){
        try {
            statusLayoutManager.showEmptyData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showError(){
        try {
            statusLayoutManager.showError();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showNetworkError(){
        try {
            statusLayoutManager.showNetWorkError();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showLoading(){
        try {
            statusLayoutManager.showLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
