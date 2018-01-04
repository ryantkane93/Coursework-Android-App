package com.example.ryan.bcs421ryankanehwk5courseworkapp2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Ryan on 4/23/2016.
 */
public class AssignmentContentProvider extends ContentProvider {

    AssignmentDBOpenHelper dbHelper;

    public static final Uri CONTENT_URI = AssignmentDBContract.CONTENT_URI;
    @Override
    public boolean onCreate() {
        dbHelper = new AssignmentDBOpenHelper(this.getContext(),AssignmentDBContract.DATABASE_NAME, null, 1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor resultCursor;
        String[] resultColumns = new String[]{AssignmentDBContract.KEY_ASSIGNMENTS_ID_COLUMN, AssignmentDBContract.KEY_ASSIGNMENTS_NAME_COLUMN, AssignmentDBContract.KEY_ASSIGNMENTS_CATEGORY_COLUMN, AssignmentDBContract.KEY_ASSIGNMENTS_GRADE_COLUMN};
        // Get the database instance. This will create/update the db as necessary.
        resultCursor = dbHelper.getWritableDatabase().query(AssignmentDBContract.DATABASE_TABLE,
                resultColumns, selection, selectionArgs, null, null, null);

        return resultCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id= dbHelper.getWritableDatabase().insert(AssignmentDBContract.DATABASE_TABLE, null, values);
        if (id >0)
        {
            ContentUris.withAppendedId(uri, id);
            getContext().getContentResolver().notifyChange(CONTENT_URI, null);
            return CONTENT_URI;
        }
        else{
            throw new SQLException("Failed to add a record: " + uri);
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        dbHelper.getWritableDatabase().execSQL("delete from " + AssignmentDBContract.DATABASE_TABLE);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
