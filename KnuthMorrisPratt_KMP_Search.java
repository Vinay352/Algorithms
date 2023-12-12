package com.company.Algorithms;

public class KnuthMorrisPratt_KMP_Search {

    public static void main(String[] args) {
        String text = "abcdabca";
        text = "aabaabaaa";
        text = "acacabacacabacacac";
        int[] kmpDp = prefixSearch(text);
        for(int i = 0; i < kmpDp.length; i++){
            System.out.print(kmpDp[i] + " ");
        }
        System.out.println();

        text = "abxabcabcaby";
        String pattern = "abcaby";
        kmpDp = prefixSearch(text);

        boolean substringPresent = kmpSubstringSearch(text, pattern, kmpDp);
        System.out.println(substringPresent);
    }

    public static boolean kmpSubstringSearch(String text, String pattern, int[] kmpDP){
        int i = 0; // pointer for text in which we have to find the pattern
        int j = 0; // pointer for pattern which we have to find in the text

        int length = text.length();
        while(i < length){ // loop to compare characters of pattern and text
            if(text.charAt(i) == pattern.charAt(j)){ // if there is a match
                // compare the next characters in text and pattern
                i += 1;
                j += 1;
            }
            else{
                if(text.charAt(i) != pattern.charAt(j)){ // if they don't match
                    if(j > 0){ // go to the ((last index in pattern till where you had a match) + 1)
                        j = kmpDP[j - 1];
                    }
                    else if(j == 0){
                        i += 1; // check next character of text
                    }
                }
            }

            if(j == pattern.length()){ // if we found the pattern
                return true;
            }
        }

        return false;
    }

    public static int[] prefixSearch(String pattern){
        int length = pattern.length();
        int[] kmp_dp = new int[length];

        int i = 1; // pointer for checking the suffix
        int j = 0; // pointer for checking the prefix

        while(i < length){
            if(pattern.charAt(i) != pattern.charAt(j)){ // if they don't match
                if(j == 0){ // if jth pointer is at the beginning
                    kmp_dp[i] = 0;
                    i += 1;
                }
                else{ // if jth pointer is not the beginning
                    // jth pointer is now (the last place till where we had a match) + 1
                    j = kmp_dp[j - 1];
                }
            }
            else if(pattern.charAt(i) == pattern.charAt(j)){ // if the values match
                // make a record at ith location
                // that we have a match just before current jth position
                kmp_dp[i] = j + 1;

                // increment i and j - compare next character
                i += 1;
                j += 1;
            }
        }

        return kmp_dp;
    }

}
