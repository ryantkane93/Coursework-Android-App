package com.example.ryan.bcs421ryankanehwk5courseworkapp2;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Ryan on 3/29/2016.
 */
public class CourseWorkListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,CourseWorkListActivity.OnLVClick{

    CourseWorkItem landscapeItem;
    SimpleCursorAdapter dataAdapter;
    ListView lv;
    ContentResolver cr;
    Uri contentUri;

    @Override
    public CourseWorkItem OnLVClick(){
        return landscapeItem;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.course_work_list_fragment, parent, false);

        cr= getActivity().getContentResolver();
        contentUri = Uri.parse("content://" + AssignmentDBContract.AUTHORITY + "/" + AssignmentDBContract.PATH);
        getLoaderManager().initLoader(0, null, this);
        lv = (ListView) rootView.findViewById(R.id.listViewAssignmentNames);
        populateLV();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long
                    id) {
                int orientation = getResources().getConfiguration().orientation;
                /*Query the database and return the DataCursor. Use the DataCursor to
                extract the record whose ID value is equal to the position. Place each
                column value into a new CourseWorkItem object so the intial method of
                passing data is not disturbed.*/
                Cursor dataCursor = cr.query(contentUri, null, AssignmentDBContract.KEY_ASSIGNMENTS_ID_COLUMN + "= ?", new String[]{String.valueOf(id)}, null);
                dataCursor.moveToFirst();
                CourseWorkItem dbItem = new CourseWorkItem(dataCursor.getString(AssignmentDBContract.NAME_ROW),dataCursor.getString(AssignmentDBContract.CATEGORY_ROW), (int) (dataCursor.getFloat(AssignmentDBContract.GRADE_ROW)));

                if (orientation == Configuration.ORIENTATION_LANDSCAPE) { //Landscape Mode
                     landscapeItem = dbItem; //QUERY DATABASE, REASSEMBLE THE CLASS OBJECT AND SET IT EQUAL TO LANDSCAPEITEM
                    ((CourseWorkListActivity) getActivity()).sendData(); //Send the data to the CourseWorkItem fragment,
                }
                else { //Portrait mode
                    // Create an intent to start the other activity
                    Intent intent = new Intent(getActivity(), CourseWorkItemActivity.class);
                    intent.putExtra("id", dbItem); //Pass the serializable object to the intent
                    // Run the activity
                    startActivity(intent);

                }
            }

        });
        return rootView;
    }


    public void populateLV(){

        dataAdapter = new SimpleCursorAdapter(this.getContext(),
                R.layout.list_view_assignment_names,
                null,
                new String[] { AssignmentDBContract.KEY_ASSIGNMENTS_NAME_COLUMN},
                new int[] { R.id.textViewLVName },
                0);

        lv.setAdapter(dataAdapter);
        }



    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        return new CursorLoader(getActivity(), AssignmentDBContract.CONTENT_URI, new String[]{AssignmentDBContract.KEY_ASSIGNMENTS_ID_COLUMN, AssignmentDBContract.KEY_ASSIGNMENTS_NAME_COLUMN}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        dataAdapter.swapCursor(arg1);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        dataAdapter.swapCursor(null); //Empty the loader so that the listView is empty. This is invoked by the CourseWorkListActivity after deleting the data in the database.
    }

}
