package com.example.ryan.bcs421ryankanehwk5courseworkapp2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Created by Ryan on 3/29/2016.
 */
public class CourseWorkListActivity extends AppCompatActivity {

    //This method will retrieve the CourseWorkItem data from the CourseWorkItem fragment.
    public interface OnLVClick {
        CourseWorkItem OnLVClick();
    }

    OnLVClick lvObject;
    ContentResolver cr;
    Uri contentUri;
    //The following method is required since the bundled object cannot be passed to CourseWorkItemFragment via setArguments() as
    //the fragment is already active when the changes are to be made.
    public void sendData()
    {
        CourseWorkItemFragment itemFragment = (CourseWorkItemFragment)getFragmentManager().findFragmentById(R.id.FrameLayoutCourseWorkItem);
        Bundle cwObject = new Bundle();
        cwObject.putSerializable("cwObject", lvObject.OnLVClick()); //Get the CourseWorkItem from the list fragment and put it in the bundle.
        itemFragment.onLVLandSelected(cwObject); //Pass the bundled object to the CourseWorkItemFragment.
    }
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_work_list_activity);

        CourseWorkListFragment frag;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        frag = new CourseWorkListFragment();
        ft.add(R.id.FrameLayoutCourseWorkListContainer, frag);
        lvObject = (OnLVClick) frag;
        cr= getContentResolver();
        int rotation;
        rotation = getWindowManager().getDefaultDisplay().getRotation();

        //If the device is in portrait mode add the CourseWorkItem fragment
        if ((rotation == Surface.ROTATION_90) || (rotation == Surface.ROTATION_270))
        {
            ft.add(R.id.FrameLayoutCourseWorkItem, new CourseWorkItemFragment());
        }

        ft.commit();

            Button clearButton = (Button) findViewById(R.id.buttonClear);
            clearButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Context context = getApplicationContext();
                    AlertDialog.Builder checkDelete = new AlertDialog.Builder(CourseWorkListActivity.this);
                    checkDelete.setTitle("Confirm Deletion of Records");
                    checkDelete.setMessage("Are you sure you want to delete all records?");

                    //Set up the negative or "cancel" button
                    checkDelete.setNegativeButton("CANCEL", new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); //Close the dialog and do not delete any information.
                        }
                    });

                    checkDelete.setPositiveButton("OK", new DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface dialog, int which) {
                            deleteData(); //Delete the database and clear the listView.
                            dialog.dismiss(); //Close the dialog.
                        }
                    });
                    AlertDialog buildConfirmation = checkDelete.create();
                    buildConfirmation.show();
                }
            });
    }


    private void deleteData()
    {
        CourseWorkListFragment listFragment = (CourseWorkListFragment)getFragmentManager().findFragmentById(R.id.FrameLayoutCourseWorkListContainer);
        int numDeleted = listFragment.dataAdapter.getCount();
        cr.delete(listFragment.contentUri, null, null);
        listFragment.getLoaderManager().restartLoader(0, null, listFragment); //Swap the dataAdapter in the fragment in order to empty the listVIew
        listFragment.populateLV();


        CharSequence text = numDeleted + " records deleted!";
        int duration = Toast.LENGTH_SHORT;;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }

}
