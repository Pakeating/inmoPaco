package com.inmopaco.AuctionService.infrastructure.scraper;
import com.inmopaco.AuctionService.application.dto.AuctionDetailsDTO;

import java.util.List;

public interface AuctionScraperProviderService {
    public List<AuctionDetailsDTO> fetchSearchResults(String province);

}
