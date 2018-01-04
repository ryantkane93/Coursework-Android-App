package com.example.ryan.bcs421ryankanehwk5courseworkapp2;

import android.net.Uri;

/**
 * Created by Ryan on 4/23/2016.
 */
public class AssignmentDBContract
{
    public static final String DATABASE_NAME = "myDatabase.db";
    public static final String DATABASE_TABLE = "assignmentsTable";
    public static final int DATABASE_VERSION = 1;
    public static final String KEY_ASSIGNMENTS_ID_COLUMN = "_id";
    public static final String KEY_ASSIGNMENTS_NAME_COLUMN = "Name";
    public static final String KEY_ASSIGNMENTS_CATEGORY_COLUMN = "Category";
    public static final String KEY_ASSIGNMENTS_GRADE_COLUMN = "Grade";
    public static final int ID_ROW = 0;
    public static final int NAME_ROW = 1;
    public static final int CATEGORY_ROW = 2;
    public static final int GRADE_ROW = 3;
    public static String AUTHORITY = "com.ryankane.assignmentcontentprovider";
    public static String PATH = "assignments";
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+PATH);
}
