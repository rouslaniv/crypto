package com.crypto.web.service;

import java.io.InputStream;
import java.util.List;

import com.crypto.web.persistance.entity.Crypto;

public interface Parser {
	
	List<Crypto> parse(InputStream is);

}
