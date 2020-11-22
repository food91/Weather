package acitivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiekun.myapplication.R;

import control.StatusLayoutManager;

public abstract class BaseActivity extends AppCompatActivity {

    protected StatusLayoutManager statusLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_base_view);
    }

    protected abstract initStatusLayout(){

    }

    protected abstract void initView(){

    }

    protected abstract initView(){
        LinearLayout linearLayout=findViewById(R.id.ll_base_activity_main);
        linearLayout.addView();
    }

    protected void showContent(){

    }

    protected void showEmptyData(){

    }

    protected void showError(){

    }

    protected void showNetworkError(){

    }

    protected void showLoading(){

    }


}
