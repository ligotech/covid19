package com.creditsuisse.covid19.outbreak;

import com.creditsuisse.covid19.beans.TotalCount;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OutBreakMongoRepository extends MongoRepository<OutBreak, String> {

    // Full Aggregation
    @Aggregation(pipeline = {"{$match: {}}", "{$group: { _id: null, sum: { $sum: \"$Active\"}}}"})
    TotalCount getTotalActiveCount();

    @Aggregation(pipeline = {"{$match: {}}", "{$group: { _id: null, sum: { $sum: \"$Confirmed\"}}}"})
    TotalCount getTotalConfirmedCount();

    @Aggregation(pipeline = {"{$match: {}}", "{$group: { _id: null, sum: { $sum: \"$Recovered\"}}}"})
    TotalCount getTotalRecoveredCount();

    @Aggregation(pipeline = {"{$match: {}}", "{$group: { _id: null, sum: { $sum: \"$Deaths\"}}}"})
    TotalCount getTotalDeathsCount();

    @Aggregation(pipeline = {"{$match: {}}", "{$group: { _id: null, average: { $avg: \"$Incident_Rate\"}}}"})
    TotalCount getTotalIncidentRate();

    @Aggregation(pipeline = {"{$match: {}}", "{$group: { _id: null, average: { $avg: \"$Case_Fatality_Ratio\"}}}"})
    TotalCount getTotalCaseFatalityRatio();


    //{ $match: {date: 20210102}}, {$group: { _id: "$Country_Region", activeCount: { $sum: "$Active"}}}
    //{ $match: {date: 20210102}}, {$group: { _id: "$Province_State", activeCount: { $sum: "$Active"}}}
}
