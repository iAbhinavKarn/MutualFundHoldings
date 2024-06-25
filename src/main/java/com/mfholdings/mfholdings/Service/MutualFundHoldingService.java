package com.mfholdings.mfholdings.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.mfholdings.mfholdings.Entity.MutualFund;
import com.mfholdings.mfholdings.Repository.MutualFundRepository;
import com.mfholdings.mfholdings.Response.MutualFundsHoldingResponse;
import com.mfholdings.mfholdings.Wrapper.AsyncWrapper;

@Service
public class MutualFundHoldingService {

    private final MutualFundRepository mutualFundRepository;

    private final AsyncWrapper asyncWrapper;

    private final SaveStocksAsyncService saveStocksAsyncService;

    public MutualFundHoldingService(MutualFundRepository mutualFundRepository, AsyncWrapper asyncWrapper, SaveStocksAsyncService saveStocksAsyncService){
        this.mutualFundRepository = mutualFundRepository;
        this.asyncWrapper = asyncWrapper;
        this.saveStocksAsyncService = saveStocksAsyncService;
    }

    public MutualFundsHoldingResponse getMutualFundHoldings() throws IOException{
        List<MutualFund> allMutualFunds = mutualFundRepository.findAll();
        Function<MutualFund, Map<String,List<String>>> function = r -> {
            Map<String, List<String>> returnMap = new HashMap<>();
            try {
                returnMap = getHoldings(r);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return returnMap;
        };
        List<CompletableFuture<Map<String, List<String>>>> responses = Collections.synchronizedList(new ArrayList<>());
        for(MutualFund mutualFund : allMutualFunds){
            responses.add(asyncWrapper.execute(function, mutualFund));
        }

        MutualFundsHoldingResponse mutualFundsHoldingResponse = new MutualFundsHoldingResponse();
        List<Map<String, List<String>>> mutualFundHoldingList = new ArrayList<>();

        for(CompletableFuture<Map<String, List<String>>> response : responses){
            mutualFundHoldingList.add(response.join());
        }

        mutualFundsHoldingResponse.setMutualFundHoldings(mutualFundHoldingList);

        saveStocksAsyncService.saveStocks(mutualFundHoldingList);

        return mutualFundsHoldingResponse;
    }



    public Map<String, List<String>> getHoldings(MutualFund mutualFund) throws IOException{
        Document doc = Jsoup.connect(mutualFund.getUrlString()).get();
        Elements rowClassData = doc.getElementsByClass("row pt-3");
        List<String> returnList = new ArrayList<>();
        for(Element element : rowClassData){
            if(element.text().contains("Top 10 Holdings")){
                Elements d = element.getElementsByClass("col-xs-12 col-sm-12 col-md-6 col-lg-6");
                Elements rows = d.select("tr");
                for(Element row : rows){
                    Element nameCell = row.select("td").first();
                    if (nameCell != null) {
                        returnList.add(nameCell.text());
                    }
                }
            }
        }
        Map<String, List<String>> returnMap = new HashMap<>();
        returnMap.put(mutualFund.getName(), returnList);
        return returnMap;
    }
}
