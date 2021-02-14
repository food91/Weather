package control;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.rxjava.rxlife.BaseScope;
import com.rxjava.rxlife.RxLife;
import com.rxjava.rxlife.ScopeViewModel;
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
import util.MMKVUtils;

/**
 * The type Login.
 * SharedPreferences只存储用户状态，不对身份验证
 * 数据库对身份验证
 */
public class Login extends BaseScope  {

    private LoginData mloginData;

    public Login(LifecycleOwner owner) {
        super(owner);
    }

    public Login(String user,String pw,LifecycleOwner owner){
        super(owner);
        String mmk=MMKVUtils.getLoginData();
        mloginData=GsonUtilX.parseJsonWithGson(mmk,LoginData.class);
    }



    public void  IsUser(String id,String pw,
    Observer<Boolean> b){


        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                Thread.sleep(2500);
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
                .as(RxLife.as(this))
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
    public void wirteDate() {
        MMKVUtils.setLoginData(mloginData.toString());
    }


    public static void register( String u, String p, Observer<String> observer)
    throws  Exception{
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Thread.sleep(2500);
                UserEntityRepository  userEntityRepository=new UserEntityRepository(MyApplication.getApplicationInstance());
                userEntityRepository.InsertId(u,p);
                emitter.onNext("注册成功");
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);

    }

}
