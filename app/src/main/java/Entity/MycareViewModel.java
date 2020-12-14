package Entity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import acitivity.MyApplication;
import control.UserEntityRepository;

public class MycareViewModel extends ViewModel {


    private MutableLiveData<UserEntity> userEntityLiveData;
    UserEntityRepository userEntityRepository;

    public MycareViewModel() {
        update();
    }

    public void update(){

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