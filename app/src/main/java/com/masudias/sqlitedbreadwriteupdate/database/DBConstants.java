package com.masudias.sqlitedbreadwriteupdate.database;

import android.net.Uri;

import com.masudias.sqlitedbreadwriteupdate.util.Constants;

public class DBConstants {

    public static final String TAG = "DataBaseOpenHelper";
    public static final String DB_PATH = "/data/data/" + Constants.ApplicationPackage + "/databases/";
    public static final String DB_NAME = "sqlitedboperation";
    public static final String DB_TABLE_USER = "my_user";
    public static final Uri DB_TABLE_USER_URI = Uri
            .parse("sqlite://" + Constants.ApplicationPackage + "/" + DB_TABLE_USER);

    // User table columns
    public static final String KEY_ID = "id";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_CREATED_AT = "created_at";
}