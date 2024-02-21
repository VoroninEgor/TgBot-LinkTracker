package edu.java.client.stackoverflow;

import edu.java.dto.QuestionItem;

public interface StackOverFlowClient {
    QuestionItem fetchQuestion(Long id);
}
