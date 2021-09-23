package com.creditsuisse.covid19.upload;

import com.creditsuisse.covid19.exception.handler.Covid19Exception;
import com.creditsuisse.covid19.exception.handler.NotFoundException;
import com.creditsuisse.covid19.outbreak.OutBreak;
import com.creditsuisse.covid19.outbreak.OutBreakService;
import com.mongodb.MongoBulkWriteException;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@RestController
public class CsvToMongoUploadController {

    @Autowired
    private OutBreakService outBreakService;

    @Value("${csv.download.path}")
    private String csvPath;

    @GetMapping("/upload/{date}")
    public ResponseEntity upload(@PathVariable String date){
        File file = new File(csvPath + date);
        List<OutBreak> outBreaks;
        long start = System.currentTimeMillis();
        if(file.exists()){
            try(FileReader fileReader = new FileReader(file)){
                outBreaks = new CsvToBeanBuilder<OutBreak>(fileReader)
                        .withType(OutBreak.class)
                        .build()
                        .parse();

                outBreakService.saveAll(outBreaks);
            }
            catch (IOException | MongoBulkWriteException ex){
                ex.printStackTrace();
                //TODO: logging and remove trace
                throw new Covid19Exception(ex.getMessage());
            }
            long end = System.currentTimeMillis();
            long diff = end - start;
            return new ResponseEntity("Created in " + diff + " ms.", HttpStatus.CREATED);
        }
        throw new NotFoundException("File Not Found:" + file.getName());
    }
}
