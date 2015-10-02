package com.example.cindy.ghost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GhostGameActivity extends Activity {

    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost_game);

        Intent toGhostGame = getIntent();
        String player1_name = toGhostGame.getStringExtra("player1_name");
        String player2_name = toGhostGame.getStringExtra("player2_name");
        String language = toGhostGame.getStringExtra("language");

        PlayersList players = new PlayersList();
        Player player1 = players.getPlayer(player1_name);
        Player player2 = players.getPlayer(player2_name);

        Context context = this;
        game = new Game(player1, player2, language, context);

        setPlayerInformation();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost_game, menu);
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

    private void setPlayerInformation() {
        // Player On Turn
        TextView namePlayerOnTurn = (TextView) findViewById(R.id.namePlayerOnTurn);
        namePlayerOnTurn.setText(game.onTurn().name);
        TextView scorePlayerOnTurn = (TextView) findViewById(R.id.scorePlayerOnTurn);
        scorePlayerOnTurn.setText(game.onTurn().score);
        TextView lettersPlayerOnTurn = (TextView) findViewById(R.id.lettersPlayerOnTurn);
        lettersPlayerOnTurn.setText(game.onTurn().letters);

        // Other Player
        TextView nameOtherPlayer = (TextView) findViewById(R.id.nameOtherPlayer);
        nameOtherPlayer.setText(game.onTurn().name);
        TextView scoreOtherPlayer = (TextView) findViewById(R.id.scoreOtherPlayer);
        scoreOtherPlayer.setText(game.onTurn().score);
        TextView lettersOtherPlayer = (TextView) findViewById(R.id.lettersOtherPlayer);
        lettersOtherPlayer.setText(game.onTurn().letters);

        // word
        TextView wordTextView = (TextView) findViewById(R.id.word);
        wordTextView.setText(game.word);
    }

    public void playTurn(View view) {
        EditText wordTextView = (EditText) findViewById(R.id.word);
        String inputWord = String.valueOf(wordTextView.getText());
        game.playTurn(inputWord);
        setPlayerInformation();
    }
}
