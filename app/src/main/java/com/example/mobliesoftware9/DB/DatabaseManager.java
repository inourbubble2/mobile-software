package com.example.mobliesoftware9.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.AbstractMap;
import java.util.Vector;

import static java.sql.DriverManager.println;


public class DatabaseManager extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    private static DatabaseManager mInstance = null;
    //싱글톤 패턴으로 구현
    public static DatabaseManager GetInstance()
    {
        if(mInstance == null)
        {
            mInstance = new DatabaseManager();
        }

        return mInstance;
    }

    private SQLiteDatabase mDatabase = null;

    public DatabaseManager()
    {
        this.mInstance = this;
        this.OpenDatabase();
    }

    private void OpenDatabase()
    {
        SQLiteEasyHelper helper = new SQLiteEasyHelper(this); //헬퍼를 생성함
        mDatabase = helper.getWritableDatabase(); //읽기 쓰기 모두 가능
    }

    public SQLiteDatabase GetSQLiteDatabase()
    {
        return this.mDatabase;
    }

    public static class ColumnContainer
    {
        public String mColumnName;
        public String mColumnType;
        public boolean mPrimaryKey = false;

        public ColumnContainer(String columnName, String columnType)
        {
            mColumnName = columnName;
            mColumnType = columnType;
        }
        public ColumnContainer(String columnName, String columnType, boolean primaryKey)
        {
            mColumnName = columnName;
            mColumnType = columnType;
            mPrimaryKey = primaryKey;
        }

    }

    //사용법 :
    //
    //DatabaseManager.GetInstance().CreateTable("dsf", new ColumnContainer[]
    //{
    //    new ColumnContainer("password", "text", true),
    //    new ColumnContainer("age", "integer", false)
    //}
    //);
    //
    public void CreateTable
            (
            String tableName,
            ColumnContainer columns[]
            )
    {
        if (mDatabase != null)
        {
            String queryStr =
                    "create table " +
                    tableName +
                    " (";

            for(ColumnContainer column : columns)
            {
                queryStr += ',' + column.mColumnName;
                queryStr += ' ' + column.mColumnType;
                if(column.mPrimaryKey == true)
                {
                    queryStr += " PRIMARY KEY autoincrement";
                }

            }
            queryStr += ')';
            this.CreateTable(queryStr);

        } else {
            throw new AssertionError("데이터베이스가 아직 오픈 되지 않았습니다");
        }
    }

    //사용법 :
    //
    //DatabaseManager.GetInstance().CreateTable(
    // "create table " + tableName +
    // "(primaryKey integer PRIMARY KEY autoincrement, name text, age integer, mobile text)"
    // );
    //
    //
    public void CreateTable(String queryStr)
    {
        if (mDatabase != null)
        {
            mDatabase.execSQL(queryStr);
            println("테이블이 성공적으로 생성됨");
        } else {
            throw new AssertionError("데이터베이스가 아직 오픈 되지 않았습니다");
        }
    }

    public void DropTable(String tableName)
    {
        if (mDatabase != null)
        {
            mDatabase.execSQL("DROP TABLE IF EXISTS " + tableName);
            println("테이블이 성공적으로 삭제됨 (" + tableName + " )");
        }
        else {
            throw new AssertionError("데이터베이스가 아직 오픈 되지 않았습니다");
        }
    }


    //return : Primary Key
    //
    //사용법 :
    //
    //ContentValues insertedData = new ContentValues();
    //insertedData.put("id", "kmsjkh");
    //DatabaseManager.GetInstance().InsertData("tableName", insertedData);
    //
    long InsertOrUpdateColumnData(String tableName, ContentValues insertedData)
    {
        if (mDatabase != null)
        {
            long nowRowID = mDatabase.replaceOrThrow (tableName, null, insertedData);
            return nowRowID;
        }
        else {
            throw new AssertionError("데이터베이스가 아직 오픈 되지 않았습니다");
        }
    }



    public
    void UpdateColumnData(String tableName, ContentValues updatedData,
                          String wherePrimaryKeyColumnName, String[] primaryKey)
    {
        if (mDatabase != null)
        {
            mDatabase.update (tableName, updatedData, wherePrimaryKeyColumnName + "=?", primaryKey);

        }
        else {
            throw new AssertionError("데이터베이스가 아직 오픈 되지 않았습니다");
        }
    }

    void DeleteColumnData(String tableName, String whereColumn, String[] whereData)
    {
        if (mDatabase != null)
        {
            mDatabase.delete(tableName, whereColumn + " LIKE ?", whereData);
        }
        else {
            throw new AssertionError("데이터베이스가 아직 오픈 되지 않았습니다");
        }
    }

    public Cursor SelectData(String selectQueryStr)
    {
        if (mDatabase != null) {
            return mDatabase.rawQuery(selectQueryStr, null);
        }
        else
        {
            throw new AssertionError("데이터베이스가 아직 오픈 되지 않았습니다");
        }
    }

    /*
    public Cursor selectData(String tableName)
    {
        if (mDatabase != null)
        {
            String sql = "select name, age, mobile from " + tableName;

            Cursor cursor = mDatabase.rawQuery(sql, null); //파라미터는 없으니깐 null 값 넣어주면된다.
            println("조회된 데이터개수 :" + cursor.getCount());
            //for문으로해도되고 while 문으로 해도됨.
            for (int i = 0; i < cursor.getCount(); i++)
            {
                cursor.moveToNext();//이걸 해줘야 다음 레코드로 넘어가게된다.
                String name = cursor.getString(0); //첫번쨰 칼럼을 뽑아줌
                int age = cursor.getInt(1);
                String mobile = cursor.getString(2);
                println("#" + i + " -> " + name + ", " + age + ", " + mobile);
            }


            cursor.close(); //cursor라는것도 실제 데이터베이스 저장소를 접근하는 것이기 때문에 자원이 한정되있다. 그러므로 웬만하면 마지막에 close를 꼭 해줘야한다.
        }
    }
    */
}
