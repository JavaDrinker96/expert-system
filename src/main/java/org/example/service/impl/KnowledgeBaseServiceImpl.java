package org.example.service.impl;

import org.example.entity.KnowledgeBase;
import org.example.service.KnowledgeBaseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    @Override
    public String findMinimumFrequencyOption(final KnowledgeBase knowledgeBase) {

        final LinkedList<String> optionNames = knowledgeBase.getOptionNames();
        final boolean[][] table = knowledgeBase.getCorrelationTable();

        int minimalFrequency = Integer.MAX_VALUE;
        int minimalFrequencyOptionIndex = -1;

        for (int optionIndex = 0; optionIndex < optionNames.size(); optionIndex++) {

            int frequency = 0;
            for (int rowIndex = 0; rowIndex < table.length; rowIndex++) {

                if (table[rowIndex][optionIndex]) {
                    frequency++;
                }
            }

            if (frequency < minimalFrequency) {
                minimalFrequency = frequency;
                minimalFrequencyOptionIndex = optionIndex;
            }
        }

        return optionNames.get(minimalFrequencyOptionIndex);
    }

    @Override
    public void removeEmptyOptions(final KnowledgeBase knowledgeBase) {

        final LinkedList<String> optionNames = knowledgeBase.getOptionNames();
        final boolean[][] table = knowledgeBase.getCorrelationTable();
        final List<String> removedOptions = new ArrayList<>();

        for (int optionIndex = 0; optionIndex < optionNames.size(); optionIndex++) {

            int frequency = 0;
            for (int rowIndex = 0; rowIndex < table.length; rowIndex++) {

                if (table[rowIndex][optionIndex]) {
                    frequency++;
                }
            }

            if (frequency == 0) {
                removedOptions.add(optionNames.get(optionIndex));
            }
        }

        for (final String removedOption : removedOptions) {
            removeOptionByName(knowledgeBase, removedOption);
        }
    }

    @Override
    public void removeObjectsWithoutOption(final KnowledgeBase knowledgeBase, final String optionName) {

        final List<String> objectWithoutOptionList = findObjectsWithoutOption(knowledgeBase, optionName);

        for (final String objectWithoutOption : objectWithoutOptionList) {

            removeObjectByName(knowledgeBase, objectWithoutOption);
        }
    }

    @Override
    public void removeObjectWithOption(final KnowledgeBase knowledgeBase, final String optionName) {

        final List<String> objectWithOptionList = new LinkedList<>(knowledgeBase.getObjectNames());
        final List<String> objectWithoutOptionList = findObjectsWithoutOption(knowledgeBase, optionName);
        objectWithOptionList.removeAll(objectWithoutOptionList);

        for (final String objectWithOption : objectWithOptionList) {

            removeObjectByName(knowledgeBase, objectWithOption);
        }
    }

    public List<String> findObjectsWithoutOption(final KnowledgeBase knowledgeBase, final String optionName) {

        final List<String> objectWithoutOptionList = new ArrayList<>();

        final LinkedList<String> objectNames = knowledgeBase.getObjectNames();
        final int optionIndex = knowledgeBase.getOptionNames().indexOf(optionName);
        final boolean[][] table = knowledgeBase.getCorrelationTable();

        for (int objectIndex = 0; objectIndex < objectNames.size(); objectIndex++) {

            final boolean objectNotHasOption = table[objectIndex][optionIndex];

            if (!objectNotHasOption) {
                objectWithoutOptionList.add(objectNames.get(objectIndex));
            }
        }

        return objectWithoutOptionList;
    }

    private void removeOptionByName(final KnowledgeBase knowledgeBase, final String optionName) {

        final int optionIndex = knowledgeBase.getOptionNames().indexOf(optionName);
        final boolean[][] newTable = deleteColumn(knowledgeBase.getCorrelationTable(), optionIndex);

        knowledgeBase.getOptionNames().remove(optionIndex);
        knowledgeBase.setCorrelationTable(newTable);
    }

    private void removeObjectByName(final KnowledgeBase knowledgeBase, final String objectName) {

        final int objectIndex = knowledgeBase.getObjectNames().indexOf(objectName);
        final boolean[][] newTable = deleteRow(knowledgeBase.getCorrelationTable(), objectIndex);

        knowledgeBase.getObjectNames().remove(objectIndex);
        knowledgeBase.setCorrelationTable(newTable);
    }

    private boolean[][] deleteColumn(final boolean[][] table, final int deleteIndex) {

        final int rowsCount = table.length;
        final int columnsCount = table[0].length - 1;

        final boolean[][] newTable = new boolean[rowsCount][columnsCount];

        for (int rowIndex = 0; rowIndex < rowsCount; rowIndex++) {

            for (int newColumnIndex = 0, oldColumnIndex = 0; newColumnIndex < columnsCount; newColumnIndex++, oldColumnIndex++) {

                if (newColumnIndex == deleteIndex) {
                    oldColumnIndex++;
                }

                newTable[rowIndex][newColumnIndex] = table[rowIndex][oldColumnIndex];
            }
        }

        return newTable;
    }

    private boolean[][] deleteRow(final boolean[][] table, final int deleteIndex) {

        final int rowsCount = table.length - 1;
        final int columnsCount = table[0].length;

        final boolean[][] newTable = new boolean[rowsCount][columnsCount];

        for (int columIndex = 0; columIndex < columnsCount; columIndex++) {

            for (int newRowIndex = 0, oldRowIndex = 0; newRowIndex < rowsCount; newRowIndex++, oldRowIndex++) {

                if (newRowIndex == deleteIndex) {
                    oldRowIndex++;
                }

                newTable[newRowIndex][columIndex] = table[oldRowIndex][columIndex];
            }
        }

        return newTable;
    }

}
