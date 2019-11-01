package com.example.sqlite1

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context : Context) : SQLiteOpenHelper(context, "Test.db", null, 1) {

    override fun onCreate(p0: SQLiteDatabase?) {
        Log.d("msg", "on create")

        // 쿼리문 저장
        var sql = "create table TestTable (" +
                "idx integer primary key autoincrement, " +
                "textData text not null, " +
                "intData integer not null, " +
                "floatData real not null, " +
                "dateData date not null" +
                ")"

        // 쿼리문 실행
        p0?.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("msg", "oldVersion : ${oldVersion}, newVersion : ${newVersion}")
    }
}