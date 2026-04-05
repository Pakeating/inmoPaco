package com.inmopaco.AuctionService.infrastructure.pdf;

import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Log4j2
@Service
public class PdfProcessingServiceImpl implements PdfProcessingService {

    private final HttpClient httpClient;

    public PdfProcessingServiceImpl() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Override
    public String getTextFromPdfUrl(String pdfUrl) {
        try {
            log.info("Processing PDF from URL: {}", pdfUrl);
            byte[] pdfBytes = downloadPdf(pdfUrl);
            return extractTextFromPdf(pdfBytes);
        } catch (Exception e) {
            log.error("Failed to process PDF from URL: {}", pdfUrl, e);
            throw new RuntimeException("Error processing PDF", e);
        }
    }

    private byte[] downloadPdf(String pdfUrl) throws Exception {
        log.debug("Downloading PDF...");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(pdfUrl))
                .GET()
                .timeout(Duration.ofSeconds(30))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .header("Accept", "application/pdf")
                .build();

        HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
        
        if (response.statusCode() != 200) {
            throw new IOException("Failed to download PDF. HTTP status: " + response.statusCode());
        }

        return response.body();
    }

    private String extractTextFromPdf(byte[] pdfData) throws IOException {
        log.debug("Extracting text from PDF data of size {} bytes", pdfData.length);
        try (PDDocument document = Loader.loadPDF(pdfData)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String extractedText = pdfStripper.getText(document);
            log.debug("Successfully extracted {} characters from PDF", extractedText.length());
            return extractedText;
        }
    }
}
