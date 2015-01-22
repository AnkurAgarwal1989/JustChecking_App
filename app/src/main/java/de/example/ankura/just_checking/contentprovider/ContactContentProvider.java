package de.example.ankura.just_checking.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;

import de.example.ankura.just_checking.database.ContactInfoDatabaseHelper;
import de.example.ankura.just_checking.database.ContactInfoTable;

/**
 * Created by ankura on 1/20/2015.
 */
public class ContactContentProvider extends ContentProvider {

    private ContactInfoDatabaseHelper database;

    //authority: symbolic name of provider
    private static final String AUTHORITY = "de.example.ankura.just_checking.contentprovider";

    //table_path: name of table
    private static final String BASE_PATH = "contactInfo";

    private static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/contactInfo";
    private static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "contactInfo";

    //For URI Matching
    public static final int COLUMN_ID = 1;
    public static final int CONTACT_NAME = 2;
    public static final int CONTACT_TIME = 3;
    public static final int CONTACT_LOCATION = 4;
    public static final int CONTACT_VIDEO = 5;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", COLUMN_ID);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#/name", CONTACT_NAME);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#/time", CONTACT_TIME);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#/location", CONTACT_LOCATION);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#/video", CONTACT_VIDEO);
    }

    @Override
    public boolean onCreate() {
        database = new ContactInfoDatabaseHelper(getContext());
        return false;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

}
}
}
