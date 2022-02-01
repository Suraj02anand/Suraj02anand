package com.example.memoapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBAdapter extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbMemos";
    public static String TABLE_NAME = "memo";
    public static String COLUMN_ID = "_id";
    public static String COLUMN_TITLE = "title";
    public static String COLUMN_CONTENT = "content";
    public static String COLUMN_DATE = "date";


    public DBAdapter(Context context) {
        super(context, "dbTest", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE "+TABLE_NAME+" ( "+COLUMN_ID+" integer primary key autoincrement, "+COLUMN_TITLE+" text , "+COLUMN_CONTENT+" text ,"+COLUMN_DATE+" text)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    ArrayList<MainModel> getAllMemo(){
        ArrayList<MainModel> arrayList = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_NAME+" order by "+COLUMN_ID+" desc";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String title = cursor.getString(1);
                String content = cursor.getString(2);
                String date = cursor.getString(3);
                arrayList.add(new MainModel(id,title,content,date));
            }while(cursor.moveToNext());
        }
        return arrayList;
    }

    void addMemo(MainModel mainModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE,mainModel.getTitle());
        contentValues.put(COLUMN_CONTENT,mainModel.getContent());
        contentValues.put(COLUMN_DATE,mainModel.getDate());
        this.getWritableDatabase().insert(TABLE_NAME,null,contentValues);
    }

    void updateMemo(MainModel mainModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE,mainModel.getTitle());
        contentValues.put(COLUMN_CONTENT,mainModel.getContent());
        contentValues.put(COLUMN_DATE,mainModel.getDate());
        this.getWritableDatabase().update(TABLE_NAME,contentValues,COLUMN_ID +" = "+mainModel.getId(),null);
    }

    void deleteMemo(MainModel mainModel){
        this.getWritableDatabase().delete(TABLE_NAME,COLUMN_ID+" = "+mainModel.getId(),null);
    }

}
