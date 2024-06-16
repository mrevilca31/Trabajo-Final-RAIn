package com.example.idexadorrain.buscador;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuscadorDeDocumentos {
    private StandardAnalyzer analizador = new StandardAnalyzer();
    private Directory indice;

    public BuscadorDeDocumentos(Directory indexDirectory){
        this.indice = indexDirectory;
    }


    public List<String> search(String queryStr) throws IOException, ParseException {
        Query q = new QueryParser("content", analizador).parse(queryStr);
        try (DirectoryReader reader = DirectoryReader.open(indice)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(q, 10);
            ScoreDoc[] hits = docs.scoreDocs;

            List<String> results = new ArrayList<>();
            for (ScoreDoc hit : hits) {
                int docId = hit.doc;
                org.apache.lucene.document.Document d = searcher.doc(docId);
                results.add(d.get("path"));
            }
            return results;
        }
    }

}
