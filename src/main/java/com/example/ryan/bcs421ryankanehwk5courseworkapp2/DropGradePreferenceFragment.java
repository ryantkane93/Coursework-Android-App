package com.example.ryan.bcs421ryankanehwk5courseworkapp2;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Ryan on 4/26/2016.
 */
public class DropGradePreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.grade_preference_screen);

    }
}