package com.example.kristian.dtu.dk.galgespil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

/**
 * Created by Kristian on 12-10-2016.
 */

public class GalgeLogic {

    private ArrayList<String> listOfWordsToGuess;
    private ArrayList<String> listOfWordsThatHasBeenUsed = new ArrayList<>();
    public String wordToGuess;
    public String wordWithCorrectChar;
    public int wrongGuesses = 0;
    public boolean gameOver = false;
    public boolean gameWon = false;


    public void startGame(){
        wordToGuess = wordList().get(new Random().nextInt(listOfWordsToGuess.size()));
        checkWord();
    }

    public void restart(){
        gameOver = false;
        gameWon = false;
        wordToGuess ="";
        wordWithCorrectChar = "";
        listOfWordsThatHasBeenUsed = new ArrayList<>();
        listOfWordsToGuess = new ArrayList<>();
        wrongGuesses=0;
    }

    public String guessedWord(String word){
        if(!wordToGuess.contains(word)){wrongGuesses++;}
        listOfWordsThatHasBeenUsed.add(word);
        return checkWord();
    }

    private String checkWord(){
        wordWithCorrectChar = "";
        for (int n = 0; n < wordToGuess.length(); n++) {
            String cha = wordToGuess.substring(n, n + 1);
            if (listOfWordsThatHasBeenUsed.contains(cha)) {
                wordWithCorrectChar = wordWithCorrectChar + cha;
            } else {
                wordWithCorrectChar = wordWithCorrectChar + "*";
            }
        }
        printToLog();
        return wordWithCorrectChar;
    }

    public String checkStatus(){
        if (wordToGuess.equalsIgnoreCase(wordWithCorrectChar)) { gameWon = true;}
        if(wrongGuesses >= 6){gameOver = true;}
        Object e = imageList().get(wrongGuesses);
        return e.toString();
    }

    private HashMap imageList () {
        HashMap<Integer,String> imageList = new HashMap<>();
        imageList.put(0,"galge");
        imageList.put(1,"forkert1");
        imageList.put(2,"forkert2");
        imageList.put(3,"forkert3");
        imageList.put(4,"forkert4");
        imageList.put(5,"forkert5");
        imageList.put(6,"forkert6");
        return imageList;
    }

    private ArrayList<String> wordList (){
        listOfWordsToGuess = new ArrayList<>();
        listOfWordsToGuess.add("fisse");
        listOfWordsToGuess.add("kusse");
        listOfWordsToGuess.add("rottehul");
        listOfWordsToGuess.add("skede");
        return listOfWordsToGuess;
    }

    private void printToLog() {
        System.out.println("---------- ");
        System.out.println("- ordet (skjult) = " + wordToGuess);
        System.out.println("- synligtOrd = " + wordWithCorrectChar);
        System.out.println("- size = " + listOfWordsThatHasBeenUsed.size());
        System.out.println("- wrong guesses = " + wrongGuesses);
        System.out.println("- gameWon = " + gameWon);
        System.out.println("- gameOver = " + gameOver);
        System.out.println("---------- ");
    }

    public String getWordWithCorrectChar() {
        return wordWithCorrectChar;
    }

    public ArrayList<String> getListOfWordsThatHasBeenUsed() {
        return listOfWordsThatHasBeenUsed;
    }
}
