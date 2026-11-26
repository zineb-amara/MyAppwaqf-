package com.example.myappwaqf;
public class WaqfItem {
    String icon;
    String name;
    String location;
    String cost;
    int progress;
    int families;

    public WaqfItem(String icon, String name, String location, String cost, int progress, int families) {
        this.icon = icon;
        this.name = name;
        this.location = location;
        this.cost = cost;
        this.progress = progress;
        this.families = families;
    }
}
