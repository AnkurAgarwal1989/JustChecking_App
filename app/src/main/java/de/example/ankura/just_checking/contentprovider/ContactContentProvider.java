package de.example.ankura.just_checking.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import de.example.ankura.just_checking.database.ContactInfo;
import de.example.ankura.just_checking.database.ContactInfoDatabaseHelper;
import de.example.ankura.just_checking.database.ContactInfoTable;

/**
 * Created by ankura on 1/20/2015.
 */
public class ContactContentProvider extends ContentProvider {

    private ContactInfoDatabaseHelper dbHelper;

    //authority: symbolic name of provider
    private static final String AUTHORITY = "de.example.ankura.just_checking.contentprovider";

    //table_path: name of table
    private static final String CONTACTINFO_TABLE = "contactInfo";

    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTACTINFO_TABLE);

    //For URI Matching
    public static final int CONTACTS = 1;
    public static final int CONTACT_ID = 2;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, CONTACTINFO_TABLE, CONTACTS);
        sURIMatcher.addURI(AUTHORITY, CONTACTINFO_TABLE + "/#", CONTACT_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new ContactInfoDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);

        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();

        // auto generated row ID or -1 if error
        long autoID = 0;
        switch (uriType) {
            case CONTACTS:
                autoID = sqlDB.insert(ContactInfoTable.TABLE_CONTACT_INFO, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(CONTACTINFO_TABLE + "/" + autoID);
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase sqlDB = dbHelper.getReadableDatabase();
        SQLiteQueryBuilder sqlQuery = new SQLiteQueryBuilder();
        sqlQuery.setTables(ContactInfoTable.TABLE_CONTACT_INFO);

        int uriType = sURIMatcher.match(uri);

        // Constructing query after matching URIs
        switch (uriType) {
            case CONTACT_ID:
                sqlQuery.appendWhere(ContactInfoTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            case CONTACTS:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        // Executing query on db
        Cursor cursor = sqlQuery.query(sqlDB, projection, selection, selectionArgs, null, null, sortOrder);
        // Notify content resolver of operattion
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        //return cursor containing results of the query
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();

        int rowsDeleted = 0;

        switch (uriType) {
            case CONTACTS:
                rowsDeleted = sqlDB.delete(ContactInfoTable.TABLE_CONTACT_INFO, selection, selectionArgs);
                break;
            case CONTACT_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(ContactInfoTable.TABLE_CONTACT_INFO,
                            ContactInfoTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(ContactInfoTable.TABLE_CONTACT_INFO,
                            ContactInfoTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();

        int rowsUpdated = 0;

        switch (uriType) {
            case CONTACTS:
                rowsUpdated = sqlDB.update(ContactInfoTable.TABLE_CONTACT_INFO, values, selection, selectionArgs);
                break;
            case CONTACT_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(ContactInfoTable.TABLE_CONTACT_INFO, values,
                            ContactInfoTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = sqlDB.update(ContactInfoTable.TABLE_CONTACT_INFO, values,
                            ContactInfoTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
