package com.example.myappwaqf;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {WaqfEntity.class, DonationEntity.class},
        version = 3,
        exportSchema = false
)
public abstract class WaqfDatabase extends RoomDatabase {

    private static WaqfDatabase instance;

    public abstract WaqfDao     waqfDao();
    public abstract DonationDao donationDao();

    public static synchronized WaqfDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            WaqfDatabase.class,
                            "waqf_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}