package com.yoru.currency;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoru.currency.entity.Currency;
import com.yoru.currency.service.CurrencyService;

@SpringBootTest
@AutoConfigureMockMvc
public class TestController {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CurrencyService service;
    
    public Currency getTestCurrency(){
    	Currency currency = new Currency();
    	currency.setCode("TWD");
    	currency.setName("台幣");
    	currency.setRate(1.0);
    	return currency;
    }
    
    //測試呼叫查詢幣別對應表資料 API，並顯示其內容
    @Test
    public void testGetCurrency() throws Exception {
    	
    	List<Currency> expectedList = new ArrayList<Currency>();
    	Currency currency = getTestCurrency();
    	expectedList.add(currency);

    	Mockito.when(service.saveCurrency(getTestCurrency())).thenReturn(null);
//    	Mockito.when(service.fetchCurrency()).thenReturn(expectedList);
    	
    	String returnStr = mockMvc.perform(
    			MockMvcRequestBuilders.get("/api/currency")
    			.accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk())
    			.andReturn().getResponse()
    			.getContentAsString(StandardCharsets.UTF_8)
    			;
    	Iterable<Currency> actualList = objectMapper.readValue(
    			returnStr, new TypeReference<Iterable<Currency>>() {
        });
        assertEquals(expectedList,  actualList);

        System.out.println(expectedList);
        System.out.println(actualList);
    }
    
//    //測試呼叫新增幣別對應表資料 API 
//    @Test
//    public void testInsertCurrency() throws Exception {    	
//    	JSONObject jsonObj = new JSONObject();
//    	jsonObj.put("code", "TWD");
//    	jsonObj.put("name", "台幣");
//    	jsonObj.put("rate", 1.0);
//        
//    	String actual = mockMvc.perform(MockMvcRequestBuilders.post("/api/currency")
//    			.accept(MediaType.APPLICATION_JSON)
//    			.contentType(MediaType.APPLICATION_JSON)
//    			.content(String.valueOf(jsonObj)))
//    			.andExpect(status().isCreated())
//    			.andReturn().getResponse().getContentAsString()
//    			;
//
//        assertEquals("TWD",  actual);
//
//    }
//    //測試呼叫更新幣別對應表資料 API，並顯示其內容
//    
//
//    //測試呼叫刪除幣別對應表資料 API
//    @Test
//    public void testDeleteCurrencySuccess() throws Exception {
//    	String rtn = "";
//    	Mockito.when(service.saveCurrency(getTestCurrency())).thenReturn(rtn);
//    	System.out.println("testDeleteCurrencySuccess  "+rtn);
//        Mockito.when(service.delCurrency("TWD")).thenReturn(true);
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/currency/TWD")
//                .accept(MediaType.APPLICATION_JSON_UTF8 )
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    }
}