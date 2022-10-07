package com.crypto.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import lombok.SneakyThrows;

@SpringBootTest
@AutoConfigureMockMvc
class CryptoControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@SneakyThrows
	void testGetAll() {
		mockMvc.perform(get("/crypto"))
		.andExpect(status().isOk());
	}
	
	@Test
	@SneakyThrows
	void testSummaryForCrypto() {
		String symbol = "BTC";
		mockMvc.perform(get("/crypto/summary/{symbol}", symbol))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.oldest.id").value("1"))
		.andExpect(jsonPath("$.latest.id").value("100"))
		.andExpect(jsonPath("$.min.id").value("78"))
		.andExpect(jsonPath("$.max.id").value("6"));
	}

	@Test
	@SneakyThrows
	void testHighestForDay() {
		mockMvc.perform(get("/crypto/highest")
				.param("year", "2022")
				.param("month", "1")
				.param("day", "1"))
		.andExpect(content().contentType("application/json"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id").value("1"))
		.andExpect(jsonPath("$[0].symbol").value("BTC"));
	}
	
	@Test
	@SneakyThrows
	void testallNormalized() {
		mockMvc.perform(get("/crypto/all-normilized"))
		.andExpect(content().contentType("application/json"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id").value("373"))
		.andExpect(jsonPath("$[0].symbol").value("XRP"));

	}

}