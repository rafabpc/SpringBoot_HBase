package com.documentanalyzer.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimestampConverter {

    //Timestamp is being stored in the database, so, this method creates the instance based on a String date
    public static Timestamp getTimestamp(String dateString) throws ParseException {
        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse(dateString);
        long time = date.getTime();
        return new Timestamp(time);
    }

    //This method does a simple conversion from date to timestamp
    public static Timestamp getTimestamp(Date date){
        long time = date.getTime();
        return new Timestamp(time);
    }

    //This method decreases a certain amount of days from the current date
    public static Timestamp getTimestampTodaySubtractedByDays(Integer days){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -days);
        long time = calendar.getTime().getTime();
        return new Timestamp(time);
    }

}
