package com.example.ryan.bcs421ryankanehwk5courseworkapp2;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    TextView gradePercentage;
    TextView gradeLetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.registerOnSharedPreferenceChangeListener(this);

        gradePercentage = (TextView) findViewById(R.id.textViewNumberGrade);
        gradeLetter = (TextView) findViewById(R.id.textViewLetterGrade);

        setGradeText();
        ImageButton listImageButton = (ImageButton) findViewById(R.id.imageButtonCourseWorkList);
        listImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CourseWorkListActivity.class);

                startActivity(intent);
            }
        });

        ImageButton addImageButton = (ImageButton) findViewById(R.id.imageButtonAddCourseWorkItem);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCourseWorkItemActivity.class);

                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, DropGradePreferenceActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public double calculateGrade(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        /*Get the category and grade column from the database. Create an arrayList for each category and store all corresponding
        grades inside of it. If a particular category is to be dropped, sort the array list and delete the first (lowest value).
        Count the values in each of the array list and calculate an average. Plug these average into the final grade formula.
        Round the final grade to two decimal places so that it is formatted for the user.*/

        //Query the database for all of the assignment categories and grades.
        ContentResolver cr= getContentResolver();
        Uri contentUri = Uri.parse("content://" + AssignmentDBContract.AUTHORITY + "/" + AssignmentDBContract.PATH);
        Cursor assignmentCursor = cr.query(contentUri, new String[]{AssignmentDBContract.KEY_ASSIGNMENTS_CATEGORY_COLUMN, AssignmentDBContract.KEY_ASSIGNMENTS_GRADE_COLUMN},null,null, null);


        //Process the data in the cursor and assign each grade to its corresponding array.

        //assignmentCursor.moveToFirst(); //Make the the cursor is at the beginning of the queried data.

        ArrayList<Float> examArray = new ArrayList<Float>();
        ArrayList<Float> homeworkArray = new ArrayList<Float>();
        ArrayList<Float> quizArray = new ArrayList<Float>();
        ArrayList<Float> labArray = new ArrayList<Float>();

        while(assignmentCursor.moveToNext()) //While their is data in the cursor...
        {
            Log.d("DEBUG HELP", assignmentCursor.getString(1));

            if(assignmentCursor.getString(2).equals("Exam")) //If the grade is an exam...
            {
                examArray.add(new Float(assignmentCursor.getFloat(3)));//Add the grade to its arraylist
            }
            else if(assignmentCursor.getString(2).equals("Homework")) //If the grade is a homework...
            {
                homeworkArray.add(new Float(assignmentCursor.getFloat(3))); //Add the grade to its arraylist
            }
            else if(assignmentCursor.getString(2).equals("Quiz")) //If the grade is a quiz...
            {
                quizArray.add(new Float(assignmentCursor.getFloat(3))); //Add the grade to its arraylist
            }
            else //The assignment must be a lab..
            {
                labArray.add(new Float(assignmentCursor.getFloat(3))); //Add the grade to its arraylist
            }
        }

        /*With each grade from each category in its own array list, now check if the user wants to drop that
        category. If this is the case, sort the list in ascending order and then delete the first index.
        Check that there are at least two grades in the category before dropping. This will avoid dropping
        the only grade for a category and assigning a zero for said category.
         */
        boolean dropExam = sp.getBoolean("drop_lowest_exam", false);
        if(dropExam) //If the drop lowest exam grade checkbox is checked...
        {
            if(examArray.size() >= 2) {
                Collections.sort(examArray); //Sort the exams.
                examArray.remove(0); //Remove the first index as it is the smallest.
                examArray.trimToSize(); //Adjust the length of the arrayList since an item was removed.
            }
        }

        boolean dropHomework = sp.getBoolean("drop_lowest_homework", false);
        if(dropHomework) //If the drop lowest homework grade checkbox is checked...
        {
            if(homeworkArray.size() >=2) {
                Collections.sort(homeworkArray); //Sort the homework grades.
                homeworkArray.remove(0); //Remove the first index as it is the smallest.
                homeworkArray.trimToSize(); //Adjust the length of the arrayList since an item was removed.
            }
        }

        boolean dropQuiz = sp.getBoolean("drop_lowest_quiz", false);
        if(dropQuiz) //If the drop lowest quiz grade checkbox is checked...
        {
            if(quizArray.size() >= 2) {
                Collections.sort(quizArray); //Sort the quiz grades.
                quizArray.remove(0); //Remove the first index as it is the smallest.
                quizArray.trimToSize(); //Adjust the length of the arrayList since an item was removed.
            }
        }
        boolean dropLab = sp.getBoolean("drop_lowest_lab", false);
        if(dropLab) //If the drop lowest lab grade if the checkbox is checked...
        {
            if(labArray.size() >= 2) {
                Collections.sort(labArray); //Sort the lab grades.
                labArray.remove(0); //Remove the first index as it is the smallest.
                labArray.trimToSize(); //Adjust the length of the arrayList since an item was removed.
            }
        }

        /*With the lowest grades dropped, find the sum of each arrayList and divide it by its size.
        Multiple each value with their respective percentage total for the class.*/

        double average = Double.parseDouble(getArrayAverage(examArray).toString()) * .50 + Double.parseDouble(getArrayAverage(homeworkArray).toString()) * .35 + Double.parseDouble(getArrayAverage(quizArray).toString()) * .10 + Double.parseDouble(getArrayAverage(labArray).toString()) * .05;
        return average;
    }

    public Float getArrayAverage(ArrayList<Float> grades)
    {
        Float sum = 0.0f;
        for(int i =0; i<grades.size(); i++)
        {
            sum += grades.get(i);
        }

        return (sum/grades.size());
    }

    public String getLetterGrade(double numGrade)
    {
        //Round the double value to the nearest whole number before matching to a letter grade.
        int roundedAverage = (int) Math.round(numGrade);

        //Check the average value against the grading scale to determine the letter grade.
        String letterGrade;
        if (roundedAverage <= 100 && roundedAverage >= 97) {
            letterGrade = "A+";
        } else if (roundedAverage <= 96 && roundedAverage >= 93) {
            letterGrade = "A";
        } else if (roundedAverage <= 92 && roundedAverage >= 90) {
            letterGrade = "A-";
        } else if (roundedAverage <= 89 && roundedAverage >= 87) {
            letterGrade = "B+";
        } else if (roundedAverage <= 86 && roundedAverage >= 83) {
            letterGrade = "B";
        } else if (roundedAverage <= 82 && roundedAverage >= 80) {
            letterGrade = "B-";
        } else if (roundedAverage <= 79 && roundedAverage >= 77) {
            letterGrade = "C+";
        } else if (roundedAverage <= 76 && roundedAverage >= 73) {
            letterGrade = "C";
        } else if (roundedAverage <= 72 && roundedAverage >= 70) {
            letterGrade = "C-";
        } else if (roundedAverage <= 69 && roundedAverage >= 65) {
            letterGrade = "D";
        } else {
            letterGrade = "F";
        }

        return letterGrade;

    }

    public void setGradeText()
    {
        String formattedAvg = String.format("%.2f", calculateGrade());
        if(formattedAvg.equals("NaN")) //When there is no values entered, the calculateGrade function evaluates to NaN. Remove this and replace it with a zero.
        {
            formattedAvg = "Undefined";
            gradePercentage.setText(formattedAvg);
            gradeLetter.setText(formattedAvg);
        }
        else {
            gradePercentage.setText(formattedAvg + "%");
            gradeLetter.setText(getLetterGrade(calculateGrade()));
        }

    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {

        setGradeText();
    }

    @Override
    public void onResume() {
    super.onResume();
        setGradeText();
    }
}
