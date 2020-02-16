package acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.xiekun.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import control.Login;
import util.UtilX;

public class LoginActivity extends Xactivity {

    @BindView(R.id.login_sure_button)
    QMUIRoundButton loginSureButton;
    @BindView(R.id.button_login_register)
    QMUIRoundButton buttonLoginRegister;
    @BindView(R.id.login_password_text)
    TextInputEditText loginPasswordText;
    @BindView(R.id.login_user_text)
    TextInputEditText loginUserText;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.checkBox_keeppassword)
    CheckBox checkBoxKeeppassword;
    @BindView(R.id.checkBox_keepuser)
    CheckBox checkBoxKeepuser;
    private Button login, btn_login_register;
    private TextInputEditText usertext, passwordtext;

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
        btn_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = usertext.getText().toString();
                String password = passwordtext.getText().toString();
                if (!user_pw_check(user, password)) {
                    return;
                }
                boolean keepuser =checkBoxKeepuser.isChecked();
                boolean keeppassword=checkBoxKeeppassword.isChecked();
                Login login=new Login(user,password,keepuser,keeppassword);
                if(login.IsUser(getApplication())){
                    Toast.makeText(LoginActivity.this,
                            getResources().getString(R.string.login_effect)
                            , Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = usertext.getText().toString();
                String password = passwordtext.getText().toString();
                if (!user_pw_check(user, password)) {
                    return;
                }
                Logger.d("user==" + user + " pa==" + password);
                Login login = new Login(user, password);
                login.wirteDate(getApplication(), user, password);
                if (login.IsUser(getApplicationContext())) {

                    Intent intent = new Intent(LoginActivity.this, WeatherActivity.class);
                    //启动
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,
                            getResources().getString(R.string.login_Wrong)
                            , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    protected void init() {

    }
}
