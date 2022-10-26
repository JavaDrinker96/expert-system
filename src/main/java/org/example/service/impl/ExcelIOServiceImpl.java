package org.example.service.impl;

import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.KnowledgeBase;
import org.example.service.ExcelIOService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class ExcelIOServiceImpl implements ExcelIOService {

    @Override
    public KnowledgeBase readKnowledgeBaseFromFile(final String filePath) throws IOException {

        try (FileInputStream inputStream = new FileInputStream(filePath)) {

            final XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            final XSSFSheet sheet = workbook.getSheetAt(0);

            final int rowsCount = sheet.getLastRowNum() + 1;
            final int cellsCount = sheet.getRow(rowsCount - 1).getLastCellNum();

            final LinkedList<String> optionNames = readOptionNames(sheet.getRow(0));
            final LinkedList<String> objectNames = readObjectNames(sheet, rowsCount);
            final boolean[][] correlationTable = readCorrelationTable(sheet, rowsCount, cellsCount);

            return KnowledgeBase.of(objectNames, optionNames, correlationTable);
        }
    }

    private LinkedList<String> readOptionNames(final Row row) {

        final LinkedList<String> optionNames = new LinkedList<>();

        for (int cellIndex = 1; cellIndex < row.getLastCellNum(); cellIndex++) {

            final Cell cell = row.getCell(cellIndex);
            optionNames.add(cell.getStringCellValue());

        }

        return optionNames;
    }

    private LinkedList<String> readObjectNames(final Sheet sheet, final int rowsCount) {

        final LinkedList<String> objectNames = new LinkedList<>();

        for (int rowIndex = 1; rowIndex < rowsCount; rowIndex++) {

            final Row row = sheet.getRow(rowIndex);
            final Cell cell = row.getCell(0);
            final String objectName = cell.getStringCellValue();

            objectNames.add(objectName);
        }

        return objectNames;
    }

    private boolean[][] readCorrelationTable(final Sheet sheet, final int rowsCount, final int cellsCount) {

        final boolean[][] correlationTable = new boolean[rowsCount - 1][cellsCount-1];

        for (int rowIndex = 1; rowIndex < rowsCount; rowIndex++) {

            final Row row = sheet.getRow(rowIndex);

            for (int cellIndex = 1; cellIndex < cellsCount; cellIndex++) {

                final Cell cell = row.getCell(cellIndex);
                correlationTable[rowIndex - 1][cellIndex - 1] = cell.getBooleanCellValue();

            }
        }

        return correlationTable;
    }

    @Override
    public void createFileByKnowledgeBase(final KnowledgeBase knowledgeBase, final String folderPath, final String fileName) throws IOException {

        final String filePath = String.format("%s%s%s.xlsx", folderPath, File.separator, fileName);

        final Workbook workbook = new XSSFWorkbook();
        final Sheet sheet = workbook.createSheet("Knowledge base");

        final LinkedList<String> optionNames = knowledgeBase.getOptionNames();

        createHeader(workbook, sheet, optionNames);
        createRows(workbook, sheet, knowledgeBase);
        fitAllColumns(sheet, optionNames.size() + 1);
        saveFile(filePath, workbook);

    }

    private void createHeader(final Workbook workbook, final Sheet sheet, final LinkedList<String> optionNames) {

        final CellStyle headerCellStyle = initializeHeaderStyle(workbook);

        final Row headerRow = sheet.createRow(0);

        final Cell zeroCell = headerRow.createCell(0);
        zeroCell.setCellValue(Strings.EMPTY);

        for (int columnIndex = 0; columnIndex < optionNames.size(); columnIndex++) {
            Cell cell = headerRow.createCell(columnIndex + 1);
            cell.setCellValue(optionNames.get(columnIndex));
            cell.setCellStyle(headerCellStyle);
        }
    }

    private CellStyle initializeHeaderStyle(final Workbook workbook) {

        final Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 16);
        headerFont.setColor(IndexedColors.CORAL.getIndex());

        final CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        return headerCellStyle;
    }

    private void createRows(final Workbook workbook, final Sheet sheet, final KnowledgeBase knowledgeBase) {

        final CellStyle firstColumnStyle = initializeFirstColumnStyle(workbook);
        final List<String> objectNames = knowledgeBase.getObjectNames();

        final boolean[][] table = knowledgeBase.getCorrelationTable();

        for (int rowIndex = 0; rowIndex < objectNames.size(); rowIndex++) {

            final Row row = sheet.createRow(rowIndex + 1);

            for (int columnIndex = 0; columnIndex <= knowledgeBase.getOptionNames().size(); columnIndex++) {

                final Cell cell = row.createCell(columnIndex);

                if (columnIndex == 0) {
                    cell.setCellValue(objectNames.get(rowIndex));
                    cell.setCellStyle(firstColumnStyle);
                } else {
                    cell.setCellValue(table[rowIndex][columnIndex - 1]);
                }
            }
        }
    }

    private CellStyle initializeFirstColumnStyle(final Workbook workbook) {

        final Font firstColumnFont = workbook.createFont();
        firstColumnFont.setBold(true);
        firstColumnFont.setFontHeightInPoints((short) 16);
        firstColumnFont.setColor(IndexedColors.GREEN.getIndex());

        final CellStyle firstColumnStyle = workbook.createCellStyle();
        firstColumnStyle.setFont(firstColumnFont);

        return firstColumnStyle;
    }

    private void fitAllColumns(final Sheet sheet, final int columnsCount) {
        for (int i = 0; i < columnsCount; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void saveFile(final String path, final Workbook workbook) throws IOException {

        try (final FileOutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
        }
    }

}