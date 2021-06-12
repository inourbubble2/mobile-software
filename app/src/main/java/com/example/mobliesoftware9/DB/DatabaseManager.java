package com.example.mobliesoftware9.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import static java.sql.DriverManager.println;


public class DatabaseManager
{
    public static final String DB_NAME = "mobileSW9.db";

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

    private SQLiteEasyHelper mSQLiteEasyHelper = null;
    private SQLiteDatabase mDatabase = null;

    public DatabaseManager()
    {
        this.mInstance = this;

    }



    public void OpenDatabase(@Nullable Context context)
    {
        try
        {
            this.mSQLiteEasyHelper = new SQLiteEasyHelper(DB_NAME, context); //헬퍼를 생성함
            this.mDatabase = this.mSQLiteEasyHelper.getWritableDatabase(); //읽기 쓰기 모두 가능
        }
        catch (Exception e)
        {
            throw new AssertionError(e.getMessage());
        }

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
            ColumnContainer[] columns
            )
    {
        if (mDatabase != null)
        {
            String queryStr =
                    "create table IF NOT EXISTS " +
                    tableName +
                    " (";

            int primaryKeyCount = 0;
            String primaryKeyList = new String();
            for(int i = 0 ; i < columns.length ; i++)
            {
                if(i != 0)
                {
                    queryStr += ',';
                }

                queryStr += columns[i].mColumnName;
                queryStr += ' ' + columns[i].mColumnType;

                if(columns[i].mPrimaryKey == true)
                {
                    if(primaryKeyCount > 0)
                    {
                        primaryKeyList += ", ";
                    }
                    primaryKeyList += columns[i].mColumnName;
                    if(columns[i].mColumnType == "integer")
                    {
                        //primaryKeyList += " autoincrement";
                    }

                    primaryKeyCount++;
                }

            }

            queryStr += ", PRIMARY KEY (" + primaryKeyList + ')';
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


    //
    //사용법 :
    //
    //ContentValues insertedData = new ContentValues();
    //insertedData.put("id", "kmsjkh");
    //DatabaseManager.GetInstance().InsertData("tableName", insertedData);
    //
    public long InsertNewRow(String tableName, ContentValues insertedData)
    {
        if (mDatabase != null)
        {
            try
            {
                long rowID = mDatabase.insertOrThrow (tableName, null, insertedData);
                return rowID;
            }
            catch (Exception e)
            {
                throw new AssertionError(e.getMessage());
            }

        }
        else {
            throw new AssertionError("데이터베이스가 아직 오픈 되지 않았습니다");
        }
    }

    // Get Where Query from whereColumnName
    public static String GetWhereQuery(String[] whereColumnName)
    {
        String whereQuery = new String();
        for(int i = 0 ; i < whereColumnName.length ; i++)
        {
            if(i != 0)
            {
                whereQuery += "AND"; //!!!!!!
            }
            whereQuery += whereColumnName[i];
            whereQuery += "=?";
        }
        return whereQuery;
    }


    // 사용법 :
    // UpdateColumnData("table명", updatedData, "where 조건문 ex) mPrimaryKey", "2");
    // = mPrimaryKey가 "2"인 row(데이터)를 모두 updatedData로 Update하라
    //
    //
    // UpdateColumnData("table명", updatedData, new String[]{"name", "age"}, new String[]{"John", "12"});
    // // = name이 "John" 이고(AND) age가 12인 row(데이터)를 모두 updatedData로 Update하라
    //
    //
    // whereColumCompareData가 String[]인 이유 : SQLite가 다른 type은 허용안함
    // 그래서 integer도 String으로 변환해서 넘겨야한다.
    public void UpdateRows(String tableName, ContentValues updatedData,
                           String[] whereColumnNames, String[] whereColumnCompareData)
    {
        if (mDatabase != null)
        {
            mDatabase.update (tableName, updatedData, GetWhereQuery(whereColumnNames), whereColumnCompareData);
        }
        else {
            throw new AssertionError("데이터베이스가 아직 오픈 되지 않았습니다");
        }
    }

    // 사용법 :
    // DeleteColumnData("table명", "where 조건문 ex) mPrimaryKey", "2");
    // = mPrimaryKey가 "2"인 row(데이터)를 삭제하라
    //
    // DeleteColumnData("table명", new String[]{"name", "age"}, new String[]{"John", "12"});
    // // = name이 "John" 이고(AND) age가 12인 row(데이터)를 모두 삭제하라
    //
    // whereColumCompareData가 String[]인 이유 : SQLite가 다른 type은 허용안함
    // 그래서 integer도 String으로 변환해서 넘겨야한다.
    public void DeleteRow(String tableName, String[] whereColumnNames, String[] whereColumnCompareData)
    {
        if (mDatabase != null)
        {
            mDatabase.delete(tableName, whereColumnNames + " LIKE ?", whereColumnCompareData);
        }
        else {
            throw new AssertionError("데이터베이스가 아직 오픈 되지 않았습니다");
        }
    }

    public void DeleteRow(String tableName, String whereColumnNames, int whereColumnCompareIngerData)
    {
        if (mDatabase != null)
        {
            mDatabase.delete(tableName, whereColumnNames + " LIKE ?", new String[]{Integer.toString(whereColumnCompareIngerData)});
        }
        else {
            throw new AssertionError("데이터베이스가 아직 오픈 되지 않았습니다");
        }
    }

    public CursorWrapper SelectRow(String selectQueryStr)
    {
        if (mDatabase != null) {
            return new CursorWrapper( mDatabase.rawQuery(selectQueryStr, null) );
        }
        else
        {
            throw new AssertionError("데이터베이스가 아직 오픈 되지 않았습니다");
        }
    }

    public CursorWrapper SelectRowWithRowID(String tableName, long rowID)
    {
        if (mDatabase != null) {
            return new CursorWrapper( mDatabase.query(tableName, null,
                    "rowid = ?", new String[]{Long.toString(rowID)},
                    null, null, null) );
        }
        else
        {
            throw new AssertionError("데이터베이스가 아직 오픈 되지 않았습니다");
        }
    }
    // 사용법
    // Cursor resultCursor = SelectData("table명", new String[]{"mPrimaryKey", "name", "age", "height"},
    //                             new String[]{"age"}, new String[]{"12"},
    //                             "age", "height"
    //                            );
    //
    //  "table명"의 테이블에서 column "age"가 12이인 모든 data(row)에서 
    //  "mPrimaryKey", "name", "age", "height" 데이터를 가져오라. 
    //  그리고 같은 "age"끼리 grouping을 하고, "height" 순으로 ordering해라
    //
    //
    // selectColumnNames에 null 넣으면 모든 column 가져옴
    public CursorWrapper SelectRows(String tableName, String[] selectColumnNames,
                                    String[] whereColumnNames, String[] whereColumnCompareData,
                                    String groupByColumnName, String orderByColumName
    )
    {
        if (mDatabase != null)
        {
            return new CursorWrapper( mDatabase.query(tableName, selectColumnNames,
                    whereColumnNames == null ? null : GetWhereQuery(whereColumnNames), whereColumnCompareData,
                    groupByColumnName, null, orderByColumName) );
        }
        else {
            throw new AssertionError("데이터베이스가 아직 오픈 되지 않았습니다");
        }
    }


}
