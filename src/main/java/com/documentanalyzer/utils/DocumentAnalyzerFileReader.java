package com.documentanalyzer.utils;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DocumentAnalyzerFileReader {

    //It reads simple .txt files
    public static String[] readTxtFile(byte[] bytes){
        String words = new String(bytes);
        return transformStringIntoArray(words);
    }

    //It reads microsoft word files (doc and docx) using Apache poi API
    public static String[] readMicrosoftWordFile(String filename, byte[] bytes) throws IOException {
        InputStream input = new ByteArrayInputStream(bytes);

        String fileData = new String();

        if(filename.contains(".docx")){
            XWPFDocument document = new XWPFDocument(input);
            XWPFWordExtractor xExtractor = new XWPFWordExtractor(document);
            fileData = xExtractor.getText();
        }else{
            HWPFDocument document = new HWPFDocument(input);
            WordExtractor hExtractor = new WordExtractor(document);
            fileData = hExtractor.getText();
        }

        return transformStringIntoArray(fileData);
    }

    //Internal method to split the full string into words
    private static String[] transformStringIntoArray(String text){
        String[] wordsArray = text.split("\\s+");
        return wordsArray;
    }

}
