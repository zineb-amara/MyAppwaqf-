package com.example.myappwaqf;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface DonationDao {

    @Insert
    void insert(DonationEntity donation);

    @Query("SELECT * FROM donation_table ORDER BY date DESC")
    List<DonationEntity> getAllDonations();

    @Query("SELECT * FROM donation_table WHERE waqfId = :waqfId ORDER BY date DESC")
    List<DonationEntity> getDonationsByWaqf(int waqfId);

    @Query("SELECT SUM(amount) FROM donation_table WHERE waqfId = :waqfId")
    int getTotalByWaqf(int waqfId);
}