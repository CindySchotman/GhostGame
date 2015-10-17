package com.example.cindy.ghost;

/** Cindy Schotman - 10595635 */

import java.util.HashMap;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "firstdb.db";
    private static final String TABLE = "playerHighScores";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_HIGHSCORE = "highScore";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE playerHighScores(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, highScore INTEGER)";
        db.execSQL(query);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST playerHighScores");
        onCreate(db);
    }

    /** adds name and high score of a new player to the database */
    public void create(String name, int highScore) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_HIGHSCORE, highScore);

        db.insert(TABLE, null, values);
        db.close();
    }

    /** reads names and high scores from database and returns it in a HashMap */
    public HashMap<String, Integer> read() {
        /** reads names and high scores */
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT name, highScore FROM playerHighScores";
        Cursor cursor = db.rawQuery(query, null);

        /** gets row indexes */
        int rowIndexName = cursor.getColumnIndex(KEY_NAME);
        int rowIndexHighScore = cursor.getColumnIndex(KEY_HIGHSCORE);

        // puts data into HashMap
        HashMap<String, Integer> listPlayersHighScores = new HashMap<>();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            listPlayersHighScores.put(cursor.getString(rowIndexName), cursor.getInt(rowIndexHighScore));
        }

        db.close();
        return listPlayersHighScores;
    }

    /** updates name and high score of a player in the database */
    public void update(Player player) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, player.name);
        values.put(KEY_HIGHSCORE, player.highScore);

        SQLiteDatabase db = getWritableDatabase();
        int id = getIDfromDatabase(db, player);
        db.update(TABLE, values, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    /** returns ID from player from database */
    public int getIDfromDatabase(SQLiteDatabase db, Player player) {
        String query = "SELECT id, name FROM playerHighScores";
        Cursor cursor = db.rawQuery(query, null);
        int nameIndex = cursor.getColumnIndex(KEY_NAME);

        int id = -1;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if(cursor.getString(nameIndex).equals(player.name)) {
                id = cursor.getColumnIndex(KEY_ID);
            }
        }
        return id;
    }
}