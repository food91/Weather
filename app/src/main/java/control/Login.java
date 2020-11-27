package control;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.xiekun.myapplication.R;

import Entity.LoginData;
import Entity.UserEntity;
import acitivity.BaseActivity;
import acitivity.MyApplication;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import util.GsonUtilX;

/**
 * The type Login.
 * SharedPreferences只存储用户状态，不对身份验证
 * 数据库对身份验证
 */
public class Login {

    private LoginData mloginData;

    /**
     * Instantiates a new Login.
     *
     * @param u  the u
     * @param p  the p
     * @param ub the ub
     * @param pb the pb
     */
    public Login(String u,String p,boolean ub,boolean pb){
        mloginData=new LoginData(u,p,ub,pb);
    }

    /**
     * Instantiates a new Login.
     */
    public  Login() {
    }


    /**
     * Instantiates a new Login.
     *
     * @param loginData the login data
     */
    public Login(LoginData loginData){
        this.mloginData=loginData;
    }

    /**
     * Instantiates a new Login.
     *
     * @param context the context
     */
    public Login(Context context){
        SharedPreferences sharedPreferences= context.getSharedPreferences("data", Context .MODE_PRIVATE);
    }

    /**
     * Instantiates a new Login.
     *
     * @param user     the user
     * @param password the password
     */
    public Login(String user, String password){

        mloginData=new LoginData(user,password);
    }

    /**
     * Is user boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public void  IsUser(String id,String pw,
    Observer<Boolean> b){


        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                UserEntityRepository  userEntityRepository=new UserEntityRepository(MyApplication.getApplicationInstance());
                UserEntity userData=userEntityRepository.QueryId(id);
                if(userData!=null&&userData.userid.equals(id)&&userData.password.equals(pw))
                {
                    emitter.onNext(true);
                }else{
                    emitter.onNext(false);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(b);

    }

    /**
     * Wirte date.
     *  @param user     the user
     * @param password the password
     * @param ku       the ku
     * @param kp       the kp
     * @param context  the context
     * @return
     */
    public Login wirteDate(Context context) {
        //步骤1：创建一个SharedPreferences对象
        SharedPreferences sharedPreferences = context.getSharedPreferences(LoginData.LOGIN, Context.MODE_PRIVATE);
        //步骤2： 实例化SharedPreferences.Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //步骤3：将获取过来的值放入文件

        editor.putString(LoginData.LOGINDATA,mloginData.toString());
        //步骤4：提交
        editor.commit();
        return this;
    }


    public static void register(Context context, String u, String p,
                                StateLayoutManager stateLayoutManager)
    throws  Exception{
        stateLayoutManager.showLoading();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Thread.sleep(2500);
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
                        try {
                            stateLayoutManager.showContent();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context,
                                context.getResources().getString(R.string.login_register_success)
                                , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            stateLayoutManager.showContent();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
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
