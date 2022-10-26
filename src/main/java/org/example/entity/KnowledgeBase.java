package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@AllArgsConstructor(staticName = "of")
public class KnowledgeBase {

    private LinkedList<String> objectNames;
    private LinkedList<String> optionNames;

    @Setter
    private boolean[][] correlationTable;

}