package com.mfholdings.mfholdings.Service;

import java.util.Map;
import java.util.List;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mfholdings.mfholdings.Entity.Stocks;
import com.mfholdings.mfholdings.Repository.StocksRepository;

@Service
public class SaveStocksAsyncService {

    private final StocksRepository stocksRepository;

    public SaveStocksAsyncService(StocksRepository stocksRepository){
        this.stocksRepository = stocksRepository;
    }

    @Async
    public void saveStocks(List<Map<String, List<String>>> stocksList){
        for(Map<String, List<String>> stockHolding : stocksList){
            for(String key : stockHolding.keySet()){
                List<String>  data = stockHolding.get(key);
                for(String stockName : data){
                    if(stocksRepository.findByName(stockName).isPresent()){
                        continue;
                    }
                    Stocks stock = new Stocks();
                    stock.setName(stockName);
                    stocksRepository.save(stock);
                }
            }
        }
    }

}
