package com.example.cindy.ghost;

/** Cindy Schotman - 10595635 */

public class Player {

    public String name;
    public String letters;
    public int score;
    public int highScore;

    {
        name = "";
        letters = ".....";
        score = 0;
        highScore = 0;
    }

    /** constructs a new empty Player */
    public Player() {}

    /** constructs a new Player with input name */
    public Player(String inputName) {
        name = inputName;
    }

    /** adds a letter of the word "GHOST" to the letters the player already has */
    public void addLetter() {
        switch(letters) {
            case ".....":
                letters = "G....";
                break;
            case "G....":
                letters = "GH...";
                break;
            case "GH...":
                letters = "GHO..";
                break;
            case "GHO..":
                letters = "GHOS.";
                break;
            case "GHOS.":
                letters = "GHOST";
                break;
            default:
                letters = "GHOST!";
                break;
        }
    }

    /** calculates new score depending on the length of the formed word:
      * 5*(length^2) + 10% of current score */
    public void updateScore(int length) {
        score += 5*((int)Math.pow(length, 2)) + (score * 1.10);
    }

    /** sets score as high score when score is currently higher than the high score */
    public void updateHighScore() {
        if(score > highScore) {
            highScore = score;
        }
    }
}