package de.example.ankura.just_checking.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ankura on 1/20/2015.
 */
public class ContactInfoDatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "ContactInfoTable.db";
    private static final int DATABASE_VERSION = 1;

    public ContactInfoDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        ContactInfoTable.onCreate(database);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        ContactInfoTable.onUpgrade(database, oldVersion, newVersion);
    }
}
