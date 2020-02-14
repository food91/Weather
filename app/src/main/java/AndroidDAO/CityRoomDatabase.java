package AndroidDAO;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import data.UserEntity;

@Database(entities = {UserEntity.class},version = 1)
public class CityRoomDatabase extends RoomDatabase {

    private static volatile CityRoomDatabase INSTANCE;

    public static CityRoomDatabase getDatabase(Context context){
        if (INSTANCE == null) {
            synchronized (CityRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserEntity.class, "users_database").build();
                }
            }
        }
        return INSTANCE;
    }
    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
