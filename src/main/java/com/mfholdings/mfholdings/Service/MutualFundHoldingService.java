package com.mfholdings.mfholdings.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.mfholdings.mfholdings.Entity.MutualFund;
import com.mfholdings.mfholdings.Entity.Stocks;
import com.mfholdings.mfholdings.Repository.MutualFundRepository;
import com.mfholdings.mfholdings.Repository.StocksRepository;
import com.mfholdings.mfholdings.Wrapper.AsyncWrapper;

@Service
public class MutualFundHoldingService {

    private final MutualFundRepository mutualFundRepository;

    private final AsyncWrapper asyncWrapper;

    private final StocksRepository stocksRepository;

    public MutualFundHoldingService(MutualFundRepository mutualFundRepository, AsyncWrapper asyncWrapper, StocksRepository stocksRepository){
        this.mutualFundRepository = mutualFundRepository;
        this.stocksRepository = stocksRepository;
        this.asyncWrapper = asyncWrapper;
    }

    public void getMutualFundHoldings() throws IOException{
        List<MutualFund> allMutualFunds = mutualFundRepository.findAll();
        Function<MutualFund, List<String>> function = r -> {
            List<String> returnList = new ArrayList<>();
            try {
                returnList = getHoldings(r);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return returnList;
        };
        List<CompletableFuture<List<String>>> responses = Collections.synchronizedList(new ArrayList<>());
        for(MutualFund mutualFund : allMutualFunds){
            responses.add(asyncWrapper.execute(function, mutualFund));
        }
    }



    public List<String> getHoldings(MutualFund mutualFund) throws IOException{
        System.out.println("INN here");
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
                        Stocks stocks = new Stocks();
                        stocks.setName(nameCell.text());
                        stocksRepository.save(stocks);
                    }
                }
            }
        }
        return returnList;
    }

}
