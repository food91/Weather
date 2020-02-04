package control;

import android.content.Context;
import android.content.SharedPreferences;

import com.orhanobut.logger.Logger;

import data.LoginData;

public class Login {

    private LoginData mloginData;

    public  Login() {
    }

    public Login(LoginData loginData){
        this.mloginData=loginData;
    }

    public Login(Context context){
        SharedPreferences sharedPreferences= context.getSharedPreferences("data", Context .MODE_PRIVATE);
       String user=sharedPreferences.getString(LoginData.SUSER,"");
       String password=sharedPreferences.getString(LoginData.SPASSWORD,"");
       mloginData=new LoginData(user,password);
    }

    public Login(String user, String password){

        mloginData=new LoginData(user,password);
    }

    public boolean  IsUser(Context context){

        SharedPreferences sharedPreferences= context.getSharedPreferences(LoginData.LOGINDATA, Context .MODE_PRIVATE);
        String user=sharedPreferences.getString(LoginData.SUSER,"");
        String password=sharedPreferences.getString(LoginData.SPASSWORD,"");
        Logger.d("m="+user+"p="+password);
        Logger.d("m2="+mloginData.getUser()+"p2="+mloginData.getPassword());
        if(user.equals(mloginData.getUser())&&password.equals(mloginData.getPassword())){
            return true;
        }else{
            return false;
        }
    }

    public void wirteDate(Context context,String user,String password) {
        //步骤1：创建一个SharedPreferences对象
        SharedPreferences sharedPreferences = context.getSharedPreferences(LoginData.LOGINDATA, Context.MODE_PRIVATE);
        //步骤2： 实例化SharedPreferences.Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //步骤3：将获取过来的值放入文件
        editor.putString(LoginData.SUSER,user);
        editor.putString(LoginData.SPASSWORD,password);

        //步骤4：提交
        editor.commit();
    }

}
