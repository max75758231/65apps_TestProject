package com.example.a65apps_testproject.data.database.model;

import com.orm.SugarRecord;

public class DBEmployeeFirstModel extends SugarRecord {

    public String name;
    public String surname;
    public String birthday;
    public String image;

    public DBEmployeeFirstModel(String name, String surname, String birthday, String image) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.image = image;
    }

    public DBEmployeeFirstModel() {
    }
}
