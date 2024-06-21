package com.mfholdings.mfholdings.Controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfholdings.mfholdings.Service.MutualFundHoldingService;

@RestController
public class MutualFundHoldingController {

    private final MutualFundHoldingService mutualFundHoldingService;

    public MutualFundHoldingController(MutualFundHoldingService mutualFundHoldingService){
        this.mutualFundHoldingService = mutualFundHoldingService;
    }

    @GetMapping("/holdings")
    public void getMutualFundHoldings() throws IOException{
        mutualFundHoldingService.getMutualFundHoldings();
    }


}
