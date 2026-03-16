package com.gstech.AssistantAi.dto;

import java.util.List;

public record BBQOption(
        String title,
        List<String> beefs,
        List<String> porks,
        List<String> chickens,
        List<String> side
) {
}
