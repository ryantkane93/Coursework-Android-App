package com.example.ryan.bcs421ryankanehwk5courseworkapp2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Ryan on 3/29/2016.
 */
public class CourseWorkItemFragment extends Fragment implements AddCourseWorkItemActivity.OnAddClickListener {
    ArrayAdapter <CharSequence> categoryAdapter;
    CourseWorkItem item; //Class object that may potentially be received from CourseWorkItemActivity
    //Declare each of the control variables so that they can be used in the interface listener as well.
    EditText name;
    Spinner spin;
    SeekBar gradeBar;
    TextView gradeView;

    //Method is called by the CourseWOrkListActivity after bundling the class object.
    public void onLVLandSelected(Bundle changes)
    {
        item = (CourseWorkItem)changes.getSerializable("cwObject");
        setControlValues();

    }
    //Sets the values of the controls based on the CourseWorkItem objects from activities.
    private void setControlValues()
    {
        //Set the controls using the data extracted from this object
        name.setText(item.getName()); //Set the edit text to the name of the object
        spin.setSelection(categoryAdapter.getPosition(item.getCategory())); //Set the spinner to the type of assignment the object contained.
        gradeBar.setProgress(item.getGrade()); //Set the progress bar to the grade value.
        gradeView.setText(item.getGrade() + "%");
    }

    //Override the interface function "OnAddClick" to pass the control data to the activity.
    @Override
    public CourseWorkItem OnAddClick() {
        CourseWorkItem newItem = new CourseWorkItem(name.getText().toString(), spin.getSelectedItem().toString(), gradeBar.getProgress());
        return newItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.course_work_item_fragment, parent, false);

       name = (EditText) rootView.findViewById(R.id.editTextName); //Declare the editText view so that it can be passed into a CourseWorkItem object.

                //Populate the spinner with the category choices
                spin = (Spinner) rootView.findViewById(R.id.spinnerCategories);
        categoryAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.categories_array, android.R.layout.simple_spinner_item);

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(categoryAdapter);

        //Update the seek bar's textview as the percentage changes
        gradeBar = (SeekBar) rootView.findViewById(R.id.seekBarGrade);

        //Declare the TextView so that it could be edited by an incoming bundle or the seekBar
        gradeView = (TextView) rootView.findViewById(R.id.textViewPercentage);
        //Check if an object was passed from CourseWorkItem activity so that the controls can be populated.

        Bundle arguments = getArguments();
        if(arguments != null) //If there were arguments in the bundle..
        {
            //Get the class object from CourseWorkListFragment and stores it in a variable
            item = (CourseWorkItem)arguments.getSerializable("courseWorkItem");
           setControlValues();

        }


        gradeView = (TextView) rootView.findViewById(R.id.textViewPercentage);
        SeekBar.OnSeekBarChangeListener customSeekBarListener = new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gradeView.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        gradeBar.setOnSeekBarChangeListener(customSeekBarListener); //Implement the custom listener so that the percentage in the textview adjusts dynamically.

        return rootView;
    }


}