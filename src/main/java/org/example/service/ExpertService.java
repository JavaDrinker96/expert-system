package org.example.service;

import org.example.entity.KnowledgeBase;

import java.util.LinkedList;

public interface ExpertService {

    KnowledgeBase study(LinkedList<String> objectNames, LinkedList<String> optionNames);

    void expertise(KnowledgeBase knowledgeBase);
}
