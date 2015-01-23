package de.example.ankura.just_checking.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


/**
 * Created by ankura on 1/20/2015.
 */
public class ContactInfoTable {

    // Table structure to handle data
    public static final String TABLE_CONTACT_INFO = "contactInfo";
    public static final String COLUMN_ID = "_id";
    // name of contact
    public static final String CONTACT_NAME = "name";
    // Phone Contact List ID
    public static final String CONTACT_ID = "phoneID";
    // time last checked up on the contact
    public static final String CONTACT_TIME = "time";
    // latest known location received from contact
    public static final String CONTACT_LOCATION = "location";
    // path to latest received video from contact
    public static final String CONTACT_VIDEO = "video";

    // SQL Table creating string
    private static final String DB_CREATE =
            " create table "
                    + TABLE_CONTACT_INFO
                    + "("
                    + COLUMN_ID + " integer primary key autoincrement, "
                    + CONTACT_NAME + " text not null, "
                    + CONTACT_ID + " text, "
                    + CONTACT_TIME + " text, "
                    + CONTACT_LOCATION + " text, "
                    + CONTACT_VIDEO + " text);";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DB_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(ContactInfoTable.class.getName(), "Upgrading database from version " +
                oldVersion + " to " + newVersion + ". This will erase all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT_INFO);
        onCreate(database);
    }
}
