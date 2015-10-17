package com.example.cindy.ghost;

/** Cindy Schotman - 10595635 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    /** sets and starts intent to GhostGameActivity */
    public void toGhostGameActivity(View view) {
        Intent toGhostGame = new Intent(this, GhostGameActivity.class);
        startActivity(toGhostGame);
    }

    /** sets and starts intent to HighScoreActivity */
    public void toHighScoreActivity(View view) {
        Intent toHighScore = new Intent(this, HighScoreActivity.class);
        startActivity(toHighScore);
    }

    /** sets game on ended and sets and starts intent to SettingsActivity */
    public void newGame(View view) {
        SharedPreferences preferenceSettings = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor preferenceEditor = preferenceSettings.edit();
        preferenceEditor.putBoolean("ended", true);
        preferenceEditor.commit();

        Intent toSettings = new Intent(this, SettingsActivity.class);
        startActivity(toSettings);
    }

    /** sets and starts intent to HelpActivity */
    public void toHelpActivity(View view) {
        Intent toHelp = new Intent(this, HelpActivity.class);
        startActivity(toHelp);
    }
}
