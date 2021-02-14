package acitivity;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;


import com.jaeger.library.StatusBarUtil;
import com.xiekun.myapplication.R;


import adapter.Gloading;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @des:
 * @author: xk
 * @date: 2021/2/13 0013 04:23
 * @see {@link }
 */
public abstract class BaseActivityX extends BasePermissionsActivity  {


    protected Gloading.Holder mHolder;
    private RelativeLayout rlTitleBar;//标题栏
    private ImageView action_exit;
    private TextView action_title;

    public abstract int getContentViewResId();

    public abstract boolean showSystemWindows();

    public abstract void init(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置无标题
        if (!showSystemWindows()) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        if (getActionBar() != null)
            getActionBar().hide();
        setContentView(getContentViewResId());
        initBar();
        init(savedInstanceState);
    }

    protected void initLoadingStatusViewIfNeed() {
        if (mHolder == null) {
            //bind status view to activity root view by default
            mHolder = Gloading.getDefault().wrap(this).withRetry(new Runnable() {
                @Override
                public void run() {
                    onLoadRetry();
                }
            });
        }
    }

    protected void onLoadRetry() {
        // override this method in subclass to do retry task
    }

    protected void showLoading() {
        initLoadingStatusViewIfNeed();
        mHolder.showLoading();
    }

    protected void showSuccess(){
        initLoadingStatusViewIfNeed();
        mHolder.showLoadSuccess();
    }

    protected void showLoadFailed(){
        initLoadingStatusViewIfNeed();
        mHolder.showLoadFailed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    private void initBar() {
        rlTitleBar = LayoutInflater.from(this).inflate(R.layout.base_activity_bar,
                null).findViewById(R.id.common_title_bar);
        action_exit = rlTitleBar.findViewById(R.id.action_esc);
        action_title = rlTitleBar.findViewById(R.id.action_title);
        action_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //动态判断添加
        View mRootView = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        Class cls = mRootView.getClass();
        if (cls == LinearLayout.class) {//线性布局
            LinearLayout linearLayout = (LinearLayout) mRootView;
            linearLayout.addView(rlTitleBar, 0);
        } else if (cls == RelativeLayout.class) {//相对布局
            RelativeLayout relativeLayout = (RelativeLayout) mRootView;
            relativeLayout.addView(rlTitleBar, 0);
        } else if (cls == ConstraintLayout.class) {//约束布局
            ConstraintLayout constraintLayout = (ConstraintLayout) mRootView;
            constraintLayout.addView(rlTitleBar, 0);
        }
    }



    /**
     * 设置标题栏颜色
     *
     * @param color
     */
    protected void setTitleBarBg(int color) {
        rlTitleBar.setBackgroundColor(color);
    }

    /**
     * 检查网络是否可用
     *
     * @return
     */
    protected boolean checkNetWork() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 设置是否显示状态栏 如果显示 则会恢复默认颜色的状态栏
     *
     * @param isFullScreen
     */
    protected void setIsFullScreen(boolean isFullScreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = getWindow();
                View decorView = window.getDecorView();
                if (isFullScreen) { //全屏
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    getWindow().setAttributes(lp);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                } else { //非全屏
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    getWindow().setAttributes(lp);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                }
            } else {
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus;
                if (!isFullScreen) {
                    flagTranslucentStatus = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
                } else {
                    flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                }
                attributes.flags |= flagTranslucentStatus;
                window.setAttributes(attributes);
            }
        }
        if (!isFullScreen)
            setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    protected void setStatusBarColor(int color) {
        setStatusBarColor(color, 0);
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     * @param alpha
     */
    protected void setStatusBarColor(int color, int alpha) {
        StatusBarUtil.setColor(this, color, alpha);
    }

    /**
     * 设置状态栏全透明
     */
    protected void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
