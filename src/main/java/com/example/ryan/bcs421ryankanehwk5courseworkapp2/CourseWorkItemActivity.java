package com.example.ryan.bcs421ryankanehwk5courseworkapp2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ryan on 3/29/2016.
 */
public class CourseWorkItemActivity extends AppCompatActivity {
    CourseWorkItem received;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_work_item_activity);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        CourseWorkItemFragment frag = new CourseWorkItemFragment();
        ft.add(R.id.frameLayoutCourseItem, frag);
        ft.commit();

        Bundle extra = getIntent().getExtras();
        if(extra != null)
        {
            received = (CourseWorkItem) getIntent().getSerializableExtra("id");
        }
        Bundle sendData = new Bundle();
        sendData.putSerializable("courseWorkItem",received); //Put the class object in a bundle.
        frag.setArguments(sendData); //Send the bundled object to the COurseWorkItemFragment via the setArguments method
    }
}
