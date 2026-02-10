package com.inmopaco.AuctionService.infrastructure.scraper.providers.jsoup.chain.impl;

import com.inmopaco.AuctionService.application.dto.AuctionDetailsDTO;
import com.inmopaco.AuctionService.application.dto.AuctionSummaryDTO;
import com.inmopaco.AuctionService.infrastructure.scraper.providers.jsoup.chain.JsoupScraperChainNode;
import com.inmopaco.AuctionService.infrastructure.scraper.providers.jsoup.chain.JsoupScraperChainService;
import com.inmopaco.AuctionService.infrastructure.scraper.providers.jsoup.chain.dto.JsoupChainContextDTO;
import com.inmopaco.AuctionService.infrastructure.scraper.providers.jsoup.chain.nodes.JsoupAuctionDetailsNodeVer1ChainNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Deque;
import java.util.LinkedList;


@Service
@RequiredArgsConstructor
public class JsoupScraperChainServiceImpl implements JsoupScraperChainService {
    private final Deque<JsoupScraperChainNode> nodeList = new LinkedList<>();
    private JsoupChainContextDTO context;

    //this should be done via reflection, but I prefer not to do it due to reflection problems with native compilation
    @Autowired
    public static JsoupAuctionDetailsNodeVer1ChainNode ver1Node;

    private JsoupScraperChainServiceImpl(AuctionSummaryDTO summary){
        this.context = new JsoupChainContextDTO(
                summary,
                AuctionDetailsDTO.builder()
        );
    }

    @Override
    public AuctionDetailsDTO execute() {
        while (!nodeList.isEmpty()) {
            JsoupScraperChainNode node = nodeList.pollFirst();
            node.execute(context);
        }
        return context.getBuilder().build();
    }

    @Override
    public JsoupScraperChainService add(JsoupScraperChainNode node) {
        nodeList.addLast(node);
        return this;
    }

    @Override
    public JsoupScraperChainService create(AuctionSummaryDTO summary) {
        var instance = new JsoupScraperChainServiceImpl(summary);
        instance.nodeList.clear();
        return instance;
    }

}
