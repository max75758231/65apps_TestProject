package com.example.a65apps_testproject.data.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Employee {

    @SerializedName("f_name")
    private String name;

    @SerializedName("l_name")
    private String surname;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("avatr_url")
    private String image;

    @SerializedName("specialty")
    private ArrayList<Specialty> specialties = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(ArrayList<Specialty> specialties) {
        this.specialties = specialties;
    }

    public class Specialty {
        @SerializedName("specialty_id")
        private Integer specialtyId;

        @SerializedName("name")
        private String specialtyName;

        public Integer getSpecialtyId() {
            return specialtyId;
        }

        public void setSpecialtyId(Integer specialtyId) {
            this.specialtyId = specialtyId;
        }

        public String getSpecialtyName() {
            return specialtyName;
        }

        public void setSpecialtyName(String specialtyName) {
            this.specialtyName = specialtyName;
        }
    }
}
