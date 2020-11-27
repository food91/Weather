package acitivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiekun.myapplication.R;

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
        initStatusLayout();
        initBaseView();
        initView();
    }

    protected abstract void initStatusLayout();

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
