package com.creditsuisse.covid19;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OutBreakControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMockMvc(){
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    public void totalActiveTest() throws Exception{
        mockMvc.perform(get("/outbreak/active")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void totalConfirmedTest() throws Exception{
        mockMvc.perform(get("/outbreak/confirmed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void totalDeathsTest() throws Exception{
        mockMvc.perform(get("/outbreak/deaths")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void totalRecoveredTest() throws Exception{
        mockMvc.perform(get("/outbreak/recovered")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void totalIncidentRate() throws Exception{
        mockMvc.perform(get("/outbreak/incident-rate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void totalCaseFatalityRatio() throws Exception{
        mockMvc.perform(get("/outbreak/case-fatality-ratio")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void totalActiveWithCriteriaTest1() throws Exception{
        mockMvc.perform(get("/query/active")
                        .param("date", "20210102")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void totalActiveWithCriteriaTest2() throws Exception{
        mockMvc.perform(get("/query/active")
                        .param("date","20210102")
                        .param("country","Australia")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void totalActiveWithCriteriaTest3() throws Exception{
        mockMvc.perform(get("/query/active/")
                        .param("date","20210102")
                        .param("country","Australia")
                        .param("groupBy", "country")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void containmentZoneTest() throws Exception{
        mockMvc.perform(get("/query/containment-zones/")
                        .param("date","20210102")
                        .param("threshold","1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void readyToWorkTest1() throws Exception{
        mockMvc.perform(get("/query/ready-to-work/")
                        .param("latitude","41.1533")
                        .param("longitude","20.1683")
                        .param("radius", "10")
                        .param("population", "100000")
                        .param("threshold", "25")
                        .param("date", "20210102")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void readyToWorkTest2() throws Exception{
        mockMvc.perform(get("/query/ready-to-work/")
                        //.param("latitude","41.1533")
                        .param("longitude","20.1683")
                        .param("radius", "10")
                        .param("population", "100000")
                        .param("threshold", "25")
                        .param("date", "20210102")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
