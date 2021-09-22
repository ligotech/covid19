package com.creditsuisse.covid19.download;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RestController
public class CsvDownloadController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${covid19.csv.data.repository}")
    private String url;

    @Value("${csv.download.path}")
    private String csvPath;

    @GetMapping("/download/{date}")
    public ResponseEntity download(@PathVariable String date){
        ResponseEntity<String> responseEntity =  restTemplate.getForEntity(url + date, String.class);
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            File file = new File(csvPath + date);
            if(file.exists()) {
                file.delete();
            }
            try(FileWriter fileWriter = new FileWriter(file)){
                fileWriter.write(responseEntity.getBody());
            }
            catch (IOException ex){
                //TODO: logging
            }
        }
        return responseEntity;
    }
}
