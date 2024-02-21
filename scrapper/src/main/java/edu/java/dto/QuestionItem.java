package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record QuestionItem(List<QuestionResponse> items) {
    public record QuestionResponse(
        @JsonProperty("question_id")
        Long id,
        @JsonProperty("last_activity_date")
        OffsetDateTime updatedAt) {
    }
}
