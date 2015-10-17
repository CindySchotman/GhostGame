package com.example.cindy.ghost;

/** Cindy Schotman - 10595635 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class startActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        /** When there is an unfinished game, continue it in GhostGameActivity.
          * Otherwise start a new one in SettingsActivity */
        SharedPreferences gamePreferences = getPreferences(MODE_PRIVATE);
        Boolean newGame = gamePreferences.getBoolean("ended", true);
        if(!newGame) {
            Intent toGhostGame = new Intent(this, GhostGameActivity.class);
            startActivity(toGhostGame);
        } else {
            Intent toSettings = new Intent(this, SettingsActivity.class);
            startActivity(toSettings);
        }
    }
}
