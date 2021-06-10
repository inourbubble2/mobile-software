package com.example.mobliesoftware9.DB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper
{
    private static SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String DateToString(Date data)
    {
        return mDateFormat.format(data);
    }

    public static Date StringToDate(String dateFormat)
    {
        try
        {
            return mDateFormat.parse(dateFormat);
        }
        catch (ParseException e)
        {
            throw new AssertionError("Date Parse 실패");
        }

    }
}
