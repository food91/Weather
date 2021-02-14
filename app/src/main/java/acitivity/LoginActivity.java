package acitivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.room.util.StringUtil;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.xiekun.myapplication.R;

import java.util.Random;

import Entity.LoginData;
import Entity.UserData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import control.Login;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.http.Url;
import util.GsonUtilX;
import util.MMKVUtils;
import util.UtilX;
import view.FullScreenVideoView;

/**
 * The type Login activity.
 */
public class LoginActivity extends CommonActivity {


    @BindView(R.id.activity_login_videoview)
    FullScreenVideoView activityLoginVideoview;
    @BindView(R.id.activity_login_user)
    EditText activityLoginUser;
    @BindView(R.id.activity_login_ps)
    EditText activityLoginPs;
    @BindView(R.id.activity_login_sure)
    Button activityLoginSure;
    @BindView(R.id.activity_login_regist)
    TextView activityLoginRegist;
    @BindView(R.id.activity_login_privacy)
    CheckBox activityLoginPrivacy;
    String[] video_path;

    @Override
    public boolean showSystemWindows() {
        return false;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle var1) {
        setTitle("BaseActivity");//设置标题
        initView();
    }




    private void initView() {

        ButterKnife.bind(this);
        init();
        onclick();
    }


    @Override
    protected void onPause() {
        super.onPause();
        activityLoginVideoview.pause();
        activityLoginVideoview.stopPlayback();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityLoginVideoview.resume();
        activityLoginVideoview.start();
    }

    private boolean userisnull(String user, String password) {
        if (UtilX.IsStringNull(user) || UtilX.IsStringNull(password)) {
            Toast.makeText(LoginActivity.this,
                    getResources().getString(R.string.login_null)
                    , Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean userandpwIsFail(String user, String password) {
        if (!(UtilX.IsStringEffect(user) && UtilX.IsStringEffect(password))) {
            Toast.makeText(LoginActivity.this,
                    getResources().getString(R.string.login_effect)
                    , Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean user_pw_check(String user, String password) {

        if (userandpwIsFail(user, password) || userisnull(user, password)) {
            return false;
        }
        return true;

    }

    private void onclick() {
        activityLoginRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = activityLoginUser.getText().toString();
                String password = activityLoginPs.getText().toString();
                if (!user_pw_check(user, password)) {
                    return;
                }
                if(!activityLoginPrivacy.isChecked()){
                    ToastUtils.showLong("请同意下方隐私协议");
                    return;
                }
                try {
                    showLoading();
                    Login.register( user, password, new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(String string) {
                            showSuccess();
                            ToastUtils.showLong("   注册成功  ");
                        }

                        @Override
                        public void onError(Throwable e) {
                            BarUtils.setStatusBarVisibility(LoginActivity.this,true);
                            showLoadFailed("注册失败:"+e.getMessage(),R.drawable.loading);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        activityLoginSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = activityLoginUser.getText().toString();
                String password = activityLoginPs.getText().toString();
                if (!user_pw_check(user, password)) {
                    return;
                }
                Login login = new Login(user, password,LoginActivity.this);
                Observer<Boolean> observer = new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            UtilX.LogX("on activity");
                            UserData.getUserData().setName(user);
                            UserData.getUserData().setPassword(password);
                            Intent intent = new Intent(LoginActivity.this, WeatherActivity.class);
                            //启动
                            startActivity(intent);

                            finish();
                        } else {
                            showSuccess();
                            ToastUtils.showLong("用户名或密码错误");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showLoadFailed();
                        ToastUtils.showLong("登录失败 "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                };
                showLoading();
                login.IsUser(user, password, observer);
            }
        });
        activityLoginVideoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Random random=new Random();
                int n=random.nextInt(video_path.length);
                activityLoginVideoview.setVideoPath(video_path[n]);
                activityLoginVideoview.start();
            }
        });
    }

    protected void showLoadFailed(String text,int image) {
        initLoadingStatusViewIfNeed();
        mHolder.showLoadFailed(text,image);
    }

    protected void init() {
        video_path=new String[1];
        video_path[0]= Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.login_v2).toString();
        activityLoginVideoview.setVideoPath(video_path[0]);
        activityLoginVideoview.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
