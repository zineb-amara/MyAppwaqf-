package com.example.myappwaqf;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "waqf_table")
public class WaqfEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String icon;
    private String type;
    private String location;
    private String cost;
    private int targetAmount;
    private int collectedAmount;
    private int donorsCount;

    // Constructor
    public WaqfEntity(String icon, String type, String location,
                      String cost, int targetAmount, int collectedAmount) {
        this.icon = icon;
        this.type = type;
        this.location = location;
        this.cost = cost;
        this.targetAmount = targetAmount;
        this.collectedAmount = collectedAmount;
        this.donorsCount = 0;
    }

    // Getters
    public int getId() { return id; }
    public String getIcon() { return icon; }
    public String getType() { return type; }
    public String getLocation() { return location; }
    public String getCost() { return cost; }
    public int getTargetAmount() { return targetAmount; }
    public int getCollectedAmount() { return collectedAmount; }
    public int getDonorsCount() { return donorsCount; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setIcon(String icon) { this.icon = icon; }
    public void setType(String type) { this.type = type; }
    public void setLocation(String location) { this.location = location; }
    public void setCost(String cost) { this.cost = cost; }
    public void setTargetAmount(int targetAmount) { this.targetAmount = targetAmount; }
    public void setCollectedAmount(int collectedAmount) { this.collectedAmount = collectedAmount; }
    public void setDonorsCount(int donorsCount) { this.donorsCount = donorsCount; }
}