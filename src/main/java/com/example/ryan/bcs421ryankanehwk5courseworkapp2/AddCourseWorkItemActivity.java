package com.example.ryan.bcs421ryankanehwk5courseworkapp2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Ryan on 3/29/2016.
 */
public class AddCourseWorkItemActivity extends AppCompatActivity {

    //This method will retrieve the CourseWorkItem data from the CourseWorkItem fragment.
    public interface OnAddClickListener {
        CourseWorkItem OnAddClick();
    }

    OnAddClickListener getCourseWorkData; //Declare an object of the interface that will communicate with the fragment.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course_work_item_activity);

        final FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        CourseWorkItemFragment frag  = new CourseWorkItemFragment();
        ft.add(R.id.FrameLayoutAddFragmentContainer, frag);
        ft.commit();

        getCourseWorkData = (OnAddClickListener) frag; //Link the fragment to the OnAddClickListener object


        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CourseWorkItem item = getCourseWorkData.OnAddClick();
                addToDB(item); //Use the content provider to send the newly-formed object to the database.
                //CourseWorkApplication.addItem(item); //Use the interface object to get the CourseWorkItem data and store it in the global array list.
                CharSequence text = item.getName() + " added";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });
    }

    private void addToDB(CourseWorkItem item)
    {
        ContentResolver cr = getContentResolver();
        Uri contentUri = Uri.parse("content://" + AssignmentDBContract.AUTHORITY + "/" + AssignmentDBContract.PATH);
        ContentValues rowData = new ContentValues();
        //Put each of the attributes from the EditTexts into the ContentValues object.
        rowData.put(AssignmentDBContract.KEY_ASSIGNMENTS_NAME_COLUMN, item.getName());
        rowData.put(AssignmentDBContract.KEY_ASSIGNMENTS_CATEGORY_COLUMN, item.getCategory());
        rowData.put(AssignmentDBContract.KEY_ASSIGNMENTS_GRADE_COLUMN, (float) item.getGrade());
        // Insert the new row's data into the database using the content provider
        cr.insert(contentUri,rowData);
    }
}

