package com.example.ryan.bcs421ryankanehwk5courseworkapp2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ryan on 4/23/2016.
 */
public class AssignmentDBOpenHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_CREATE =
            "create table " + AssignmentDBContract.DATABASE_TABLE + " (" +
                    AssignmentDBContract.KEY_ASSIGNMENTS_ID_COLUMN + " integer primary key autoincrement, " +
                    AssignmentDBContract.KEY_ASSIGNMENTS_NAME_COLUMN + " text not null, " +
                    AssignmentDBContract.KEY_ASSIGNMENTS_CATEGORY_COLUMN + " text not null, " +
                    AssignmentDBContract.KEY_ASSIGNMENTS_GRADE_COLUMN + " float);";

    public AssignmentDBOpenHelper(Context context, String name,
                               SQLiteDatabase.CursorFactory factory, int version) {

        // Just calls the base class constructor
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE); //Creates the database create table statement above.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
