package com.masudias.sqlitedbreadwriteupdate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.masudias.sqlitedbreadwriteupdate.domain.User;
import com.masudias.sqlitedbreadwriteupdate.util.Constants;

public class DataHelper {

    private static final int DATABASE_VERSION = 1;

    private final Context context;
    private static DataHelper instance = null;
    private static DataBaseOpenHelper dOpenHelper;

    private DataHelper(Context context) {
        this.context = context.getApplicationContext();
    }

    public static synchronized DataHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DataHelper(context);
            dOpenHelper = new DataBaseOpenHelper(context, DBConstants.DB_INOVACE,
                    DATABASE_VERSION);
        }
        return instance;
    }

    public void closeDbOpenHelper() {
        if (dOpenHelper != null) dOpenHelper.close();
        instance = null;
    }

    public long insertEmployee(User user) {

        long rowIdOfSavedEmployee = -1;

        if (user != null) {
            SQLiteDatabase db = dOpenHelper.getWritableDatabase();
            db.beginTransaction();

            try {
                ContentValues values = new ContentValues();
                values.put(DBConstants.KEY_USER_NAME, user.name);
                values.put(DBConstants.KEY_CREATED_AT, user.createdAt);
                rowIdOfSavedEmployee = db.insertWithOnConflict(DBConstants.DB_TABLE_USER, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            } catch (Exception e) {
                e.printStackTrace();
            }

            db.setTransactionSuccessful();
            db.endTransaction();

            context.getContentResolver().notifyChange(DBConstants.DB_TABLE_USER_URI, null);

            Log.d(Constants.ApplicationTag, "Inserted user into the user table.");
        }

        return rowIdOfSavedEmployee;
    }

    public Cursor getAllUsers() {
        Cursor cursor = null;
        SQLiteDatabase db = dOpenHelper.getReadableDatabase();

        try {
            String queryString = "SELECT * FROM " + DBConstants.DB_TABLE_USER;
            cursor = db.rawQuery(queryString, null);
            if (cursor != null) cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cursor;
    }

    public void deleteUser(int id) {
        Cursor cursor = null;
        SQLiteDatabase db = dOpenHelper.getWritableDatabase();

        try {
            String queryString = "DELETE FROM " + DBConstants.DB_TABLE_USER
                    + " WHERE " + DBConstants.KEY_ID + " = " + id;
            cursor = db.rawQuery(queryString, null);
            if (cursor != null) cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        context.getContentResolver().notifyChange(DBConstants.DB_TABLE_USER_URI, null);

        Log.d(Constants.ApplicationTag, "Deleted user from the user table with user id: " + id);
    }

    public void updateUserName(int id, String userName) {
        Cursor cursor = null;
        SQLiteDatabase db = dOpenHelper.getWritableDatabase();

        try {
            String queryString = "UPDATE " + DBConstants.DB_TABLE_USER
                    + " SET " + DBConstants.KEY_USER_NAME + " = " + userName
                    + " WHERE " + DBConstants.KEY_ID + " = " + id;
            cursor = db.rawQuery(queryString, null);
            if (cursor != null) cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        context.getContentResolver().notifyChange(DBConstants.DB_TABLE_USER_URI, null);

        Log.d(Constants.ApplicationTag, "Updated user name into the user table for user id: " + id);
    }
}