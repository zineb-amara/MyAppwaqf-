package com.example.myappwaqf;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface WaqfDao {

    @Insert
    void insert(WaqfEntity waqf);

    @Update
    void update(WaqfEntity waqf);

    @Query("SELECT * FROM waqf_table ORDER BY id DESC")
    List<WaqfEntity> getAllWaqf();

    //  المشاريع النشطة (اللي ما اكتملتش)
    @Query("SELECT * FROM waqf_table WHERE collectedAmount < targetAmount ORDER BY id DESC")
    List<WaqfEntity> getActiveWaqf();

    @Query("SELECT * FROM waqf_table WHERE id = :id")
    WaqfEntity getWaqfById(int id);

    @Query("UPDATE waqf_table SET collectedAmount = collectedAmount + :amount, " +
            "donorsCount = donorsCount + 1 WHERE id = :id")
    void addDonation(int id, int amount);

    @Query("DELETE FROM waqf_table")
    void deleteAll();
}