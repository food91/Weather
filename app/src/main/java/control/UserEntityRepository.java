package control;

import android.app.Application;


import androidx.lifecycle.MutableLiveData;

import java.util.List;

import AndroidDAO.CityRoomDatabase;
import AndroidDAO.UserDAO;
import Entity.UserEntity;


public class UserEntityRepository {

    private UserDAO userDAO;
    private UserEntity userEntity;
    private MutableLiveData<List<UserEntity>> listLiveData;

    public UserEntityRepository(Application application) {
      CityRoomDatabase db = CityRoomDatabase.getDatabase(application);
        userDAO=db.wordDao();
    }

    public UserEntity QueryId(String id){

        return userDAO.getUser(id);
    }

    public void InsertId(String u,String p){
      UserEntity userEntity=new UserEntity();
      userEntity.userid=u;
      userEntity.password=p;
      userDAO.insert(userEntity);
    }
}
