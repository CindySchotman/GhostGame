package com.example.cindy.ghost;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private String language;             // language of the game
    private int turn;                    // number of index of placeholders of player on turn
    public String word;                 // word that players are currently guessing on
    private List<Player> players;        // list of players
    private Lexicon lexicon;             // lexicon - language

    {
        // instance initializer; runs before any constructor
        language = "English";
        turn = 0;
        word = "";
        players = new ArrayList<Player>();
        lexicon = new Lexicon();
        clear();
    }



    /** constructs a new empty Game */
    public Game() {
        // empty
    }

    /** constructs a new Game with given input */
    public Game(Player player1, Player player2, String inputLanguage, Context context) {
        language = inputLanguage;
        players.add(player1);
        players.add(player2);
        lexicon = new Lexicon(inputLanguage, context);
    }

    /** resets the game back to an empty initial state */
    public void clear() {
        language = "English";
        turn = 0;
        word = "";
        players.clear();
        lexicon.clear();
    }

    /** constructs a new Game with given input */
    public void changeLanguage(String inputLanguage, Context context) {
        if(!language.equals(inputLanguage)) {
            language = inputLanguage;
            lexicon = new Lexicon(inputLanguage, context);
        }
    }


    /** play turn */
    public void playTurn(String inputWord) {
        if(guess(inputWord)) {
            word = inputWord;
            if(ended()) {
                if(winner(onTurn())) {
                    // player on turn is winner
                } else {
                    // other player is winner
                }
            }
            nextTurn();
        }
    }

    /** adds new guessed letter to word, returns true if succeeded otherwise returns false */
    public boolean guess(String letter) {
        if (letter.matches("[a-zA-Z]+")) {
            word += letter;
            if(lexicon.fragmentExists(word)) {
                return true;
            } else {
                //toast not an existing word
            }
        }
        return false; // toast no letter
    }

    /** returns true if the given player is on turn, otherwise gives false */
    public boolean turn(Player player) {
        int index = players.indexOf(player);
        return turn == index;
    }

    /** returns true if the given player is on turn, otherwise gives false */
    public void nextTurn() {
        if(turn == 0) {
            turn = 1;
        } else {
            turn = 0;
        }
    }

    /** returns the player on turn */
    public Player onTurn() {
        return players.get(turn);
    }

    /** returns true if one of the players has 'GHOST', otherwise returns false */
    public boolean ended() {
        for (int i = 0; i < players.size(); i++) {
            String ghostLetters = players.get(i).letters;
            if (ghostLetters.equals("GHOST")) {
                return true;
            }
        }
        return false;
    }

    /** returns if player is winner of game */
    public boolean winner(Player player) {
        return !player.letters.equals("GHOST");
    }
}
