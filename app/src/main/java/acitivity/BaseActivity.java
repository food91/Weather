package acitivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiekun.myapplication.R;

import control.StateLayoutManager;

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

    protected abstract void initStatusLayout();

    protected abstract void initView();

    private   void initBaseView(){
        LinearLayout linearLayout=findViewById(R.id.ll_base_activity_main);
        linearLayout.addView(statusLayoutManager.getRootLayout());
    }

    protected void showContent(){
        statusLayoutManager.showContent();
    }

    protected void showEmptyData(){
        statusLayoutManager.showEmptyData();
    }

    protected void showError(){
        statusLayoutManager.showError();
    }

    protected void showNetworkError(){
        statusLayoutManager.showNetWorkError();
    }

    protected void showLoading(){
        statusLayoutManager.showLoading();
    }


}
