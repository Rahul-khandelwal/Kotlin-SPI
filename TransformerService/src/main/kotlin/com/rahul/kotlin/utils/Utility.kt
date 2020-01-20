package com.rahul.kotlin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

fun getFormattedCurrentDate(format: String) : String {
    return SimpleDateFormat(format).format(Date())
}

fun getOneYearLaterDate(format: String) : String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.YEAR, 1)
    return SimpleDateFormat(format).format(calendar.time)
}

fun calculateAge(date: String, format: String) : Int? {
    try {
        val dob = SimpleDateFormat(format).parse(date);
        val now = Date();

        var diff = now.time- dob.time
        return (diff / (1000.toLong() * 3600.toLong() * 24.toLong() * 365.toLong())).toInt()
    } catch (e: ParseException) {
        return null
    }
}

fun reformatDate(date: String, formatFrom: String, formatTo: String) : String? {
    try {
        return SimpleDateFormat(formatTo).format(SimpleDateFormat(formatFrom).parse(date))
    } catch (e: ParseException) {
        return null
    }
}
