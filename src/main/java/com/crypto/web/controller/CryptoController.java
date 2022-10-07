package com.crypto.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.web.dto.CryptoSummaryDto;
import com.crypto.web.enums.CryptoSymbol;
import com.crypto.web.persistance.entity.Crypto;
import com.crypto.web.service.CryptoService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = CryptoController.BASE_URL)
@Api(value = CryptoController.BASE_URL)
public class CryptoController {
	public static final String BASE_URL = "/crypto";
	
	private final CryptoService cryptoService;
	
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Crypto>> allCryptos() {
		return new ResponseEntity<>(cryptoService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/summary/{symbol}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<CryptoSummaryDto> summaryForCrypto(@PathVariable CryptoSymbol symbol) {
		return new ResponseEntity<>(cryptoService.summary(symbol), HttpStatus.OK);
	}
	

	@GetMapping(value = "/highest", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Crypto>> highestForDay(
			@RequestParam Integer year, 
			@RequestParam Integer month, 
			@RequestParam Integer day) {
	
		return new ResponseEntity<>(cryptoService.highestForDay(year, month, day), HttpStatus.OK);
	}
	
	// to support a particular date range would add corresponding start and end dates
	// and change service and repository method accordingly
	@GetMapping(value = "/all-normilized", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Crypto>> allNormalized() {
		return new ResponseEntity<>(cryptoService.allNormalized(), HttpStatus.OK);
	}

}
