package com.creditsuisse.covid19.outbreak;

import com.creditsuisse.covid19.beans.ReadyToWorkRequestObject;
import com.creditsuisse.covid19.beans.TotalCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OutBreakService {

    @Autowired
    private OutBreakMongoRepository outBreakMongoRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public OutBreak save(OutBreak outBreak){
        return outBreakMongoRepository.save(outBreak);
    }

    public List<OutBreak> saveAll(List<OutBreak> outBreaks){
        return outBreakMongoRepository.saveAll(outBreaks);
    }

    public Long getTotalActive(){
        return outBreakMongoRepository.getTotalActiveCount().getSum();
    }

    public Long getTotalConfirmed(){
        return outBreakMongoRepository.getTotalConfirmedCount().getSum();
    }

    public Long getTotalDeaths(){
        return outBreakMongoRepository.getTotalDeathsCount().getSum();
    }

    public Long getTotalRecovered(){
        return outBreakMongoRepository.getTotalRecoveredCount().getSum();
    }
    public Double getTotalIncidentRate(){
        return outBreakMongoRepository.getTotalIncidentRate().getAverage();
    }

    public Double getTotalCaseFatalityRatio(){
        return outBreakMongoRepository.getTotalCaseFatalityRatio().getAverage();
    }

    public List<TotalCount> getTotalActiveWithCriteria(Map<String, String> params) {
        return getResult(params, "Active", "sum");
    }

    public List<TotalCount> getTotalConfirmedWithCriteria(Map<String, String> params) {
        return getResult(params, "Confirmed", "sum");
    }

    public List<TotalCount> getTotalDeathsWithCriteria(Map<String, String> params) {
        return getResult(params, "Deaths", "sum");
    }

    public List<TotalCount> getTotalRecoveredWithCriteria(Map<String, String> params) {
        return getResult(params, "Recovered", "sum");
    }

    public List<TotalCount> getTotalIncidentRateWithCriteria(Map<String, String> params) {
        return getResult(params, "Incident_Rate", "average");
    }

    public List<TotalCount> getTotalCaseFatalityRatioWithCriteria(Map<String, String> params) {
        return getResult(params, "Case_Fatality_Ratio", "average");
    }

    public List<String> getContainmentZone(Long activeCaseThreshold, Integer date){
        Query query = new Query(new Criteria("Active").gt(activeCaseThreshold).and("date").is(date));
        query.fields().include("Combined_Key");
        return mongoTemplate.find(query, OutBreak.class).stream().map(it -> it.getCombined_Key()).collect(Collectors.toList());
    }

    public Boolean getReadyToWork(ReadyToWorkRequestObject object){
        Double kmInLongitudeDegree = 111.320 * Math.cos( object.getLatitude() / 180.0 * Math.PI);
        Double deltaLat = object.getRadius() / 111.1;
        Double deltaLong = object.getRadius() / kmInLongitudeDegree;

        Double minLat = object.getLatitude() - deltaLat;
        Double maxLat = object.getLatitude() + deltaLat;
        Double minLong = object.getLongitude() - deltaLong;
        Double maxLong = object.getLongitude() + deltaLong;

        Criteria criteria = new Criteria("date").is(object.getDate())
                            .and("Lat").gt(minLat).lt(maxLat)
                            .and("Long_").gt(minLong).lt(maxLong);
        Query query = new Query(criteria);
        query.fields().include("Active", "Country_Region");
        Double totalActive  = mongoTemplate.find(query, OutBreak.class)
                            .stream()
                            .parallel()
                            .mapToDouble(it -> it.getActive()).sum();
        Double percent = (totalActive / object.getPopulation()) * 100;
        if(percent < object.getThreshold()){
            return true;
        }
        return false;
    }

    private Criteria getCriteria(Map<String, String> params){
        List<Criteria> criterias = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            if(key.equals("city")){
                Criteria criteria = new Criteria("Province_State").is(value);
                criterias.add(criteria);
            }
            if(key.equals("country")){
                Criteria criteria = new Criteria("Country_Region").is(value);
                criterias.add(criteria);
            }
            if(key.equals("date")){
                Criteria criteria = new Criteria("date").is(Integer.valueOf(value));
                criterias.add(criteria);
            }
            if(key.equals("frequency")){

            }
        }
        if(criterias.size() > 1){
            return criterias.get(0).andOperator(criterias.stream().skip(1).collect(Collectors.toList()).toArray(new Criteria[0]));
        }
        return criterias.get(0);
    }

    private List<TotalCount> getResult(Map<String, String> params, String aggColumn, String alias){
        Criteria criteria = getCriteria(params);
        MatchOperation matchStage = Aggregation.match(criteria);
        String groupBy = getGroupBy(params.get("groupBy"));
        GroupOperation groupOperation;
        if(alias.equals("sum")){
            groupOperation = Aggregation.group(groupBy)
                    .sum(aggColumn).as(alias);
        }
        else{
            groupOperation = Aggregation.group(groupBy)
                    .avg(aggColumn).as(alias);
        }

        Aggregation aggregation = Aggregation.newAggregation(matchStage, groupOperation);

        AggregationResults<TotalCount> output = mongoTemplate.aggregate(aggregation, "covid19data", TotalCount.class);
        return output.getMappedResults();
    }

    private String getGroupBy(String groupBy){
        if(groupBy.equals("city")){
            return "Province_State";
        }
        if(groupBy.equals("country")){
            return "Country_Region";
        }
        throw new RuntimeException("Not a groupBy column: " + groupBy);
    }
}
