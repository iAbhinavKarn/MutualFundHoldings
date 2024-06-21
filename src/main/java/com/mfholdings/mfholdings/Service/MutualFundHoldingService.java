package com.mfholdings.mfholdings.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.mfholdings.mfholdings.Entity.MutualFund;
import com.mfholdings.mfholdings.Entity.Stocks;
import com.mfholdings.mfholdings.Repository.MutualFundRepository;

@Service
public class MutualFundHoldingService {

    private final MutualFundRepository mutualFundRepository;

    public MutualFundHoldingService(MutualFundRepository mutualFundRepository){
        this.mutualFundRepository = mutualFundRepository;
    }

    public void getMutualFundHoldings() throws IOException{
        List<MutualFund> allMutualFunds = mutualFundRepository.findAll();
        // ExecutorService executor = Executors.newFixedThreadPool(10);
        // Future<List<String>> response = new Future();
        getHoldings(allMutualFunds.get(0));
    }



    public List<String> getHoldings(MutualFund mutualFund) throws IOException{
        Document doc = Jsoup.connect(mutualFund.getUrlString()).get();
        Elements rowClassData = doc.getElementsByClass("row pt-3");
        for(Element element : rowClassData){
            if(element.text().contains("Top 10 Holdings")){
                Elements d = element.getElementsByClass("col-xs-12 col-sm-12 col-md-6 col-lg-6");
                Elements rows = d.select("tr");
                for(Element row : rows){
                    Element nameCell = row.select("td").first();
                    if (nameCell != null) {
                        Stocks stock = new Stocks();
                        stock.setName(nameCell.text());
                    }
                }
            }
        }
        // System.out.println("text : " + rowClassData.text());
        // if(rowClassData.text().equalsIgnoreCase("Top 10 Holdings")){
        //     Elements rows = rowClassData.select("tr");
        //     for(Element row : rows){
        //         Elements cells = row.select("th, td");
        //         for(Element cell : cells){
        //             String name = cell.text();
        //             System.out.println("name : " + name);
        //         }
        //     }
        // }
        return new ArrayList<String>();
    }

}
