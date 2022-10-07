package com.crypto.web.persistance.repositroy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crypto.web.persistance.entity.Crypto;

@Repository
public interface CryptoRepository extends JpaRepository<Crypto, Long>{
	
    @Query(value =
            "select c.id as id," +
                    " c.timestamps as timestamp," +
                    " c.symbol as symbol," +
                    " c.price as price, "+
                    " from crypto c " +
                    " where c.symbol = :symbol " + 
                    " order by c.timestamps desc limit 1",
            nativeQuery = true)
    Crypto latest(String symbol);
    
    @Query(value =
            "select c.id as id," +
                    " c.timestamps as timestamp," +
                    " c.symbol as symbol," +
                    " c.price as price, "+
                    " from crypto c " +
                    " where c.symbol = :symbol " + 
                    " order by c.timestamps asc limit 1",
            nativeQuery = true)
    Crypto oldest(String symbol);
    
    @Query(value =
            "select c.id as id," +
                    " c.timestamps as timestamp," +
                    " c.symbol as symbol," +
                    " c.price as price, "+
                    " from crypto c " +
                    " where c.symbol = :symbol " + 
                    " order by c.price asc limit 1",
            nativeQuery = true)
    Crypto min(String symbol);
    
    @Query(value =
            "select c.id as id," +
                    " c.timestamps as timestamp," +
                    " c.symbol as symbol," +
                    " c.price as price, "+
                    " from crypto c " +
                    " where c.symbol = :symbol " + 
                    " order by c.price desc limit 1",
            nativeQuery = true)
    Crypto max(String symbol);
    
    @Query(value =
            "select c.id as id," +
                  " c.timestamps as timestamp," +
                  " c.symbol as symbol," +
                  " c.price as price, "+
                  " from crypto c " +
                  " where extract(year from c.timestamps) = :year " +
                  " and extract(month from c.timestamps) = :month " + 
                  " and extract(day from c.timestamps) = :day " + 
                  " and c.symbol = (" + 
                		  " select symbol from crypto " +  
                		  " where extract(year from timestamps) = :year " +
                          " and extract(month from timestamps) = :month " + 
                          " and extract(day from timestamps) = :day " + 
                		  " order by price desc limit 1 " +
                		  ")",
            nativeQuery = true)
    List<Crypto> highestForDay(Integer year, Integer month, Integer day);
    
}
