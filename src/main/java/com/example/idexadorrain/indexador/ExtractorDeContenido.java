package com.example.idexadorrain.indexador;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ExtractorDeContenido {

    public String extraerContenidoPdf(String filePath) throws IOException {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    public String extraerContenidoWord(String filePath) throws IOException {
        StringBuilder text = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            XWPFDocument document = new XWPFDocument(fis);
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph para : paragraphs) {
                text.append(para.getText()).append("\n");
            }
        }
        return text.toString();
    }

    public String extraerContenidoExcel(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook = new XSSFWorkbook(fis);
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                content.append("Sheet: ").append(sheet.getSheetName()).append("\n");
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        switch (cell.getCellType()) {
                            case STRING -> content.append(cell.getStringCellValue()).append("\t");
                            case NUMERIC -> {
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    content.append(cell.getDateCellValue()).append("\t");
                                } else {
                                    content.append(cell.getNumericCellValue()).append("\t");
                                }
                            }
                            case BOOLEAN -> content.append(cell.getBooleanCellValue()).append("\t");
                            case FORMULA -> content.append(cell.getCellFormula()).append("\t");
                            default -> content.append(" ").append("\t");
                        }
                    }
                    content.append("\n");
                }
                content.append("\n");
            }
        }
        return content.toString();
    }

}
