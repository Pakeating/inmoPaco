package com.inmopaco.AuctionService.infrastructure.scraper.providers.jsoup.chain.nodes;

import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Element;

@Log4j2
public abstract class AbstractJsoupAuctionDetailsNode {
    private static final String REFERER_HEADER = "";

//    TODO: Refactor
    protected String buildUrlForSection(String originalUrl, int sectionNumber) {
        if (originalUrl.contains("ver=")) {
            return originalUrl.replaceAll("ver=\\d+", "ver=" + sectionNumber);
        } else {
            return originalUrl + "&ver=" + sectionNumber;
        }
    }

    protected String extractValue(Element container, String label) {
        // Buscamos el th que contenga exactamente el texto de la etiqueta
        // Usamos :containsOwn para ser más precisos con el texto de la celda
        Element header = container.select("th:containsOwn(" + label + ")").first();

        if (header != null) {
            Element valueCell = header.nextElementSibling();
            if (valueCell != null) {
                return valueCell.text().trim();
            }
        }
        return "";
    }

    protected void randomDelay() throws InterruptedException {
        // Introduce un retraso aleatorio entre 1 y 3 segundos
        int delay = 500 + (int)(Math.random() * 1000);
        Thread.sleep(delay);
    }
}
