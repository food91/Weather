package data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;


/**
 * The type User entity.
 *用于构建数据库的实体，计划数据表是 id ---username-password-Favoritecity
 */
@Entity(tableName = "users")
public class UserEntity {

    /**
     * The Id.
     */
    @PrimaryKey
    public int id;

    /**
     * The Userid.
     */
    public String userid;

    /**
     * The Password.
     */
    public String password;

    /**
     * The Favoritecity.关注城市名
     */
    public List<String> favoritecity;

    //城市图片路径
    public List<String> city_picture_path;

}
