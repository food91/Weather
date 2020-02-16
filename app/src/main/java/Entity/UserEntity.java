package Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import AndroidDAO.StringTypeConverterCity;


/**
 * The type User entity.
 *用于构建数据库的实体，计划数据表是 id ---username-password-Favoritecity
 */
@Entity(tableName = "users",
        indices = {@Index(value = {"userid"},
                unique = true)}
)
public class UserEntity {

    /**
     * The Id.自增主键
     */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * The Userid.
     */
    @ColumnInfo(name = "userid")
    public String userid;

    /**
     * The Password.
     */
    @ColumnInfo(name = "password")
    public String password;

    /**
     * The Favoritecity.关注城市名
     */
    @ColumnInfo(name = "favoritecity")
    @TypeConverters(StringTypeConverterCity.class)
    public List<String> favoritecity;

    //城市图片路径
    @ColumnInfo(name = "city_picture_path")
    @TypeConverters(StringTypeConverterCity.class)
    public List<String> city_picture_path;

}
