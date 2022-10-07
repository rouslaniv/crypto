package com.crypto.web.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@ConditionalOnProperty(value = "crypto.load-sample-data", havingValue = "true")
@Component
@RequiredArgsConstructor
public class SampleDataLoader {

    private final CryptoService cryptoService;

    @Value("classpath:sample-data/BTC_values.csv")
    private Resource BTCValues;
    @Value("classpath:sample-data/DOGE_values.csv")
    private Resource DOGEValues;
    @Value("classpath:sample-data/ETH_values.csv")
    private Resource ETHValues;
    @Value("classpath:sample-data/LTC_values.csv")
    private Resource LTCValues;
    @Value("classpath:sample-data/XRP_values.csv")
    private Resource XRPValues;

    @PostConstruct
    public void loadSampleData() {
        
//        if (duplicate or other) {
//            return;
//        }
    	try {
    		cryptoService.bulkUpload(BTCValues.getInputStream());
    		cryptoService.bulkUpload(DOGEValues.getInputStream());
    		cryptoService.bulkUpload(ETHValues.getInputStream());
    		cryptoService.bulkUpload(LTCValues.getInputStream());
    		cryptoService.bulkUpload(XRPValues.getInputStream());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

    }

}