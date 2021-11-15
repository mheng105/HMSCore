package com.example.arithmeticgame;

import androidx.annotation.Nullable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.jar.Attributes;


public class DbHelper extends SQLiteOpenHelper {
    final static String TableName="Name";
    final static int dbVersion=2;
    final static String dbName="name.db";
    final static String KeyID="ID";
    final static String KeyName="Nickname";
    final static String KeyScore="Score";
    final static String KeyTime="Time";
    final static String KeyLevel="Level";

    public DbHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE "+TableName+"( "+KeyID+" Integer Primary Key AutoIncrement,"+KeyName+" Text,"
                +KeyScore+" Integer,"+KeyTime+" Integer"+")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion<2){
        String sql="ALTER TABLE "+TableName+" ADD COLUMN "+KeyLevel+" INTEGER;";
        db.execSQL(sql);}
        //onCreate(db);
    }

    public int add(BangXH xh ){
        int result=-1;
        int check=0;
        SQLiteDatabase database=this.getWritableDatabase();
        /*ArrayList<BangXH> list=this.getAllList();
        if(list.size()!=0){
            for(BangXH xh1:list){
                if(xh1.getNickname().compareTo(xh.getNickname())==0){
                    check++;
                }
            }
        }*/
        ContentValues contextValues=new ContentValues();
        contextValues.put(KeyName,xh.getNickname());
        contextValues.put(KeyScore,xh.getScore());
        contextValues.put(KeyTime,xh.getTime());
        contextValues.put(KeyLevel,xh.getLevel());
        //if(check==0){
            result=(int) database.insert(TableName,null,contextValues);
        //}else result=database.update(TableName,contextValues,KeyName+ "=?",new String[]{xh.getNickname()});

        database.close();
        return result;
    }

    public ArrayList<BangXH> getAllList() {
        ArrayList<BangXH> result=new ArrayList<>();
        SQLiteDatabase database=this.getReadableDatabase();
        //Cursor cursor=database.rawQuery("select "+KeyID+","+KeyName+","+KeyScore+","+KeyTime+" from "+TableName+" order by "+KeyScore+" DESC,"+KeyTime+" ASC LIMIT 3",null);
        Cursor cursor= database.rawQuery("select "+KeyID+","+KeyName+","+KeyScore+","+KeyTime+","+KeyLevel+" from "+TableName+" where "+KeyLevel+" = 1"+" order by "+KeyScore+" DESC,"+KeyTime+" ASC LIMIT 3",null);
        if(cursor!=null) {
            cursor.moveToFirst();
             do {
                String Nickname = cursor.getString(1);
                int Score = cursor.getInt(2);
                int Time = cursor.getInt(3);
                int level = cursor.getInt(4);
                result.add(new BangXH(Nickname, Score, Time, level));
                cursor.moveToNext();
            }while (!cursor.isAfterLast());

            cursor.close();
        }
        database.close();
        return result;
    }

    public BangXH getDiem(){
        SQLiteDatabase database=this.getReadableDatabase();
        BangXH xh=null;
        Cursor cursor=database.rawQuery("SELECT * FROM "+TableName,null);
        cursor.moveToFirst();
        xh=new BangXH(cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4));
        cursor.close();
        database.close();
        return xh;
    }

    public int getCount(){
        String countQuery="SELECT * FROM "+TableName;
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor=database.rawQuery(countQuery,null);
        int count=cursor.getCount();
        cursor.close();
        database.close();
        return count;
    }

    public void delete(){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(TableName,null,null);
        database.close();
    }
}