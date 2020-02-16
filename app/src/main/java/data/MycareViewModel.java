package data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import Entity.UserData;
import Entity.UserEntity;
import acitivity.MyApplication;
import control.UserEntityRepository;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MycareViewModel extends ViewModel {


    private MutableLiveData<UserEntity> userEntityLiveData;
    UserEntityRepository userEntityRepository;

    public MycareViewModel() {
        userEntityRepository=new UserEntityRepository(MyApplication.getApplicationInstance());
        userEntityLiveData=new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                userEntityLiveData.postValue(userEntityRepository.QueryId(UserData.getUserData().getName()));
            }
        }).start();
    }



    public MutableLiveData<UserEntity> getIdEntity(){
        return userEntityLiveData;
    }


}