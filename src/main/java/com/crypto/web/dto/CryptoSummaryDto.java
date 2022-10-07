package com.crypto.web.dto;

import com.crypto.web.persistance.entity.Crypto;

import lombok.Data;

@Data
public class CryptoSummaryDto {
	Crypto oldest;
	Crypto latest;
    Crypto min;
    Crypto max;
}
