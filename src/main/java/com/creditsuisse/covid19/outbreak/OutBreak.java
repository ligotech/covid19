package com.creditsuisse.covid19.outbreak;

import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvIgnore;
import com.opencsv.bean.CsvNumber;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

@Document(collection = "covid19data")
public class OutBreak {

    private String FIPS;
    private String Admin2;
    private String Province_State;
    private String Country_Region;
    @Transient
    @CsvDate("yyyy-MM-dd HH:mm:ss")
    private Date Last_Update;
    @CsvIgnore
    private Integer date;
    @CsvNumber("###.##")
    private Double Lat;
    @CsvNumber("###.##")
    private Double Long_;
    @CsvNumber("###.##")
    private Long Confirmed;
    @CsvNumber("###.##")
    private Long Deaths;
    @CsvNumber("###.##")
    private Long Recovered;
    @CsvNumber("###.##")
    private Long Active;
    private String Combined_Key;
    @CsvNumber("###.##")
    private Double Incident_Rate;
    @CsvNumber("###.##")
    private Double Case_Fatality_Ratio;

    public OutBreak(){
        //used for jackson deserialization
    }
    public OutBreak(String FIPS, String admin2, String province_State, String country_Region, Date last_Update, Double lat, Double long_, Long confirmed, Long deaths, Long recovered, Long active, String combined_Key, Double incident_Rate, Double case_Fatality_Ratio) {
        this.FIPS = FIPS;
        Admin2 = admin2;
        Province_State = province_State;
        Country_Region = country_Region;
        Last_Update = last_Update;
        Lat = lat;
        Long_ = long_;
        Confirmed = confirmed;
        Deaths = deaths;
        Recovered = recovered;
        Active = active;
        Combined_Key = combined_Key;
        Incident_Rate = incident_Rate;
        Case_Fatality_Ratio = case_Fatality_Ratio;
    }

    public String getFIPS() {
        return FIPS;
    }

    public void setFIPS(String FIPS) {
        this.FIPS = FIPS;
    }

    public String getAdmin2() {
        return Admin2;
    }

    public void setAdmin2(String admin2) {
        Admin2 = admin2;
    }

    public String getProvince_State() {
        return Province_State;
    }

    public void setProvince_State(String province_State) {
        Province_State = province_State;
    }

    public String getCountry_Region() {
        return Country_Region;
    }

    public void setCountry_Region(String country_Region) {
        Country_Region = country_Region;
    }

    public Date getLast_Update() {
        return Last_Update;
    }

    public void setLast_Update(Date last_Update) {
        Last_Update = last_Update;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String strDate = format.format(last_Update);
        date = Integer.valueOf(strDate);
    }

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public Double getLong_() {
        return Long_;
    }

    public void setLong_(Double long_) {
        Long_ = long_;
    }

    public Long getConfirmed() {
        return Confirmed;
    }

    public void setConfirmed(Long confirmed) {
        Confirmed = confirmed;
    }

    public Long getDeaths() {
        return Deaths;
    }

    public void setDeaths(Long deaths) {
        Deaths = deaths;
    }

    public Long getRecovered() {
        return Recovered;
    }

    public void setRecovered(Long recovered) {
        Recovered = recovered;
    }

    public Long getActive() {
        return Active;
    }

    public void setActive(Long active) {
        Active = active;
    }

    public String getCombined_Key() {
        return Combined_Key;
    }

    public void setCombined_Key(String combined_Key) {
        Combined_Key = combined_Key;
    }

    public Double getIncident_Rate() {
        return Incident_Rate;
    }

    public void setIncident_Rate(Double incident_Rate) {
        Incident_Rate = incident_Rate;
    }

    public Double getCase_Fatality_Ratio() {
        return Case_Fatality_Ratio;
    }

    public void setCase_Fatality_Ratio(Double case_Fatality_Ratio) {
        Case_Fatality_Ratio = case_Fatality_Ratio;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OutBreak{" +
                "FIPS='" + FIPS + '\'' +
                ", Admin2='" + Admin2 + '\'' +
                ", Province_State='" + Province_State + '\'' +
                ", Country_Region='" + Country_Region + '\'' +
                ", date='" + date + '\'' +
                ", Lat=" + Lat +
                ", Long_=" + Long_ +
                ", Confirmed=" + Confirmed +
                ", Deaths=" + Deaths +
                ", Recovered='" + Recovered + '\'' +
                ", Active=" + Active +
                ", Combined_Key='" + Combined_Key + '\'' +
                ", Incident_Rate=" + Incident_Rate +
                ", Case_Fatality_Ratio=" + Case_Fatality_Ratio +
                '}';
    }
}
