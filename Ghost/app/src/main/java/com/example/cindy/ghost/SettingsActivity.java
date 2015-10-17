package com.example.cindy.ghost;

/** Cindy Schotman - 10595635 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsActivity extends Activity {

    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;

    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferenceSettings = getSharedPreferences("settings", MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        setLanguageButton();
        setNameViews();
        setListViews();
    }

    /** checks whether language changes in English or Dutch */
    public void setLanguageButton() {
        String getLanguage = preferenceSettings.getString("language", "Nothing Found");
        if(getLanguage.equals("Nederlands")) {
            Button DutchButton = (Button) findViewById(R.id.DutchButton);
            setDutchLanguage(DutchButton);
        } else {
            Button EnglishButton = (Button) findViewById(R.id.EnglishButton);
            setEnglishLanguage(EnglishButton);
        }
    }

    /** sets language to English and button to blue, while other button to gray */
    public void setEnglishLanguage(View view) {
        Button EnglishButton = (Button) findViewById(R.id.EnglishButton);
        Button DutchButton = (Button) findViewById(R.id.DutchButton);

        language = "English";
        EnglishButton.setBackgroundColor(Color.rgb(70, 255, 133));
        DutchButton.setBackgroundColor(Color.LTGRAY);

        preferenceEditor.putString("language", language);
        preferenceEditor.commit();
    }

    /** sets language to Dutch and button to blue, while other button to gray */
    public void setDutchLanguage(View view) {
        Button EnglishButton = (Button) findViewById(R.id.EnglishButton);
        Button DutchButton = (Button) findViewById(R.id.DutchButton);

        language = "Nederlands";
        EnglishButton.setBackgroundColor(Color.LTGRAY);
        DutchButton.setBackgroundColor(Color.rgb(70, 255, 133));

        preferenceEditor.putString("language", language);
        preferenceEditor.commit();
    }

    /** sets (last) used names as names in EditText of the two players */
    public void setNameViews() {
        String name_1 = preferenceSettings.getString("name_1", "Player 1");
        String name_2 = preferenceSettings.getString("name_2", "Player 2");

        EditText player1EditText = (EditText) findViewById(R.id.editTextPlayer1);
        player1EditText.setText(name_1);

        EditText player2EditText = (EditText) findViewById(R.id.editTextPlayer2);
        player2EditText.setText(name_2);
    }

    public void setListViews() {
        DBhelper db = new DBhelper(this);
        HashMap<String, Integer> playerHighScores = db.read();
        setListViewPlayer1(playerHighScores);
        setListViewPlayer2(playerHighScores);
    }

    /** sets listView of names for player 1 */
    public void setListViewPlayer1(HashMap<String, Integer> playerHighScores) {
        List<String> listOfPlayerNames = new ArrayList<>(playerHighScores.keySet());
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listOfPlayerNames);

        final ListView listViewNames1 = (ListView) findViewById(R.id.listViewPlayer1);
        listViewNames1.setAdapter(adapter);
        /** Sets an on item click listener to set selected name as text of EditText */
        listViewNames1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String namePicked = "Player 1 selected " + String.valueOf(adapterView.getItemAtPosition(position));
                Toast.makeText(SettingsActivity.this, namePicked, Toast.LENGTH_SHORT).show();

                EditText player1EditText = (EditText) findViewById(R.id.editTextPlayer1);
                player1EditText.setText(adapterView.getItemAtPosition(position).toString());
                listViewNames1.setVisibility(View.GONE);
            }
        });
    }

    /** sets listView of names for player 2 */
    public void setListViewPlayer2(HashMap<String, Integer> playerHighScores) {
        List<String> listOfPlayerNames = new ArrayList<>(playerHighScores.keySet());
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listOfPlayerNames);

        final ListView listViewNames2 = (ListView) findViewById(R.id.listViewPlayer2);
        listViewNames2.setAdapter(adapter);
        /** Sets an on item click listener to set selected name as text of EditText */
        listViewNames2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String namePicked = "Player 2 selected " + String.valueOf(adapterView.getItemAtPosition(position));
                Toast.makeText(SettingsActivity.this, namePicked, Toast.LENGTH_SHORT).show();

                EditText player2EditText = (EditText) findViewById(R.id.editTextPlayer2);
                player2EditText.setText(adapterView.getItemAtPosition(position).toString());
                listViewNames2.setVisibility(View.GONE);
            }
        });
    }

    /** changes visibility of listView for player 1 */
    public void visibilityListNames1(View view) {
        ListView listViewNames1 = (ListView) findViewById(R.id.listViewPlayer1);
        if(listViewNames1.getVisibility() == View.VISIBLE) {
            listViewNames1.setVisibility(View.GONE);
        } else {
            listViewNames1.setVisibility(View.VISIBLE);
        }
    }

    /** changes visibility of listView for player 2 */
    public void visibilityListNames2(View view) {
        ListView listViewNames2 = (ListView) findViewById(R.id.listViewPlayer2);
        if(listViewNames2.getVisibility() == View.VISIBLE) {
            listViewNames2.setVisibility(View.GONE);
        } else {
            listViewNames2.setVisibility(View.VISIBLE);
        }
    }

    /** saves names for players and sets and starts intent to HelpActivity */
    public void toHelpActivity(View view) {
        saveNames();
        Intent toHelp = new Intent(this, HelpActivity.class);
        startActivity(toHelp);
    }

    /** saves names for players and sets and starts intent to GhostGameActivity */
    public void toGhostGameActivity(View view) {
        saveNames();
        Intent toGhostGame = new Intent(this, GhostGameActivity.class);
        startActivity(toGhostGame);
    }

    /** saves the names for player 1 and player 2 */
    public void saveNames() {
        EditText editTextPlayer1 = (EditText) findViewById(R.id.editTextPlayer1);
        String name_player1 = String.valueOf(editTextPlayer1.getText());

        EditText editTextPlayer2 = (EditText) findViewById(R.id.editTextPlayer2);
        String name_player2 = String.valueOf(editTextPlayer2.getText());

        preferenceEditor.putString("name_1", name_player1);
        preferenceEditor.putString("name_2", name_player2);
        preferenceEditor.commit();
    }
}