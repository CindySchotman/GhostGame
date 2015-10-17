package com.example.cindy.ghost;

/** Cindy Schotman - 10595635 */

import android.content.Context;
import java.util.HashMap;

public class Game {

    public Player player1;
    public Player player2;
    public String word;
    public String onTurn;
    public Boolean ended;
    private String language;
    private Lexicon dutchLexicon;
    private Lexicon englishLexicon;
    HashMap<String, Lexicon> lexicons;

    {
        player1 = new Player();
        player2 = new Player();
        word = "";
        onTurn = "";
        ended = false;
        language = "";
        dutchLexicon = new Lexicon();
        englishLexicon = new Lexicon();
        lexicons = new HashMap<>();
    }

    /** constructs a new Game and two players with given input and sets language */
    public Game(Player inputPlayer1, Player inputPlayer2, String inputLanguage, Context context) {
        player1 = inputPlayer1;
        player2 = inputPlayer2;
        setLanguage(inputLanguage, context);
    }

    /** If language needs to be changed, reads in the appropriate lexicon - when it doesn't
      * already exist - and adds it to the list of lexicons */
    public void setLanguage(String inputLanguage, Context context) {
        if(!language.equals(inputLanguage)) {
            language = inputLanguage;
            if(inputLanguage.equals("English") && englishLexicon != null) {
                englishLexicon = new Lexicon(inputLanguage, context);
                lexicons.put(inputLanguage, englishLexicon);
            } else if(inputLanguage.equals("Nederlands") && dutchLexicon != null) {
                dutchLexicon = new Lexicon(inputLanguage, context);
                lexicons.put(inputLanguage, dutchLexicon);
            }
        }
    }

    /** checks input and returns toast text */
    public String guess(String inputWord) {
        inputWord = inputWord.toLowerCase();
        Lexicon lexicon = lexicons.get(language);

        /** returns toast text when no valid input is given */
        if (!inputWord.matches("[a-z]+") || word.equals(inputWord)) {
            return "This is not a letter or a new letter";

        /** handles wrong input and returns toast text when fragment isn't in the lexicon */
        } else if (!lexicon.fragmentExists(inputWord)) {
            handleWrongInput();
            if(ended) {
                return "ENDED";
            }
            return "No word can be made with " + inputWord;
        }
        word = inputWord;

        /** adds letter and updates score when player completed a word */
        if (lexicons.get(language).isWord(inputWord)) {
            word = "";
            handleWrongInput();
            if(ended) {
                return "ENDED";
            }
            return "You finished the word " + inputWord;
        }

        /** goes to next turn when valid input isn't a word */
        nextTurn();
        return "NEXT";
    }

    /** adds letter and updates score when player on turn made a mistake and checks if game is ended*/
    public void handleWrongInput() {
        playerOnTurn().addLetter();
        playerNotOnTurn().updateScore(word.length());

        /** sets game on ended, if game is ended, and returns message */
        if (ended()) {
            ended = true;
            playerOnTurn().updateHighScore();
            playerNotOnTurn().updateHighScore();
        }
    }

    /** returns true if the given player is on turn, otherwise gives false */
    public void nextTurn() {
        if(onTurn.equals(player1.name)) {
            onTurn = player2.name;
        } else {
            onTurn = player1.name;
        }
    }

    /** returns the player on turn */
    public Player playerOnTurn() {
        if (onTurn.equals(player1.name)) {
            return player1;
        } else {
            return player2;
        }
    }

    /** returns the player not on turn */
    public Player playerNotOnTurn() {
        if (onTurn.equals(player1.name)) {
            return player2;
        } else {
            return player1;
        }
    }

    /** returns true if one of the players has 'GHOST', otherwise returns false */
    public boolean ended() {
        return (player1.letters.equals("GHOST") || player2.letters.equals("GHOST"));
    }

    /** returns player that is the winner of the game */
    public Player winner() {
        if(player1.letters.equals("Ghost")) {
            return player2;
        } else {
            return player1;
        }
    }
    /** returns player that is the loser of the game */
    public Player loser() {
        if(player1.letters.equals("Ghost")) {
            return player1;
        } else {
            return player2;
        }
    }
}