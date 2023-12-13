package com.company.Algorithms;

import java.util.ArrayList;
import java.util.List;

public class RabinKarpAlgorithm {

    public static final int primeNumber = 2; // use a bigger prime number, for that you'll have to use a double or long data type

    public static void main(String[] args) {
        String text = "abedabc";
        String pattern = "abc";

        // a function to implement rabin Karp algorithm for a single instance search of pattern in text
        int patternPresentAtIndex = rabinKarpAlgorithmSingleSearch(text, pattern);
        System.out.println(patternPresentAtIndex);

        text = "thestoryofleetcodeandme";
        String[] words = {"story","fleet","leetcode"};
        // rabin karp for searching different patterns and multiple occurrences of each pattern in the text
        // returns a list of all matching position in the form of Pair(startIndex, endIndex)
        List<PairIndices> patternsPresentAtPositions = rabinKarpAlgorithmMultiplePatternAndOccurence(text, words);
        for(int i = 0; i < patternsPresentAtPositions.size(); i++){
            System.out.println(patternsPresentAtPositions.get(i));
        }

        text = "ababa";
        words = new String[]{"aba", "ab"};
        // rabin karp for searching different patterns and multiple occurrences of each pattern in the text
        // returns a list of all matching position in the form of Pair(startIndex, endIndex)
        patternsPresentAtPositions = rabinKarpAlgorithmMultiplePatternAndOccurence(text, words);
        for(int i = 0; i < patternsPresentAtPositions.size(); i++){
            System.out.println(patternsPresentAtPositions.get(i));
        }

    }

    private static List<PairIndices> rabinKarpAlgorithmMultiplePatternAndOccurence(String text, String[] words) {
        // list of all the Pair(startIndex of match, endIndex of match) where we have found the match for the different patterns
        List<PairIndices> patternsPresentAtPositions = new ArrayList<PairIndices>();

        for(int patternIndex = 0; patternIndex < words.length; patternIndex++){
            String pattern = words[patternIndex]; // get the current pattern

            int textLength = text.length(); // get the text length
            int patterLength = pattern.length(); // get the pattern length

            // calculate the pattern hash value
            int patternHashValue = calculateHashValueForTextSubstring(pattern, 0, patterLength - 1, 0, patterLength);
            int textHashVal = 0; // to store the has value of the substring of the text to be compared against the text

            // check every possible substring in the text of length patternLength
            for(int i = 0; i <= textLength - patterLength; i++){
                int startIndex = i; // start index of the substring
                int endIndex = i + patterLength -1; // end index of the substring

                // System.out.println(text.substring(startIndex, endIndex + 1));

                textHashVal = calculateHashValueForTextSubstring(text, startIndex, endIndex, textHashVal, patterLength);

                if(textHashVal == patternHashValue){ // if there is a hash value match
                    int foundCount = 0; // to track number of matches
                    for(int j = 0; j < patterLength; j++){
                        // if pattern character and corresponding text character match
                        if(text.charAt(i + j) == pattern.charAt(j)){
                            foundCount += 1; // increment match count
                        }
                    }
                    if(foundCount == patterLength){ // if the number of matches == length of the pattern
                        // we have found a match, add the Pair(startIndex of match, endIndex of match)
                        patternsPresentAtPositions.add(new PairIndices(startIndex, endIndex));
                    }
                }
            }
        }

        return patternsPresentAtPositions;

    }

    private static int rabinKarpAlgorithmSingleSearch(String text, String pattern) {
        int textLength = text.length();
        int patterLength = pattern.length();

        int patternHashValue = calculateHashValueForTextSubstring(pattern, 0, patterLength - 1, 0, patterLength);
        int textHashVal = 0;

        // check every possible substring in the text of length patternLength
        for(int i = 0; i <= textLength - patterLength; i++){
            int startIndex = i; // start index of the substring
            int endIndex = i + patterLength -1; // end index of the substring

            // System.out.println(text.substring(startIndex, endIndex + 1));

            textHashVal = calculateHashValueForTextSubstring(text, startIndex, endIndex, textHashVal, patterLength);

            if(textHashVal == patternHashValue){ // if there is a hash value match
                int foundCount = 0; // to track number of matches
                for(int j = 0; j < patterLength; j++){
                    // if pattern character and corresponding text character match
                    if(text.charAt(i + j) == pattern.charAt(j)){
                        foundCount += 1; // increment match count
                    }
                }
                if(foundCount == patterLength){ // if the number of matches == length of the pattern
                    // we have found a match
                    return i;
                }
            }
        }
        return -1;
    }

    private static int calculateHashValueForTextSubstring(String text, int start, int end, int currentHashValue, int patternLength) {
        int hashVal = 0;

        if(start == 0){
            int powerCount = 0;
            for(int i = 0; i <= end; i++){
                hashVal += ((int) text.charAt(i)) * ((int) Math.pow(primeNumber, powerCount));
                powerCount += 1;
            }
        }
        else{
            hashVal = currentHashValue - ((int) text.charAt(start - 1));
            hashVal = hashVal / primeNumber;
            hashVal += ((int) text.charAt(end)) * ((int) Math.pow(primeNumber, patternLength - 1));
        }

        return hashVal;
    }

}

class PairIndices {
    int start;
    int end;

    PairIndices(int start, int end){
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "[" + this.start + ", " + this.end + "]";
    }
}
