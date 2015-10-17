package com.example.cindy.ghost;

/** Cindy Schotman - 10595635 */

import android.content.Context;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Lexicon {

    private List<String> words;
    private String language;

    {
        words = new ArrayList<>();
        language = "";
    }

    /** constructs a new empty Story */
    public Lexicon() {}

    /** constructs a new Story reading its text from the appropriate input stream */
    public Lexicon(String inputLanguage, Context context) {
        language = inputLanguage;

        InputStream stream;
        switch(language) {
            case "English":
                stream = context.getResources().openRawResource(R.raw.english);
                break;
            case "Nederlands":
                stream = context.getResources().openRawResource(R.raw.dutch);
                break;
            default:
                stream = context.getResources().openRawResource(R.raw.empty);
                break;
        }
        read(stream);
    }

    /** returns true when there is a word in the lexicon that starts with the fragment given
      * as input, otherwise returns false */
    public boolean fragmentExists(String fragment) {
        for(Iterator<String> it = words.iterator(); it.hasNext();) {
            String word = it.next();
            if(word.startsWith(fragment.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /** returns true if the input is equal to a whole word in the lexicon, otherwise returns false */
    public boolean isWord(String inputWord) {
        for(Iterator<String> it = words.iterator(); it.hasNext();) {
            String word = it.next();
            if(word.equals(inputWord.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /** reads list of words from the given input stream (.txt) */
    public void read(InputStream stream) {
        read(new Scanner(stream));
    }

    /** reads in all words longer than 3 letters into the list of words */
    public void read(Scanner input) {
        while (input.hasNext()) {
            String inputWord = input.next().toLowerCase();
            if (inputWord.length() > 3) {
                words.add(inputWord);
            }
        }
    }
}