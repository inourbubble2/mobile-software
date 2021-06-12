package com.example.mobliesoftware9.DB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateHelper
{

    private static DateTimeFormatter mDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String DateToString(LocalDateTime data)
    {
        return mDateFormat.format(data);
    }

    public static LocalDateTime StringToDate(String dateFormat)
    {
        try
        {
            return LocalDateTime.parse(dateFormat, mDateFormat);
        }
        catch (Exception e)
        {
            throw new AssertionError("Date Parse 실패");
        }

    }

    public static LocalDateTime GetCurrentDate()
    {
        return LocalDateTime.now();
    }
}
