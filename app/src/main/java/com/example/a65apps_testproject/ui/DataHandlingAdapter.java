package com.example.a65apps_testproject.ui;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataHandlingAdapter {

    //The function for format of names and surnames to right case
    public static String formatString(String string) {
        return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
    }

    //The function for showing correct birthday string
    public static String fixBirthday(String birthday) throws ParseException {
        SimpleDateFormat fromFirstFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat fromSecondFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat toFormat = new SimpleDateFormat("dd.MM.yyyy");
        String fixedBirthday = "";

        if (birthday != null && !birthday.equals("")) {

            if (isValidFormat("dd-MM-yyyy", birthday)) {
                fixedBirthday = toFormat.format(fromFirstFormat.parse(birthday));
            } else {
                fixedBirthday = toFormat.format(fromSecondFormat.parse(birthday));
            }
            return fixedBirthday;
        } else {
            return "-";
        }
    }

    //Checking format of birthday string
    private static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

    //The function for calculating the age
    public static String calculatingAge(String birthday) {
        if (birthday != null && !birthday.equals("") && !birthday.equals("-")) {
            int age;
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.yyyy");
            LocalDate date = formatter.parseLocalDate(birthday);
            LocalDate now = new LocalDate();
            age = Years.yearsBetween(date, now).getYears();
            return String.valueOf(age);
        } else {
            return "-";
        }
    }
}