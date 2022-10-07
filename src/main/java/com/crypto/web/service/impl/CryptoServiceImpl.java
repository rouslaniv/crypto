package com.crypto.web.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.crypto.web.dto.CryptoSummaryDto;
import com.crypto.web.enums.CryptoSymbol;
import com.crypto.web.persistance.entity.Crypto;
import com.crypto.web.persistance.repositroy.CryptoRepository;
import com.crypto.web.service.CryptoService;
import com.crypto.web.service.Parser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CryptoServiceImpl implements CryptoService {
	
	private final CryptoRepository cryptoRepository;
	
	private final Parser parser;
	
	@Override
	public List<Crypto> getAll() {
		return cryptoRepository.findAll();
	}
	
	@Override
	public void bulkUpload(InputStream is) {
		cryptoRepository.saveAll(parser.parse(is));
	}

	@Override
	public Crypto oldest(CryptoSymbol symbol) {
		return cryptoRepository.oldest(symbol.toString());
	}

	@Override
	public Crypto latest(CryptoSymbol symbol) {
		return cryptoRepository.latest(symbol.toString());
	}

	@Override
	public Crypto min(CryptoSymbol symbol) {
		return cryptoRepository.min(symbol.toString());
	}

	@Override
	public Crypto max(CryptoSymbol symbol) {
		return cryptoRepository.max(symbol.toString());
	}

	@Override
	public CryptoSummaryDto summary(CryptoSymbol symbol) {
		CryptoSummaryDto summaryDto = new CryptoSummaryDto();
		summaryDto.setLatest(latest(symbol));
		summaryDto.setOldest(oldest(symbol));
		summaryDto.setMin(min(symbol));
		summaryDto.setMax(max(symbol));
		return summaryDto;
	}

	@Override
	public List<Crypto> highestForDay(Integer year, Integer month, Integer day) {
		return normalizePrice(cryptoRepository.highestForDay(year, month, day));
	}
	
	private List<Crypto> normalizePrice(List<Crypto> values) {
		
		// normalized = (x-min(x))/(max(x)-min(x))
		
		BigDecimal min = values.stream().map(crypto -> crypto.getPrice()).min(BigDecimal::compareTo).get();
		BigDecimal max = values.stream().map(crypto -> crypto.getPrice()).max(BigDecimal::compareTo).get();
		BigDecimal maxMinDiff = max.subtract(min);
		
		values.stream().forEach(crypto -> {
			BigDecimal diff = crypto.getPrice().subtract(min);
			crypto.setPrice(diff.divide(maxMinDiff, 2, RoundingMode.HALF_EVEN));
		});
		
		return values;
	}

	@Override
	public List<Crypto> allNormalized() {
		
		// Exposes an endpoint that will return a descending sorted list of all the cryptos,
		// comparing the normalized range (i.e. (max-min)/min)
		
		// if the above means 
		// 1. get all cryptos
		// 2. group by symbol, normalize each group individually
		// 3. make one list, sort desc by normilized price 
		// then
		
		List<Crypto> cryptos = cryptoRepository.findAll();
		
		Map<CryptoSymbol, List<Crypto>> cryptoMap = cryptos.stream().collect(Collectors.groupingBy(Crypto::getSymbol));
		cryptoMap.values().forEach(this::normalizePrice);
		List<Crypto> normilized = cryptoMap.values().stream().flatMap(List::stream).collect(Collectors.toList());
	    Comparator<Crypto> comparator = Comparator.comparing(Crypto::getPrice).thenComparing(Crypto::getSymbol);
//	      = (c1, c2) -> c1.getPrice().compareTo(c2.getPrice());
	    
	    normilized.sort(comparator.reversed());

		return normilized;

	}

}
