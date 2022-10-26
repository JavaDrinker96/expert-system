package org.example.service;

import org.example.entity.KnowledgeBase;

public interface KnowledgeBaseService {

    String findMinimumFrequencyOption(KnowledgeBase knowledgeBase);

    void removeEmptyOptions(KnowledgeBase knowledgeBase);

    void removeObjectsWithoutOption(KnowledgeBase knowledgeBase, String optionName);

    void removeObjectWithOption(KnowledgeBase knowledgeBase, String optionName);
}
