package acitivity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.jaeger.library.StatusBarUtil;
import com.xiekun.myapplication.R;

public class SetActivity extends Xactivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        init();
    }

    @Override
    protected void init() {


        
        StatusBarUtil.setColor(this,0,0);


    }
}
