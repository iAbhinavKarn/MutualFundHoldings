package com.mfholdings.mfholdings.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfholdings.mfholdings.Entity.Stocks;

@Repository
public interface StocksRepository extends JpaRepository<Stocks, Long>{

}
