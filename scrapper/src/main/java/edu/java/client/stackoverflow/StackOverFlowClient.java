package edu.java.client.stackoverflow;

import edu.java.dto.QuestionResponse;

public interface StackOverFlowClient {
    QuestionResponse fetchQuestion(Long id);
}
