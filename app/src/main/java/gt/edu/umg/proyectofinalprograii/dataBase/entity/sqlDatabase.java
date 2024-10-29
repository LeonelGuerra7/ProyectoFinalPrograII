package gt.edu.umg.proyectofinalprograii.dataBase.entity;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import gt.edu.umg.proyectofinalprograii.Platillo;
import gt.edu.umg.proyectofinalprograii.dataBase.daoo.PlatilloDao;

@Database(entities = {Platillo.class}, version = 1)
public abstract class sqlDatabase extends RoomDatabase {

    public abstract PlatilloDao platilloDao();

    private static volatile sqlDatabase INSTANCE;

    public static sqlDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (sqlDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    sqlDatabase.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}