package control;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PostProcessor;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.xiekun.myapplication.R;

import java.util.List;

import Entity.LoginData;
import Entity.UserEntity;
import acitivity.MyApplication;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import util.GsonUtilX;

public class Login {

    private LoginData mloginData;

    public Login(String u,String p,boolean ub,boolean pb){
        mloginData=new LoginData(u,p,ub,pb);
    }

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

    public boolean IsPword(Context context){
        SharedPreferences sharedPreferences= context.getSharedPreferences(LoginData.LOGIN, Context .MODE_PRIVATE);
        String ls=sharedPreferences.getString(LoginData.LOGINDATA,"");
        List<LoginData> m= GsonUtilX.parseArrayJsonWithGson(ls,LoginData.class);
        for(int i=0;i<m.size();i++){
            String user=m.get(i).getUser();
            String password=m.get(i).getPassword();
            if(password.equals(mloginData.getPassword())){
                return true;
            }
        }
        return false;
    }

    public boolean  IsUser(Context context){

        SharedPreferences sharedPreferences= context.getSharedPreferences(LoginData.LOGIN, Context .MODE_PRIVATE);
        String ls=sharedPreferences.getString(LoginData.LOGINDATA,"");
        List<LoginData> m= GsonUtilX.parseArrayJsonWithGson(ls,LoginData.class);
        for(int i=0;i<m.size();i++){
            String user=m.get(i).getUser();
            String password=m.get(i).getPassword();
            if(user.equals(mloginData.getUser())){
                return true;
            }
        }
        return false;
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

    public void register(Context context,String u,String p){

        final QMUITipDialog  tipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(context.getResources().getString(R.string.login_loading_register))
                .create();
                tipDialog.show();
       Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                UserEntityRepository  userEntityRepository=new UserEntityRepository(MyApplication.getApplicationInstance());
                userEntityRepository.InsertId(u,p);
                emitter.onNext("123");
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        tipDialog.dismiss();
                        QMUITipDialog tipDialog_su = new QMUITipDialog.Builder(context)
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                                .setTipWord(context.getResources().getString(R.string.login_register_success))
                                .create();

                    }

                    @Override
                    public void onError(Throwable e) {
                        tipDialog.dismiss();
                        Toast.makeText(context,
                                context.getResources().getString(R.string.login_register_fail)
                                , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
