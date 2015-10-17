package com.example.cindy.ghost;

/** Cindy Schotman - 10595635 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HighScoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        /** gets names and high scores from the database */
        DBhelper db = new DBhelper(this);
        HashMap<String, Integer> playerHighScores = db.read();
        List<String> listOfPlayerNames = new ArrayList<>(playerHighScores.keySet());
        List<Integer> listOfPlayerHighScores = new ArrayList<>(playerHighScores.values());

        /** puts names and high scores as one string per set in a list */
        List<String> list = new ArrayList<>();
        for(int i = 0; i < listOfPlayerNames.size(); i++) {
            list.add(listOfPlayerNames.get(i) + "         " + listOfPlayerHighScores.get(i));
        }

        /** sets the strings of the list as items in list view */
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, list);
        ListView listOfHighScores = (ListView) findViewById(R.id.listViewHighScores);
        listOfHighScores.setAdapter(adapter);
    }

    /** sets and starts intent to StartActivity */
    public void toStart(View view) {
        Intent toGhostGame = new Intent(this, GhostGameActivity.class);
        startActivity(toGhostGame);
    }
}
