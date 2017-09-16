package com.example.a65apps_testproject.data.datamodel;

import java.util.ArrayList;

public class CollectionModel {

    private static ArrayList<Employee> employeesFirstSpec;

    private static ArrayList<Employee> employeesSecondSpec;

    public static ArrayList<Employee> getEmployeesFirstSpec() {
        return employeesFirstSpec;
    }

    public static void setEmployeesFirstSpec(ArrayList<Employee> employeesFirstSpec) {
        CollectionModel.employeesFirstSpec = employeesFirstSpec;
    }

    public static ArrayList<Employee> getEmployeesSecondSpec() {
        return employeesSecondSpec;
    }

    public static void setEmployeesSecondSpec(ArrayList<Employee> employeesSecondSpec) {
        CollectionModel.employeesSecondSpec = employeesSecondSpec;
    }
}
