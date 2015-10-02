package com.example.cindy.ghost;

import java.util.ArrayList;
import java.util.List;

public class PlayersList {

    private List<Player> players;        // list of players

    {
        players = new ArrayList<Player>();
    }

    /** constructs a new empty list of players */
    public PlayersList() {
        // empty
    }

    /** adds new player to the list of players when the name doesn't already exists */
    public void addPlayer(Player player) {
        if(!existence(player.name)) {
            players.add(player);
        }
    }

    /** clears list of players */
    public void clear() {
        players.clear();
    }

    /** returns the existing (otherwise new) player with the name given as input */
    public Player getPlayer(String inputName) {
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).name.equals(inputName)) {
                return players.get(i);
            }
        }
        Player newPlayer = new Player(inputName);
        players.add(newPlayer);
        return newPlayer;
    }

    /** returns a list of the highscores of all the players */
    public List<Integer> getAllHighscores() {
        List<Integer> allHighscores = new ArrayList<Integer>();
        for(int i = 0; i < players.size(); i++) {
            allHighscores.add(players.get(i).highscore);
        }
        return allHighscores;
    }


    /** returns true if name already exists, otherwise returns false */
    public boolean existence(String inputName) {
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).name.equals(inputName)) {
                return true;
            }
        }
        return false;
    }


}
