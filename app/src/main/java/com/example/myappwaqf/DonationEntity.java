package com.example.myappwaqf;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "donation_table")
public class DonationEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int    waqfId;
    private String donorName;
    private String donorPhone;
    private int    amount;
    private String paymentMethod;
    private String receiptNumber;
    private long   date;

    public DonationEntity(int waqfId, String donorName, String donorPhone,
                          int amount, String paymentMethod, String receiptNumber) {
        this.waqfId        = waqfId;
        this.donorName     = donorName;
        this.donorPhone    = donorPhone;
        this.amount        = amount;
        this.paymentMethod = paymentMethod;
        this.receiptNumber = receiptNumber;
        this.date          = System.currentTimeMillis();
    }

    public int    getId()            { return id; }
    public int    getWaqfId()        { return waqfId; }
    public String getDonorName()     { return donorName; }
    public String getDonorPhone()    { return donorPhone; }
    public int    getAmount()        { return amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getReceiptNumber() { return receiptNumber; }
    public long   getDate()          { return date; }

    public void setId(int id)       { this.id = id; }
    public void setDate(long date)  { this.date = date; }
}