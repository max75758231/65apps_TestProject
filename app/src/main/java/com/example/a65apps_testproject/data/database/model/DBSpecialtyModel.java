package com.example.a65apps_testproject.data.database.model;

import com.orm.SugarRecord;

public class DBSpecialtyModel extends SugarRecord {

    @Override
    public String toString() {
        return name + "";
    }

    private String name;

    public DBSpecialtyModel(String name) {
        this.name = name;
    }

    public DBSpecialtyModel() {
    }
}