package com.documentanalyzer.utils;

import java.util.*;

public class WordAnalyser {

    //This method is a bit complex
    //It finds all words and their frequency
    //Then it order them by frequency
    //Then return the first 10 or less if there are less than 10 words found.
    public static List<WordFrequencyModel> findTenWordsWithBiggestFrequency(String[] arr) {
        List<WordFrequencyModel> tenWordsWithBiggestFrequency = new ArrayList<>();

        HashMap<String, Integer> wordsFrequencyMap = new HashMap<String, Integer>();

        for (int i = 0; i < arr.length; i++) {
            if (wordsFrequencyMap.containsKey(arr[i])) {
                wordsFrequencyMap.put(arr[i], wordsFrequencyMap.get(arr[i]) + 1);
            } else {
                wordsFrequencyMap.put(arr[i], 1);
            }
        }

        Set<Map.Entry<String, Integer>> wordsFrequencySet = wordsFrequencyMap.entrySet();

        List<WordFrequencyModel> wordsFrequencyList = new ArrayList<>();
        for (Map.Entry<String, Integer> wordsFrequencyMapEntry : wordsFrequencySet) {
            if(isValidWord(wordsFrequencyMapEntry.getKey())){
                WordFrequencyModel wordFrequencyModel = new WordFrequencyModel();
                wordFrequencyModel.setFrequency(wordsFrequencyMapEntry.getValue().longValue());
                wordFrequencyModel.setWord(wordsFrequencyMapEntry.getKey());
                wordsFrequencyList.add(wordFrequencyModel);
            }
        }

        Collections.sort(wordsFrequencyList, new Comparator<WordFrequencyModel>() {
            @Override
            public int compare(WordFrequencyModel o1, WordFrequencyModel o2) {
                return -o1.getFrequency().compareTo(o2.getFrequency());
            }
        });

        int limit = wordsFrequencyList.size() > 10 ? 10 : wordsFrequencyList.size();
        for(int x=0; x<limit; x++){
            tenWordsWithBiggestFrequency.add(wordsFrequencyList.get(x));
        }

        return tenWordsWithBiggestFrequency;
    }

    //This method finds the frequency of a specific word
    public static Integer findFrequencyOfWord(String[] wordsArray, String word){
        int count = 0;
        for (int i = 0; i < wordsArray.length; i++) {
            if (word.toLowerCase().equals(wordsArray[i].toLowerCase()))
                count++;
        }
        return count;
    }

    //This method receives the file in byte array format
    //Then distribute it for the specific reader regarding the file suffix
    //Then receives a string array with all words and remove the special chars.
    public static String[] getWordsArray(String fileName, byte[] bytes) throws Exception {
        String[] wordsArray = new String[]{};

        if(fileName.contains(".txt"))
            wordsArray = DocumentAnalyzerFileReader.readTxtFile(bytes);
        else if(fileName.contains(".doc") || fileName.contains(".docx")){
            wordsArray = DocumentAnalyzerFileReader.readMicrosoftWordFile(fileName, bytes);
        }
        else
            throw new Exception("The system doesn't support this document type (try .txt, .doc or .docx)");


        List<String> validStrings = new ArrayList<String>();
        for(String string : wordsArray){
            if(!isSpecialChar(string))
                validStrings.add(string);
        }

        String[] validatedWordsArray = validStrings.stream().toArray(String[]::new);

        return validatedWordsArray;
    }

    //This method retrieves how many words there are in a file
    public static Long getWordsCount(String filename, byte[] bytes) throws Exception {
        if(filename.contains(".txt") || filename.contains(".doc") || filename.contains(".docx")){
            return new Long(getWordsArray(filename, bytes).length);
        }else{
            throw new Exception("The system doesn't support this document type (try .txt, .doc or .docx)");
        }
    }

    //This is an internal method to validate the word removing the invalid words as per requirement
    //Also removing the special chars.
    private static boolean isValidWord(String word){
        if(word.toLowerCase().equals("the") ||
           word.toLowerCase().equals("me") ||
           word.toLowerCase().equals("you") ||
           word.toLowerCase().equals("i") ||
           word.toLowerCase().equals("of") ||
           word.toLowerCase().equals("and") ||
           word.toLowerCase().equals("a") ||
           word.toLowerCase().equals("we")){
            return false;
        }

        if(isSpecialChar(word)) return false;

        return true;
    }

    //This method verifies if the word is actually a word or a special char.
    private static boolean isSpecialChar(String word){
        String alphaNumericStr = word.replaceAll("[^a-zA-Z0-9]", "");
        if(alphaNumericStr.equals("")){
            return true;
        }
        return false;
    }
}
