package com.creditsuisse.covid19.outbreak;

import com.creditsuisse.covid19.beans.TotalCount;
import com.creditsuisse.covid19.exception.handler.Covid19Exception;
import com.creditsuisse.covid19.exception.handler.NotFoundException;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(OutBreakController.class);

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
            if(column.equals("incident-rate")){
                val = outBreakService.getTotalIncidentRate();
            }
            if(column.equals("case-fatality-ratio")){
                val = outBreakService.getTotalCaseFatalityRatio();
            }
        }
        catch (MongoException ex){
            logger.error("Exception:", ex.getMessage(), ex);
            throw new Covid19Exception(ex.getMessage());
        }
        catch (Exception ex){
            logger.error("Exception:", ex.getMessage(), ex);
            throw new Covid19Exception(ex.getMessage());
        }
        if(val != null){
            return new ResponseEntity<>(val, HttpStatus.OK);
        }
        throw new NotFoundException("Data not found. " + val);
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
            if(column.equals("incident-rate")){
                totalCounts = outBreakService.getTotalIncidentRateWithCriteria(params);
            }
            if(column.equals("case-fatality-ratio")){
                totalCounts = outBreakService.getTotalCaseFatalityRatioWithCriteria(params);
            }
        }
        catch (MongoException  ex){
            logger.error("Exception:", ex.getMessage(), ex);
            throw new Covid19Exception(ex.getMessage());
        }
        catch (Exception ex){
            logger.error("Exception:", ex.getMessage(), ex);
            throw new Covid19Exception(ex.getMessage());
        }

        if(!totalCounts.isEmpty()){
            return new ResponseEntity<>(totalCounts, HttpStatus.OK);
        }
        throw new NotFoundException("Data Not Found: " + totalCounts);
    }

    @GetMapping("query/containment-zones")
    public ResponseEntity<List<String>> getContainmentZones(@RequestParam Map<String, String> params){
        List<String> list = new ArrayList<>();
        try {
            String threshold = params.get("threshold");
            String date = params.get("date");
            list = outBreakService.getContainmentZone(Long.valueOf(threshold), Integer.valueOf(date));
        }
        catch (MongoException ex){
            logger.error("Exception:", ex.getMessage(), ex);
            throw new Covid19Exception(ex.getMessage());
        }
        catch (Exception ex){
            logger.error("Exception:", ex.getMessage(), ex);
            throw new Covid19Exception(ex.getMessage());
        }
        if(!list.isEmpty()){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        throw new NotFoundException("Data Not Found: " + list);
    }
}
