package com.crypto.web.persistance.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.crypto.web.enums.CryptoSymbol;
import com.crypto.web.persistance.entity.base.BaseLong;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "crypto")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Crypto extends BaseLong {
	
	@Column(name = "timestamps", nullable = false)
    private LocalDateTime timestamp;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	private CryptoSymbol symbol;
	
	@Column(name = "price", precision = 15, scale = 5, nullable = false)
    private BigDecimal price;

	@Override
	public String toString() {
		return "Crypto [id=" + id + ", symbol=" + symbol + ", price=" + price + ", timestamp=" + timestamp  + "]";
	}
	
}
