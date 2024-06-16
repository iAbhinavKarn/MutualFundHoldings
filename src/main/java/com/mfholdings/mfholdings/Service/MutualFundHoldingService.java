package com.mfholdings.mfholdings.Service;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.mfholdings.mfholdings.Entity.MutualFund;
import com.mfholdings.mfholdings.Repository.MutualFundRepository;

@Service
public class MutualFundHoldingService {

    private final MutualFundRepository mutualFundRepository;

    public MutualFundHoldingService(MutualFundRepository mutualFundRepository){
        this.mutualFundRepository = mutualFundRepository;
    }

    public void getMutualFundHoldings(){
        List<MutualFund> allMutualFunds = mutualFundRepository.findAll();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Future<List<String>> response = new Future();
    }

}
