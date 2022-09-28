package com.yoru.currency.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yoru.currency.dao.CurrencyDao;
import com.yoru.currency.entity.Currency;


@Service
public class CurrencyService {
	@Autowired
	CurrencyDao dao;
	
	public List<Currency> fetchCurrency(){
		return (List<Currency>) dao.findAll();
	}
	
	public String saveCurrency(Currency currency) {
		return dao.save(currency).getCode();
	}
	
	public Boolean updCurrency(Currency currency, String code) {
		Optional<Currency> curr = dao.findById(code);
		if(!curr.isPresent()) {
			return false;
		}
		Currency updateCurr = curr.get();
		if(!StringUtils.isEmpty(currency.getName())) {
			updateCurr.setName(currency.getName());
		}
		updateCurr.setRate(currency.getRate());
		dao.save(updateCurr);
		return true;
	}
	
	public Boolean delCurrency(String code) {
		if(!dao.findById(code).isPresent()) {
			return false;
		}
		dao.deleteById(code);
		return true;
	}
	
	public String callAPI() throws Exception {
		URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice.json");
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setDoInput(true);
		conn.setRequestMethod("GET");
		conn.connect();
		
		StringBuffer sb = new StringBuffer();
		InputStream in = conn.getInputStream();
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		
		String line = "";
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
		
		br.close();
		isr.close();
		in.close();
		conn.disconnect();
		
		return sb.toString();
	}
	
	public String transferAPI() throws Exception {
		String jsonStr = this.callAPI();
		
		JSONObject json = new JSONObject(jsonStr);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String updateTime = sdf.format(new Date(json.getJSONObject("time").getString("updated")));
		
		JSONObject bpi = json.getJSONObject("bpi");
		JSONObject usd = bpi.getJSONObject("USD");
		JSONObject gbp = bpi.getJSONObject("GBP");
		JSONObject eur = bpi.getJSONObject("EUR");

		Currency usdCurr = new Currency(usd.getString("code"),"",Double.parseDouble(usd.getString("rate").replaceAll(",","")));
		this.updCurrency(usdCurr,usd.getString("code"));
		Currency gbpCurr = new Currency(gbp.getString("code"),"",Double.parseDouble(gbp.getString("rate").replaceAll(",","")));
		this.updCurrency(gbpCurr,gbp.getString("code"));
		Currency eurCurr = new Currency(eur.getString("code"),"",Double.parseDouble(eur.getString("rate").replaceAll(",","")));
		this.updCurrency(eurCurr,eur.getString("code"));
		
		return updateTime;
	}
}
