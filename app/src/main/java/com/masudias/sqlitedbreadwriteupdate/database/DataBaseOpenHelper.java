package com.masudias.sqlitedbreadwriteupdate.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DataBaseOpenHelper extends SQLiteOpenHelper {

    private final int newVersion;
    private final String name;

    public DataBaseOpenHelper(Context context, String name, int version) {
        super(context, name, null, version);
        this.newVersion = version;
        this.name = name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createEmployeeTable(db);
    }

    private void createEmployeeTable(SQLiteDatabase db) {
        db.execSQL("create table if not exists "
                + DBConstants.DB_TABLE_USER + "("
                + DBConstants.KEY_ID + " integer primary key autoincrement, "
                + DBConstants.KEY_USER_NAME + " text not null, "
                + DBConstants.KEY_CREATED_AT + " long not null"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The last case will contain the break statement only. As the migration will take place one by one.
        // Here's a nice explanation - http://stackoverflow.com/a/26916986/3145960
    }
}