package com.mfholdings.mfholdings.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.mfholdings.mfholdings.Service.MutualFundHoldingService;

@RestController
public class MutualFundHoldingController {

    private final MutualFundHoldingService mutualFundHoldingService;

    public MutualFundHoldingController(MutualFundHoldingService mutualFundHoldingService){
        this.mutualFundHoldingService = mutualFundHoldingService;
    }

    public void getMutualFundHoldings(){
        mutualFundHoldingService.getMutualFundHoldings();
    }


}
