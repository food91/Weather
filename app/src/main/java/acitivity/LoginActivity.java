package acitivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.xiekun.myapplication.R;

import Entity.LoginData;
import Entity.UserData;
import butterknife.BindView;
import butterknife.ButterKnife;
import control.Login;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import util.GsonUtilX;
import util.UtilX;

/**
 * The type Login activity.
 *
 */
public class LoginActivity extends Xactivity {

    /**
     * The Login sure button.
     */
    @BindView(R.id.login_sure_button)
    QMUIRoundButton loginSureButton;
    /**
     * The Button login register.
     */
    @BindView(R.id.button_login_register)
    QMUIRoundButton buttonLoginRegister;
    /**
     * The Login password text.
     */
    @BindView(R.id.login_password_text)
    TextInputEditText loginPasswordText;
    /**
     * The Login user text.
     */
    @BindView(R.id.login_user_text)
    TextInputEditText loginUserText;
    /**
     * The Image view 2.
     */
    @BindView(R.id.imageView2)
    ImageView imageView2;
    /**
     * The Text view 2.
     */
    @BindView(R.id.textView2)
    TextView textView2;
    /**
     * The Check box keeppassword.
     */
    @BindView(R.id.checkBox_keeppassword)
    CheckBox checkBoxKeeppassword;
    /**
     * The Check box keepuser.
     */
    @BindView(R.id.checkBox_keepuser)
    CheckBox checkBoxKeepuser;
    /**
     * The Empty view.
     */
    @BindView(R.id.emptyView)
    QMUIEmptyView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        onclick();
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
         buttonLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = loginUserText.getText().toString();
                String password = loginPasswordText.getText().toString();
                if (!user_pw_check(user, password)) {
                    return;
                }
                boolean keepuser = checkBoxKeepuser.isChecked();
                boolean keeppassword = checkBoxKeeppassword.isChecked();
                Login.register(LoginActivity.this,user,password,emptyView);

            }
        });
        loginSureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = loginUserText.getText().toString();
                String password = loginPasswordText.getText().toString();
                boolean keepuser = checkBoxKeepuser.isChecked();
                boolean keeppassword = checkBoxKeeppassword.isChecked();
                if (!user_pw_check(user, password)) {
                    return;
                }
                emptyView.show(true,
                      getResources().getString(R.string.login_loading_login)
                        ,null,null,null);
                emptyView.setTitleColor(Color.WHITE);
                Login login = new Login(user, password,keepuser,keeppassword);
                Observer<Boolean> observer=new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if(aBoolean) {
                            UserData.getUserData().setName(user);
                            UserData.getUserData().setPassword(password);
                            login.wirteDate(LoginActivity.this);
                            Intent intent = new Intent(LoginActivity.this, WeatherActivity.class);
                            //启动
                            startActivity(intent);
                            finish();
                        }else {
                            emptyView.hide();
                            Toast.makeText(LoginActivity.this,
                                    getResources().getString(R.string.login_Wrong)
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        emptyView.hide();
                        Toast.makeText(LoginActivity.this,
                                getResources().getString(R.string.login_unknwon_Wrong)
                                , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                };

                login.IsUser(user,password,observer);
            }
        });
    }

    protected void init() {

        emptyView.setVisibility(View.GONE);
        SharedPreferences sharedPreferences= getSharedPreferences(LoginData.LOGIN, Context.MODE_APPEND);
        String userId=sharedPreferences.getString(LoginData.LOGINDATA,"");
        if(!userId.equals("")){
            Logger.d("user==="+userId);
            LoginData loginData= GsonUtilX.parseJsonWithGson(userId,LoginData.class);
            if(loginData.isKeepuser()){
                loginUserText.setText(loginData.getUser());
                checkBoxKeepuser.setChecked(true);
            }
            if(loginData.isKeeppassword()){
                loginPasswordText.setText(loginData.getPassword());
                checkBoxKeeppassword.setChecked(true);
            }
        }
    }
}
