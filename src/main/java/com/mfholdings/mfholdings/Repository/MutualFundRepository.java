package com.mfholdings.mfholdings.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfholdings.mfholdings.Entity.MutualFund;

@Repository
public interface MutualFundRepository extends JpaRepository<MutualFund, Long>{
    
}
