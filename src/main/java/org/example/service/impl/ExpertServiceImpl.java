package org.example.service.impl;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import lombok.RequiredArgsConstructor;
import org.example.entity.KnowledgeBase;
import org.example.service.ExpertService;
import org.example.service.KnowledgeBaseService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class ExpertServiceImpl implements ExpertService {

    private final KnowledgeBaseService knowledgeBaseService;

    @Override
    public KnowledgeBase study(final LinkedList<String> objectNames, final LinkedList<String> optionNames) {

        boolean[][] correlationTable = new boolean[objectNames.size()][optionNames.size()];

        int rowNumber = 0;
        for (String objectName : objectNames) {

            int columnNumber = 0;
            for (String optionName : optionNames) {

                correlationTable[rowNumber][columnNumber] = askUser(objectName, optionName);
                columnNumber++;
            }
            rowNumber++;
        }

        return KnowledgeBase.of(objectNames, optionNames, correlationTable);
    }

    @Override
    public void expertise(final KnowledgeBase knowledgeBase) {

        while (knowledgeBase.getObjectNames().size() > 1) {
            knowledgeBaseService.removeEmptyOptions(knowledgeBase);
            final String optionWithMinFrequency = knowledgeBaseService.findMinimumFrequencyOption(knowledgeBase);
            final boolean objectHasOption = askUser("Your object", optionWithMinFrequency);

            if (objectHasOption) {
                knowledgeBaseService.removeObjectsWithoutOption(knowledgeBase, optionWithMinFrequency);
            } else {
                knowledgeBaseService.removeObjectWithOption(knowledgeBase, optionWithMinFrequency);
            }
        }

        if (knowledgeBase.getObjectNames().size() == 1) {
            informUser(knowledgeBase.getObjectNames().get(0), false);
        } else {
            informUser("unknown", true);
        }

    }

    private boolean askUser(final String objectName, final String optionName) {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UNDECORATED);

        alert.setTitle("Studying expert system");
        alert.setHeaderText(String.format("%s have/has %s?", objectName, optionName));
        alert.setContentText("Choose your option.");

        final ButtonType yesButton = new ButtonType("Yes");
        final ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);

        final ButtonType selectedButton = alert.showAndWait().get();
        return selectedButton.equals(yesButton);
    }

    private void informUser(final String objectName, final boolean isUnknownObject) {

        final Alert alert = new Alert(isUnknownObject ? Alert.AlertType.WARNING : Alert.AlertType.INFORMATION);

        alert.setTitle("Expertise");
        alert.setHeaderText(String.format("Your object is '%s'.", objectName));

        alert.show();
    }
}