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
    private IndexWriter writer;


    public Indexador(String pathToSaveIndexador) throws IOException {
        this.extraxtorDeContenido = new ExtractorDeContenido();
        this.analyzer = new StandardAnalyzer();
        limpiarDirectorioIndice(pathToSaveIndexador);
        iniciarIndexWriter(pathToSaveIndexador);
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
            if (directory.mkdirs()) {
                System.out.println("Directorio creado: " + directoryPath);
            } else {
                System.out.println("No se pudo crear el directorio: " + directoryPath);
            }
        }
    }


    private void iniciarIndexWriter(String pathToSaveIndice) throws IOException {
        this.directorioIndice = FSDirectory.open(Paths.get(pathToSaveIndice));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        this.writer = new IndexWriter(directorioIndice, config);
    }


    public void indexarDocumento(String filePath, String tipoArchivoIndexar) throws IOException {
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
        }
    }


    private void addDoc(String filePath, String content) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("path", filePath, Field.Store.YES));
        doc.add(new TextField("content", content, Field.Store.YES));
        this.writer.addDocument(doc);
    }


    public void closeIndexador() throws IOException {
        this.writer.close();
    }


    public Directory getDirectorioIndice() {
        return directorioIndice;
    }
}
