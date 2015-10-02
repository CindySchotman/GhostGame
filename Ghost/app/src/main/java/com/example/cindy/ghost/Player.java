package com.example.cindy.ghost;

public class Player {

    public String name;
    public String letters;
    public int score;
    public int highscore;

    {
        name = "";
        letters = "";
        score = 0;
        highscore = 0;
    }

    /** constructs a new empty Player */
    public Player() {
        // empty
    }

    /** constructs a new Player with given name */
    public Player(String inputName) {
        name = inputName;
    }

    /** resets the players game-data back to an initial state */
    public void clear() {
        letters = "";
        score = 0;
    }

    public void setHighscore() {
        if(score > highscore) {
            highscore = score;
        }
    }

    public void addLetter() {
        switch(letters.length()) {
            case 0:
                letters = "G";
                break;
            case 1:
                letters = "GH";
                break;
            case 2:
                letters = "GHO";
                break;
            case 3:
                letters = "GHOS";
                break;
            case 4:
                letters = "GHOST";
                break;
            default:
                letters = "GHOST!";
                break;
        }
    }

    public void updateScore() {
        score += 1000;
    }

    public void changeName(String newName) {
        name = newName;
    }
}
