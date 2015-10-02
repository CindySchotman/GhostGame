package com.example.cindy.ghost;

import android.content.Context;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Lexicon {

    private List<String> wordsEnglish;      // list of English words
    private List<String> wordsDutch;        // list of Dutch words
    private HashSet<String> filteredWords;        // list of Dutch words
    private String language;          // language of input

    {
        // instance initializer; runs before any constructor
        wordsEnglish = new ArrayList<>();
        wordsDutch = new ArrayList<>();
        filteredWords = new HashSet<>();
        language = "";
        clear();
    }

    /** constructs a new empty Story */
    public Lexicon() {
        // empty
    }

    /** constructs a new Story reading its text from the given input stream */
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

    /** resets the story back to an empty initial state */
    public void clear() {
        wordsEnglish.clear();
        wordsDutch.clear();
        filteredWords.clear();
        language = "";
    }

    /** sets filteredWords as all the words of the list in the language asked */
    public void reset() {
        filteredWords.clear();
        switch (language) {
            case "English":
                for(int i = 0; i < wordsEnglish.size(); i++) {
                    filteredWords.add(wordsEnglish.get(i));                 // deeply clone?
                }
                break;
            case "Nederlands":
                for(int i = 0; i < wordsDutch.size(); i++) {
                    filteredWords.add(wordsDutch.get(i));
                }
                break;
            default:
                break;
        }
    }

    /** returns true if all placeholders have been filled in */
    public void filter(String fragment) {
        for(Iterator<String> it = filteredWords.iterator(); it.hasNext();) {        // foreach?
            String word = it.next();
            if(!(word.contains(fragment.toLowerCase()) && word.indexOf(fragment) == 0)) {
                filteredWords.remove(word);
            }
        }
    }

    /** returns true if all placeholders have been filled in */
    public int count() {
        return filteredWords.size();
    }

    public String result() {
        for(Iterator<String> it = filteredWords.iterator(); it.hasNext();) {        // foreach?
            return it.next();
        }
        return "";
    }

    /** returns true if all placeholders have been filled in */
    public boolean fragmentExists(String fragment) {
        for(Iterator<String> it = filteredWords.iterator(); it.hasNext();) {        // foreach?
            String word = it.next();
            if(word.contains(fragment.toLowerCase()) && word.indexOf(fragment) == 0) {
                return true;
            }
        }
        return false;
    }

    /** returns true if all placeholders have been filled in */
    public boolean isWord(String inputWord) {
        for(Iterator<String> it = filteredWords.iterator(); it.hasNext();) {        // foreach?
            String word = it.next();
            if(word.equals(inputWord.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /** reads initial story text from the given input stream */
    public void read(InputStream stream) {
        read(new Scanner(stream));
    }

    /** reads initial story text from the given Scanner */
    public void read(Scanner input) {
        while (input.hasNext()) {
            String inputWord = input.next();    //input.nextLine()
            if (!inputWord.equals("")) {        //inputWord.isEmpty()
                switch(language) {
                    case "English":
                        wordsEnglish.add(inputWord);
                        filteredWords.add(inputWord);
                        break;
                    case "Dutch":
                        wordsDutch.add(inputWord);
                        filteredWords.add(inputWord);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}

