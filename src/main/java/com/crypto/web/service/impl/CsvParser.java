package com.crypto.web.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import com.crypto.web.enums.CryptoSymbol;
import com.crypto.web.persistance.entity.Crypto;
import com.crypto.web.service.Parser;

@Component
public class CsvParser implements Parser {

	@Override
	public List<Crypto> parse(InputStream is) {
//        reader = new BufferedReader(new InputStreamReader(inputStream), StandardCharsets.UTF_8);
		Reader in;
		Iterable<CSVRecord> records;
		List<Crypto> result = new ArrayList<>();
		
		try {
			
			in = new BufferedReader(new InputStreamReader(is));
			
			records = CSVFormat
			    		.DEFAULT
			    		.withHeader("timestamp", "symbol", "price")
			    		.withFirstRecordAsHeader()
			    		.parse(in);
		    for (CSVRecord record : records) {
		    	
		    	Long milliseconds = Long.parseLong(record.get("timestamp"));
		    	LocalDateTime timestamp = Instant.ofEpochMilli(milliseconds)
		    			.atZone(ZoneId.systemDefault()).toLocalDateTime();
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(record.get("price")));
		        CryptoSymbol symbol = CryptoSymbol.valueOf(record.get("symbol"));
		        Crypto crypto = new Crypto();
		        crypto.setTimestamp(timestamp);
		        crypto.setPrice(price);
		        crypto.setSymbol(symbol);
		        result.add(crypto);
		        
		    }
		    
		} catch (FileNotFoundException nfe) {
			nfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
	    return result;
	}

}
