package com.example.mobliesoftware9.DB;

import android.database.Cursor;

import java.time.LocalDateTime;
import java.util.Date;

public class CursorWrapper
{
    public Cursor mCursor;

    public CursorWrapper(Cursor cursor)
    {
        mCursor = cursor;
    }

    public void CloseCursor()
    {
        this.mCursor.close();
    }

    public void MoveToNext()
    {
        this.mCursor.moveToNext();
    }

    public int GetCount()
    {
        return this.mCursor.getCount();
    }

    public void Close()
    {
        this.mCursor.close();
    }

    public byte[] GetByteArrayData(String columnName)
    {
        return this.mCursor.getBlob(this.mCursor.getColumnIndex(columnName));
    }

    public LocalDateTime GetDateData(String columnName)
    {
        return DateHelper.StringToDate(this.mCursor.getString(this.mCursor.getColumnIndex(columnName)));
    }

    public int GetIntegerData(String columnName)
    {
        return this.mCursor.getInt(this.mCursor.getColumnIndex(columnName));
    }

    public String GetStringData(String columnName)
    {
        return this.mCursor.getString(this.mCursor.getColumnIndex(columnName));
    }

    public long GetLongData(String columnName)
    {
        return this.mCursor.getLong(this.mCursor.getColumnIndex(columnName));
    }

    public float GetFloatData(String columnName)
    {
        return this.mCursor.getFloat(this.mCursor.getColumnIndex(columnName));
    }

    public short GetShortData(String columnName)
    {
        return this.mCursor.getShort(this.mCursor.getColumnIndex(columnName));
    }
}
