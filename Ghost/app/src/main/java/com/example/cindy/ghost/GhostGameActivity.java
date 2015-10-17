package com.example.cindy.ghost;

/** Cindy Schotman - 10595635 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;

public class GhostGameActivity extends Activity {
    private Game game;
    private HashMap<String, Integer> playerHighScores;
    private SharedPreferences.Editor preferenceEditor;
    private DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost_game);

        SharedPreferences preferenceSettings = getSharedPreferences("settings", MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        db = new DBhelper(this);
        playerHighScores = db.read();

        /** gets names for players */
        String name_1 = preferenceSettings.getString("name_1", "Player 1");
        String name_2 = preferenceSettings.getString("name_2", "Player 2");

        /** creates player 1 with the given name and adds all the known information */
        Player player1 = new Player(name_1);
        if(playerHighScores.containsKey(name_1)) {
            player1.highScore = playerHighScores.get(name_1);
            if(!preferenceSettings.getBoolean("ended", true)) {
                player1.score = preferenceSettings.getInt("score_1", -1);
                player1.letters = preferenceSettings.getString("letters_1", "Nothing Found");
            }
        /** add to database and HashSet if player is not known */
        } else {
            playerHighScores.put(name_1, 0);
            db.create(name_1, 0);
        }

        /** creates player 2 with the given name and adds all the known information */
        Player player2 = new Player(name_2);
        if(playerHighScores.containsKey(name_2)) {
            player2.highScore = playerHighScores.get(name_2);
            if(!preferenceSettings.getBoolean("ended", true)) {
                player2.score = preferenceSettings.getInt("score_2", -1);
                player2.letters = preferenceSettings.getString("letters_2", "Nothing Found");
            }
        /** adds player to database and HashSet if player is not known */
        } else {
            playerHighScores.put(name_2, 0);
            db.create(name_2, 0);
        }

        /** adds other known data to the game */
        String language = preferenceSettings.getString("language", "English");
        game = new Game(player1, player2, language, this);
        game.word = preferenceSettings.getString("word", "");
        game.onTurn = preferenceSettings.getString("onTurn", name_1);

        /** sets all the information right on the screen */
        setGameInformation();
    }

    /** sets names, scores and letters of players and set word on the screen */
    private void setGameInformation() {
        // Player On Turn: set name, score and letters
        TextView namePlayerOnTurn = (TextView) findViewById(R.id.namePlayerOnTurn);
        namePlayerOnTurn.setText(game.playerOnTurn().name + "'s turn");
        TextView scorePlayerOnTurn = (TextView) findViewById(R.id.scorePlayerOnTurn);
        scorePlayerOnTurn.setText("score: " + game.playerOnTurn().score);
        TextView lettersPlayerOnTurn = (TextView) findViewById(R.id.lettersPlayerOnTurn);
        lettersPlayerOnTurn.setText("letters: " + game.playerOnTurn().letters);

        // Other Player: set name, score and letters
        TextView nameOtherPlayer = (TextView) findViewById(R.id.nameOtherPlayer);
        nameOtherPlayer.setText(game.playerNotOnTurn().name);
        TextView scoreOtherPlayer = (TextView) findViewById(R.id.scoreOtherPlayer);
        scoreOtherPlayer.setText("score: " + game.playerNotOnTurn().score);
        TextView lettersOtherPlayer = (TextView) findViewById(R.id.lettersOtherPlayer);
        lettersOtherPlayer.setText("letters: " + game.playerNotOnTurn().letters);

        // set word
        TextView wordTextView = (TextView) findViewById(R.id.word);
        wordTextView.setText(game.word);
    }



    public void playTurn(View view) {
        /** gets input */
        EditText wordTextView = (EditText) findViewById(R.id.word);
        String inputWord = String.valueOf(wordTextView.getText());

        /** let game class handle guess of player and return toast text */
        String toastText = game.guess(inputWord);
        switch (toastText) {
            /** goes to WinnerActivity when game is finished */
            case "ENDED":
                db.update(game.player1);
                db.update(game.player2);
                playerHighScores.put(game.winner().name, game.winner().highScore);
                toWinnerActivity();
                break;
            /** shows no toast when guess only leads to next turn */
            case "NEXT":
                break;
            /** shows toast text returned from guess() in game class */
            default:
                Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
                break;
        }
        saveGameInformation();
        setGameInformation();
    }

    /** saves game information in SharedPreferences */
    public void saveGameInformation() {
        preferenceEditor.putString("letters_1", game.player1.letters);
        preferenceEditor.putString("letters_2", game.player2.letters);
        preferenceEditor.putInt("score_1", game.player1.score);
        preferenceEditor.putInt("score_2", game.player2.score);
        preferenceEditor.putString("word", game.word);
        preferenceEditor.putString("turn", game.onTurn);
        preferenceEditor.putBoolean("ended", game.ended);
        preferenceEditor.commit();
    }

    /** sets and starts intent to WinnerActivity with the name of the winner and loser */
    private void toWinnerActivity() {
        Intent toWinner = new Intent(this, WinnerActivity.class);
        toWinner.putExtra("winner", game.winner().name);
        toWinner.putExtra("loser", game.loser().name);
        startActivity(toWinner);
    }

    /** sets and starts intent to MenuActivity */
    public void toMenuActivity(View view) {
        Intent toMenu = new Intent(this, MenuActivity.class);
        startActivity(toMenu);
    }

    /** sets and starts intent to SettingsActivity */
    public void toSettingsActivity(View view) {
        Intent toSettings = new Intent(this, SettingsActivity.class);
        startActivity(toSettings);
    }
}