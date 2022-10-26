package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.example.entity.KnowledgeBase;
import org.example.service.ExcelIOService;
import org.example.service.ExpertService;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

@Component
@FxmlView("main-stage.fxml")
@RequiredArgsConstructor
public class ViewController {

    @FXML
    public TextField knowledgeBasePath;

    @FXML
    public Button chooseKnowledgeBase;
    @FXML
    private TextField optionsNameFiled;
    @FXML
    private TextField objectsNameField;
    @FXML
    private TextField folderPathField;
    @FXML
    private TextField fileNameField;
    @FXML
    private ListView objectList;
    @FXML
    private ListView optionList;

    private final ExcelIOService excelIOService;
    private final ExpertService expertService;

    public void addOption(MouseEvent mouseEvent) {
        final String optionName = optionsNameFiled.getText();
        optionList.getItems().add(optionName);
    }

    public void deleteOption(MouseEvent mouseEvent) {
        final String optionName = optionsNameFiled.getText();
        optionList.getItems().remove(optionName);
    }

    public void addObject(MouseEvent mouseEvent) {
        final String objectName = objectsNameField.getText();
        objectList.getItems().add(objectName);
    }

    public void deleteObject(MouseEvent mouseEvent) {
        final String objectName = objectsNameField.getText();
        objectList.getItems().remove(objectName);
    }

    public void chooseFolder(MouseEvent mouseEvent) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        final File file = directoryChooser.showDialog(null);
        this.folderPathField.setText(file.getAbsolutePath());
    }

    public void studying(MouseEvent mouseEvent) throws IOException {

        final String folderPath = folderPathField.getText();
        final String fileName = fileNameField.getText();

        final LinkedList<String> objectNames = new LinkedList<>(objectList.getItems());
        final LinkedList<String> optionNames = new LinkedList<>(optionList.getItems());

        final KnowledgeBase knowledgeBase = expertService.study(objectNames,optionNames);
        excelIOService.createFileByKnowledgeBase(knowledgeBase, folderPath, fileName);
    }

    public void chooseKnowledgeBase(MouseEvent mouseEvent) {

        final FileChooser fileChooser = new FileChooser();
        final FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Excel files", "*.xlsx");
        fileChooser.getExtensionFilters().add(extensionFilter);
        final File file = fileChooser.showOpenDialog(null);
        this.knowledgeBasePath.setText(file.getAbsolutePath());

    }

    public void runExpertSystem(MouseEvent mouseEvent) throws IOException {

        final String filePath = knowledgeBasePath.getText();
        final KnowledgeBase knowledgeBase = excelIOService.readKnowledgeBaseFromFile(filePath);

        expertService.expertise(knowledgeBase);
    }
}