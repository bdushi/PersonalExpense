package al.edu.feut.financaime.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by bruno on 12/06/2017.
 */

public class Utilities
{

    public static String format(double value)
    {
        DecimalFormat euro = new DecimalFormat("€###,###.###");
        return euro.format(value);
    }

    public static String dateFormat(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        return dateFormat.format(date.getTime());
    }

    public static int month()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    public static int month(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static String month(int month)
    {
        String[] monthStr  = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        return monthStr[month];
    }

    public static String year()
    {
        Calendar calendar = Calendar.getInstance();
        return String.format("%s", calendar.get(Calendar.YEAR));
    }

    public static Calendar incrementAndDecrement(Calendar calendar, int date) {
        calendar.add(Calendar.DATE, date);
        return calendar;
    }

    public static String format(Calendar calendar) {
        return String.format("%s %s %s", calendar.get(Calendar.DATE), getMonth(calendar.get(Calendar.MONTH)), calendar.get(Calendar.YEAR));
    }

    public static Calendar date()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
    public static Date date(Calendar calendar)
    {
        calendar.set(Calendar.HOUR, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static String getMonth (int month)
    {
        String[] monthStr = {"Janare", "Shkurte", "Marse", "Prill", "Maj", "Quershor", "Korrik", "Gusht", "Shtatore", "Tetor", "Nentore", "Dhjetore"};
        return monthStr[month];
    }

    public static String date (int date)
    {
       switch (date)
       {
           case 1:
               return "01";
           case 2:
               return "02";
           case 3:
               return "03";
           case 4:
               return "04";
           case 5:
               return "05";
           case 6:
               return "06";
           case 7:
               return "07";
           case 8:
               return "08";
           case 9:
               return "09";
           default:
               return String.valueOf(date);
       }
    }
}
