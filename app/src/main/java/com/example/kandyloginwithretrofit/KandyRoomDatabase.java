package com.example.kandyloginwithretrofit;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Token.class, User.class}, version = 1, exportSchema = false)
public abstract class KandyRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public static KandyRoomDatabase instance;

    /**
     * Thread Safe Double Check Singleton Initialization
     *
     * @param context The context for the database.
     * @return instance of the KandyRoomDatabase
     */
    static KandyRoomDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (KandyRoomDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            KandyRoomDatabase.class, "kandyDb")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }
}
