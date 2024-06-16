package com.example.idexadorrain.indexador;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Indexador {
    private ExtractorDeContenido extraxtorDeContenido;
    private StandardAnalyzer analyzer;
    private Directory directorioIndice;

    public Indexador(String pathToSaveIndexador) throws IOException {
        this.extraxtorDeContenido = new ExtractorDeContenido();
        this.analyzer = new StandardAnalyzer();
        limpiarDirectorioIndice(pathToSaveIndexador);
        this.directorioIndice = FSDirectory.open(Paths.get(pathToSaveIndexador));
    }

    private void limpiarDirectorioIndice(String directoryPath){
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        } else {
            System.out.println("El directorio especificado no existe o no es un directorio.");
        }
    }

    public void indexDocument(String filePath, String tipoArchivoIndexar) throws IOException, IOException {
        String content="";
        switch (tipoArchivoIndexar) {
            case "PDF" -> {
                if (filePath.endsWith(".pdf")) {
                    content = extraxtorDeContenido.extraerContenidoPdf(filePath);
                }
            }
            case "WORD" -> {
                if (filePath.endsWith(".doc") || filePath.endsWith(".docx")) {
                    content = extraxtorDeContenido.extraerContenidoWord(filePath);
                }
            }
            case "EXCEL" -> {
                if (filePath.endsWith(".xls") || filePath.endsWith(".xlsx")) {
                    content = extraxtorDeContenido.extraerContenidoExcel(filePath);
                }
            }
        }
        if(!content.equals("")){
            addDoc(filePath, content);
            System.out.println("Se indexo: " + filePath);
        } else {
            System.out.println("Sin contenido: " + filePath);
        }
    }

    private void addDoc(String filePath, String content) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter w = new IndexWriter(directorioIndice, config);
        Document doc = new Document();
        doc.add(new TextField("path", filePath, Field.Store.YES));
        doc.add(new TextField("content", content, Field.Store.YES));
        w.addDocument(doc);
        w.close();
    }


    public Directory getDirectorioIndice() {
        return directorioIndice;
    }
}
