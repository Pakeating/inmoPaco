package com.inmopaco.AIService.domain.enums;

import java.util.Objects;
import java.util.Optional;

public enum AIRequestType {
    // enum con template de prompt. Meterlo con {} para indicar donde va el input
    AUCTIONS_DEBT_ANALYSIS("Answer in spanish. Summarize the liabilities someone should take into account in this text. Just name them with the associated amount, just 4 or 5 lines: {}")

    ;

    private final String promptTemplate;

    AIRequestType(String promptTemplate) {
        this.promptTemplate = promptTemplate;
    }

    public String format(String input) {
        return promptTemplate.replace("{}", Optional.ofNullable(input).orElse(""));
    }
}

