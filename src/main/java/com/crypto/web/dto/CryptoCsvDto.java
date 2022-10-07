package com.crypto.web.dto;

import lombok.Data;

@Data
public class CryptoCsvDto {
	private String timestamp;
	private String symbol;
	private String price;
}
