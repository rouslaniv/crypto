package com.crypto.web.service;

import java.io.InputStream;
import java.util.List;

import com.crypto.web.dto.CryptoSummaryDto;
import com.crypto.web.enums.CryptoSymbol;
import com.crypto.web.persistance.entity.Crypto;

public interface CryptoService {
	
	List<Crypto> getAll();
	Crypto oldest(CryptoSymbol symbol);
    Crypto latest(CryptoSymbol symbol);
    Crypto min(CryptoSymbol symbol);
    Crypto max(CryptoSymbol symbol);
    CryptoSummaryDto summary(CryptoSymbol symbol);
    List<Crypto> highestForDay(Integer year, Integer month, Integer day);
    List<Crypto> allNormalized();
	void bulkUpload(InputStream is);

}
