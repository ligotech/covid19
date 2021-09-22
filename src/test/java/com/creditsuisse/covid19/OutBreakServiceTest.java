package com.creditsuisse.covid19;

import com.creditsuisse.covid19.outbreak.OutBreak;
import com.creditsuisse.covid19.outbreak.OutBreakService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class OutBreakServiceTest {

    @Mock
    MongoTemplate mongoTemplate;

    @Mock
    OutBreakService outBreakService;

    @Autowired
    MongoTemplate mongoTemplateAutoWired;

    @Autowired
    OutBreakService outBreakServiceAutoWired;

    @Test
    public void outBreakServiceTest(){
        Assertions.assertNotNull(outBreakServiceAutoWired);
    }

    @Test
    public void mongoTemplateTest(){
        Assertions.assertNotNull(mongoTemplateAutoWired);
    }

    @Test
    public void getTotalActiveWithCriteriaTest(){
        Map<String, String> params = Mockito.anyMap();
        Mockito.when(outBreakService.getTotalActiveWithCriteria(params)).thenReturn(Mockito.anyList());
        Assertions.assertNotNull(outBreakService.getTotalActiveWithCriteria(params));
    }

    @Test
    public void getTotalActiveTest(){
        Mockito.when(outBreakService.getTotalActive()).thenReturn(100L);
        Assertions.assertEquals(100L, outBreakService.getTotalActive());
    }

    @Test
    public void saveAllTest(){
        List<OutBreak> list = Mockito.anyList();
        Mockito.when(outBreakService.saveAll(list)).thenReturn(list);
        Assertions.assertEquals(list, outBreakService.saveAll(list));
    }
}
