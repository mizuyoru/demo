package com.yoru.currency.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yoru.currency.entity.Currency;
import com.yoru.currency.service.CurrencyService;


@RestController
@RequestMapping("/api")
public class CurrencyController {
	@Autowired
	CurrencyService service;


	@GetMapping("/currency")
	public ResponseEntity getCurrency() {
		return ResponseEntity.ok(service.fetchCurrency());
	}
	
	@PostMapping("/currency")
    public ResponseEntity createCurrency(@RequestBody Currency currency) {
        String code = service.saveCurrency(currency);
        return ResponseEntity.status(HttpStatus.CREATED).body(code);
    }

	@PutMapping("/currency/{code}")
    public ResponseEntity upadteCurrency(@PathVariable String code, @RequestBody Currency currency) {
        Boolean rlt = service.updCurrency(currency, code);
        if (!rlt) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(code+"幣別不存在");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ArrayList());
//        return ResponseEntity.ok("");
    }
	
	@DeleteMapping("/currency/{code}")
    public ResponseEntity deleteCurrency(@PathVariable String code) {
        Boolean rlt = service.delCurrency(code);
        if (!rlt) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(code+"幣別不存在");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList());
    }
	
	@GetMapping("/callAPI")
	public String callAPI() throws Exception {
		return service.callAPI();
	}
	
	@GetMapping("/transferAPI")
	public ResponseEntity<List<String>> transferAPI() throws Exception {
		
		List<String> res = new ArrayList<String>();
		res.add(service.transferAPI());
		
		return ResponseEntity.ok(res);
		
	}
}
