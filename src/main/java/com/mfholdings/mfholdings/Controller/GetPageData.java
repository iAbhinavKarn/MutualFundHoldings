package com.mfholdings.mfholdings.Controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfholdings.mfholdings.Service.GetPageDataService;

@RestController
public class GetPageData {

    private final GetPageDataService getPageDataService;

    public GetPageData(GetPageDataService getPageDataService){
        this.getPageDataService = getPageDataService;
    }

    @GetMapping
    public void getPageData() throws IOException{
        getPageDataService.getPageData();
    }
    
}
