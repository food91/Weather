package acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.orhanobut.logger.Logger;
import com.xiekun.myapplication.R;



import control.Login;
import control.UtilX;

public class LoginActivity extends Xactivity {

    private Button login,exit;
    private TextInputEditText usertext,passwordtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        onclick();
    }



    private void onclick(){
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=usertext.getText().toString();
                String password=passwordtext.getText().toString();
                if(UtilX.IsStringNull(user)||UtilX.IsStringNull(password)){
                    Toast.makeText(LoginActivity.this,
                            getResources().getString(R.string.login_null)
                            ,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(UtilX.IsStringEffect(user)&&UtilX.IsStringEffect(password)))
                {
                    Toast.makeText(LoginActivity.this,
                            getResources().getString(R.string.login_effect)
                            ,Toast.LENGTH_SHORT).show();
                    return;
                }
                Logger.d("user=="+user+" pa=="+password);
                Login login=new Login(user,password);
                login.wirteDate(getApplication(),user,password);
                if(login.IsUser(getApplicationContext())){

                    Intent intent=new Intent(LoginActivity.this,WeatherActivity.class);
                    //启动
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,
                            getResources().getString(R.string.login_Wrong)
                            ,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    protected void init(){

        login=findViewById(R.id.login_sure_button);
        exit=findViewById(R.id.login_exit_button);
        usertext=findViewById(R.id.login_user_text);
        passwordtext=findViewById(R.id.login_password_text);

    }
}
