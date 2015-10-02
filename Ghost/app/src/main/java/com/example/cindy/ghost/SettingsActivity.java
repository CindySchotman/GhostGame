package com.example.cindy.ghost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends Activity {

    private Intent toGhostGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toGhostGame = new Intent(this, GhostGameActivity.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void toGhostGameActivity(View view) {

        EditText player1EditText = (EditText) findViewById(R.id.editTextPlayer1);
        EditText player2EditText = (EditText) findViewById(R.id.editTextPlayer2);
        String player1_name = String.valueOf(player1EditText.getText());
        String player2_name = String.valueOf(player2EditText.getText());



        toGhostGame.putExtra("player1", player1_name);
        toGhostGame.putExtra("player2", player2_name);
        toGhostGame.putExtra("language", "English");
        startActivity(toGhostGame);
    }

}
