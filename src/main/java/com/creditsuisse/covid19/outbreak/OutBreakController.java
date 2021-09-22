package com.creditsuisse.covid19.outbreak;

import com.creditsuisse.covid19.beans.TotalCount;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class OutBreakController {

    @Autowired
    private OutBreakService outBreakService;

    @GetMapping("outbreak/{column}")
    public ResponseEntity<Number> getTotalCount(@PathVariable String column){
        Number val = null;
        try{
            if(column.equals("active")){
                val = outBreakService.getTotalActive();
            }
            if(column.equals("confirmed")){
                val = outBreakService.getTotalConfirmed();
            }
            if(column.equals("deaths")){
                val = outBreakService.getTotalDeaths();
            }
            if(column.equals("recovered")){
                val = outBreakService.getTotalRecovered();
            }
            if(column.equals("incidentRate")){
                val = outBreakService.getTotalIncidentRate();
            }
            if(column.equals("caseFatalityRatio")){
                val = outBreakService.getTotalCaseFatalityRatio();
            }
        }
        catch (MongoException ex){
            //TODO: logging and remove stack trace
            ex.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(val != null){
            return new ResponseEntity<>(val, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("query/{column}/")
    public ResponseEntity<List<TotalCount>> getTotalCount(@PathVariable String column, @RequestParam Map<String, String> params){
        List<TotalCount> totalCounts = new ArrayList<>();
        try{
            if(column.equals("active")){
                totalCounts = outBreakService.getTotalActiveWithCriteria(params);
            }
            if(column.equals("confirmed")){
                totalCounts = outBreakService.getTotalConfirmedWithCriteria(params);
            }
            if(column.equals("deaths")){
                totalCounts = outBreakService.getTotalDeathsWithCriteria(params);
            }
            if(column.equals("recovered")){
                totalCounts = outBreakService.getTotalRecoveredWithCriteria(params);
            }
            if(column.equals("incidentRate")){
                totalCounts = outBreakService.getTotalIncidentRateWithCriteria(params);
            }
            if(column.equals("caseFatalityRatio")){
                totalCounts = outBreakService.getTotalCaseFatalityRatioWithCriteria(params);
            }
        }
        catch (MongoException ex){
            //TODO: logging and remove stack trace
            ex.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(totalCounts.size() > 0){
            return new ResponseEntity<>(totalCounts, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(totalCounts, HttpStatus.NOT_FOUND);
        }
    }
}
