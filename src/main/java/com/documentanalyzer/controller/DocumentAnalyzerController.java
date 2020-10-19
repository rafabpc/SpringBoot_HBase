package com.documentanalyzer.controller;

import com.documentanalyzer.model.Upload;
import com.documentanalyzer.model.User;
import com.documentanalyzer.repositories.UploadRepository;
import com.documentanalyzer.repositories.UserRepository;
import com.documentanalyzer.utils.TimestampConverter;
import com.documentanalyzer.utils.WordAnalyser;
import com.documentanalyzer.utils.WordFrequencyModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class DocumentAnalyzerController {

    //The document analyzer controller provides all endpoints regarding the upload model (All methods can be accessed by Postman)

    private final UserRepository userRepository;
    private final UploadRepository uploadRepository;

    public DocumentAnalyzerController(UserRepository userRepository, UploadRepository uploadRepository) {
        this.userRepository = userRepository;
        this.uploadRepository = uploadRepository;
    }

    //This is a POST method responsible for the upload
    //It receives the file as parameter, manipulate the objects and save them in the database.
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody
    Object uploadFileHandler(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();

            User user = userRepository.findById(userId).get();

            Upload upload = new Upload();
            upload.setDocumentName(file.getOriginalFilename());
            upload.setFile(bytes);
            upload.setWordsCount(WordAnalyser.getWordsCount(file.getOriginalFilename(), bytes));
            upload.setUser(user);
            uploadRepository.save(upload);

            user.setLastUpload(TimestampConverter.getTimestamp(new Date()));
            userRepository.save(user);

        }
        return new ResponseEntity(HttpStatus.OK);
    }

    //This method retrieves all the uploads made by a certain team
    @RequestMapping("/uploadsByTeam/{team}")
    public @ResponseBody
    String getUploadsByTeam(@PathVariable Long team) throws JsonProcessingException {
        Iterable<Upload> uploadsIterator = uploadRepository.findByTeam(team);
        List<Upload> uploads = StreamSupport
                .stream(uploadsIterator.spliterator(), false)
                .collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(uploads);

        return json;
    }

    //This method retrieves the frequency of a specific word in a specific file, received by parameter.
    @RequestMapping(value = "/getWordOcurrence")
    public @ResponseBody
    Object uploadFileHandler(@RequestParam("word") String word, @RequestParam("fileId") Long fileId) throws Exception {
        Upload upload = uploadRepository.findById(fileId).get();
        String[] wordsArray = WordAnalyser.getWordsArray(upload.getDocumentName(), upload.getFile());
        return WordAnalyser.findFrequencyOfWord(wordsArray, word);
    }

    //This method retrieves the 10 words with biggest frequencies in a specific file
    @RequestMapping(value = "/getTenWordsWithBiggestFrequency/{fileId}")
    public @ResponseBody String getTenWordsWithBiggestFrequency(@PathVariable Long fileId) throws Exception {
        Upload upload = uploadRepository.findById(fileId).get();
        String[] wordsArray = WordAnalyser.getWordsArray(upload.getDocumentName(), upload.getFile());
        List<WordFrequencyModel> wordsFrequency = WordAnalyser.findTenWordsWithBiggestFrequency(wordsArray);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(wordsFrequency);
        return json;
    }

}