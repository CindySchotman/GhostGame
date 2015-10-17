package com.example.cindy.ghost;

/** Cindy Schotman - 10595635 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HelpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    /** sets and starts intent to SettingsActivity */
    public void toSettingsActivity(View view) {
        Intent toSettingsActivity = new Intent(this, SettingsActivity.class);
        startActivity(toSettingsActivity);
    }
}
