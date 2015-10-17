package com.example.cindy.ghost;

/** Cindy Schotman - 10595635 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WinnerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        /** gets name of winner and loser from intent from GhostGameActivity and sets text */
        Bundle fromGhostGame = getIntent().getExtras();
        String winner = fromGhostGame.getString("winner");
        String loser = fromGhostGame.getString("loser");

        TextView congrats = (TextView) findViewById(R.id.congrats);
        congrats.setText("Congratulations!\n" + winner + " won the game, because " + loser + " completed 'GHOST'.");
    }

    /** sets and starts intent to HighScoreActivity */
    public void toHighScore(View view) {
        Intent toHighScore = new Intent(this, HighScoreActivity.class);
        startActivity(toHighScore);
    }

    /** sets and starts intent to SettingsActivity */
    public void toSettings(View view) {
        Intent toSettings = new Intent(this, SettingsActivity.class);
        startActivity(toSettings);
    }
}
