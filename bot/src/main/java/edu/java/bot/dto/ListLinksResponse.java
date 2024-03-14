package edu.java.bot.dto;

import jakarta.validation.Valid;
import java.util.List;

public record ListLinksResponse(
    @Valid
    List<@Valid LinkResponse> links,

    Integer size
) {
}
