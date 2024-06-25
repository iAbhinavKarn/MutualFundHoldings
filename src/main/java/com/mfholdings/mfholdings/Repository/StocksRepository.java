package com.mfholdings.mfholdings.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mfholdings.mfholdings.Entity.Stocks;

@Repository
public interface StocksRepository extends JpaRepository<Stocks, Long>{

    @Query("SELECT s FROM #{#entityName} s WHERE s.name = ?1")
    public Optional<Stocks> findByName(String name);

}
