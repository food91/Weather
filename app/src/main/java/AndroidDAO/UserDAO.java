package AndroidDAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Entity.UserEntity;


@Dao
public interface UserDAO {


    @Query("SELECT * FROM users")
    List<UserEntity> getAll();

    @Query("SELECT * FROM users WHERE userid == :userid")
    UserEntity getUser(String userid);

    @Insert
    void insert(UserEntity userEntity);

    @Query("delete from users")
    void deleteAll();

    @Update
    void updateUsers(UserEntity...userEntities);
}
