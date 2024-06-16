package com.mfholdings.mfholdings.Service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.mfholdings.mfholdings.Entity.MutualFund;
import com.mfholdings.mfholdings.Repository.MutualFundRepository;

@Service
public class GetPageDataService {

    private final MutualFundRepository mutualFundRepository;

    public GetPageDataService(MutualFundRepository mutualFundRepository){
        this.mutualFundRepository = mutualFundRepository;
    }

    public void getPageData() throws IOException{

        String url = "https://mfiframes.mutualfundsindia.com/MutualFundIndia/TopFund.aspx";
        Document d = Jsoup.connect(url).get();
        Elements tableData = d.getElementsByClass("table table-bordered");
        Elements rows = tableData.select("tr");
        for(Element row : rows){
            Elements cells = row.select("th, td");
            for(Element cell : cells){
                String name = cell.text();
                Elements link = cell.getElementsByTag("a");
                String linkUrl = link.attr("href");
                if(link.isEmpty()){
                    continue;
                }
                MutualFund mutualFund = new MutualFund();
                mutualFund.setName(name);
                mutualFund.setUrlString(linkUrl);
                mutualFundRepository.save(mutualFund);
            }
        }
    }
    
}
