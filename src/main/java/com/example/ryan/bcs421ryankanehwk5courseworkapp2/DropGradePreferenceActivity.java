package com.example.ryan.bcs421ryankanehwk5courseworkapp2;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Ryan on 4/26/2016.
 */
public class DropGradePreferenceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new DropGradePreferenceFragment())
                .commit();
    }

}
