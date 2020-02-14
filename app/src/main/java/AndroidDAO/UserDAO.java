package AndroidDAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import data.UserEntity;


@Dao
public interface UserDAO {


    @Query("SELECT * FROM users")
    List<UserEntity> getAll();

    @Query("SELETE * FROM users WHERE userid == :userid")
    LiveData<List<UserEntity>> getUser();

    @Insert
    void insert(UserEntity userEntity);

    @Query("delete from users")
    void deleteAll();

    @Update
    void updateUsers(UserEntity...userEntities)
}
