package com.example.a65apps_testproject.data.database.model;

import com.example.a65apps_testproject.data.datamodel.Employee;
import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

import java.util.ArrayList;

public class DBEmployeePlacement extends SugarRecord {

    public String name;
    public String surname;
    public String birthday;
    public String image;

    public DBEmployeePlacement(String name, String surname, String birthday, String image) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.image = image;
    }

    public DBEmployeePlacement() {
    }
}