package com.example.idexadorrain;

import com.example.idexadorrain.buscador.BuscadorDeDocumentos;
import com.example.idexadorrain.buscador.FileResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.List;

public class SearchController {

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<FileResult> resultsTable;

    @FXML
    private TableColumn<FileResult, String> pathColumn;

    private ObservableList<FileResult> searchResults;

    private BuscadorDeDocumentos searcher;


    @FXML
    private void initialize() {
        pathColumn.setCellValueFactory(new PropertyValueFactory<>("filePath"));
        searchResults = FXCollections.observableArrayList();
        resultsTable.setItems(searchResults);
        resultsTable.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            pathColumn.setPrefWidth(newWidth.doubleValue() - 2);
        });
    }


    public void setDirectoryIndex(Directory directoryIndex) {
        this.searcher = new BuscadorDeDocumentos(directoryIndex);
    }


    @FXML
    private void handleSearch() throws IOException, ParseException {
        String searchText = searchField.getText();

        if (searchText != null && !searchText.isEmpty()) {
            performSearch(searchText);
        }
    }


    private void performSearch(String searchText) throws IOException, ParseException {
        List<String> results = searcher.search(searchText);
        List<FileResult> re = results.stream().map(FileResult::new).toList();
        searchResults.clear();
        searchResults.addAll(re);
    }


    @FXML
    private void handleClear() {
        searchField.clear();
        searchResults.clear();
    }


    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("seleccion_tipo_archivo_interfaz.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) searchField.getScene().getWindow();
            stage.setScene(new Scene(root, 400, 200));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
