package org.example.service;

import org.example.entity.KnowledgeBase;

import java.io.IOException;

public interface ExcelIOService {

    KnowledgeBase readKnowledgeBaseFromFile(String filePath) throws IOException;

    void createFileByKnowledgeBase(KnowledgeBase knowledgeBase, String folderPath, String fileName) throws IOException;

}