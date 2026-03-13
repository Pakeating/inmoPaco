package com.inmopaco.AuctionService.infrastructure.scraper.impl;

import com.inmopaco.AuctionService.application.dto.AuctionDetailsDTO;
import com.inmopaco.AuctionService.infrastructure.scraper.AuctionScraperProviderService;
import com.inmopaco.AuctionService.infrastructure.scraper.providers.jsoup.JsoupScraperProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionScraperProviderServiceImpl implements AuctionScraperProviderService {

    @Autowired
    private JsoupScraperProviderService jsoupProvider;
    @Override
    public List<AuctionDetailsDTO> fetchSearchResults(String province) {
        return jsoupProvider.fetchSearchResults(province);
    }

}
