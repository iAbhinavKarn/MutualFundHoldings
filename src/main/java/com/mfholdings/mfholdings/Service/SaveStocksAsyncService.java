package com.mfholdings.mfholdings.Service;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mfholdings.mfholdings.Clients.EmailService;
import com.mfholdings.mfholdings.Entity.Stocks;
import com.mfholdings.mfholdings.Repository.StocksRepository;

@Service
public class SaveStocksAsyncService {

    private final StocksRepository stocksRepository;

    private final EmailService emailService;

    public SaveStocksAsyncService(StocksRepository stocksRepository, EmailService emailService){
        this.stocksRepository = stocksRepository;
        this.emailService = emailService;
    }

    @Async
    public void saveStocks(List<Map<String, List<String>>> stocksList){
        List<String> stocksName = new ArrayList<>();
        List<String> stocks = allStocksDAO(stocksRepository.findAll());
        for(Map<String, List<String>> stockHolding : stocksList){
            for(String key : stockHolding.keySet()){
                List<String>  data = stockHolding.get(key);
                for(String stockName : data){
                    stocksName.add(stockName);
                    if(stocksRepository.findByName(stockName).isPresent()){
                        continue;
                    }
                    Stocks stock = new Stocks();
                    stock.setName(stockName);
                    stocksRepository.save(stock);
                }
            }
        }

        List<String> stocksPresentOld = new ArrayList<>(stocks);
        List<String> stocksPresent = new ArrayList<>(stocksName);
        stocksPresentOld.removeAll(stocksName);
        stocksPresent.removeAll(stocks);
        emailService.sendEmail(createHTMLTable(stocksPresentOld, stocksPresent));

    }

    public List<String> allStocksDAO(List<Stocks> stocks){
        List<String> returnList = new ArrayList<>();
        for(Stocks stock : stocks){
            returnList.add(stock.getName());
        }
        return returnList;
    }

    public String createHTMLTable(List<String> oldStocks, List<String> newStock){
        oldStocks.add(0, "Stocks Removed");
        newStock.add(0, "Stocks Added");
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1' style='border-collapse: collapse;'>");

        for (String row : oldStocks) {
            sb.append("<tr>");
            sb.append("<td style='padding: 8px;'>").append(row).append("</td>");
            sb.append("</tr>");
        }

        sb.append("</table>");

        sb.append("<br>");
        sb.append("<br>");
        sb.append("<br>");
        sb.append("<br>");
        sb.append("<br>");

        sb.append("<table border='1' style='border-collapse: collapse;'>");

        for (String row : newStock) {
            sb.append("<tr>");
            sb.append("<td style='padding: 8px;'>").append(row).append("</td>");
            sb.append("</tr>");
        }

        sb.append("</table>");
        return sb.toString();
    }

}
