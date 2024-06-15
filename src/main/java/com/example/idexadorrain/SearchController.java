package com.example.idexadorrain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SearchController {

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> fileTypeComboBox;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<FileResult> resultsTable;

    @FXML
    private TableColumn<FileResult, String> nameColumn;

    @FXML
    private TableColumn<FileResult, String> typeColumn;

    @FXML
    private TableColumn<FileResult, String> pathColumn;

    private ObservableList<FileResult> searchResults;

    @FXML
    private void initialize() {
        fileTypeComboBox.setItems(FXCollections.observableArrayList("PDF", "Word", "Excel"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("fileType"));
        pathColumn.setCellValueFactory(new PropertyValueFactory<>("filePath"));

        searchResults = FXCollections.observableArrayList();
        resultsTable.setItems(searchResults);
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText();
        String fileType = fileTypeComboBox.getValue();

        if (searchText != null && !searchText.isEmpty() && fileType != null) {
            performSearch(searchText, fileType);
        }
    }

    private void performSearch(String searchText, String fileType) {
        // Aquí iría la lógica de búsqueda.
        // Vamos a agregar algunos resultados de ejemplo.
        searchResults.clear();
        searchResults.add(new FileResult("Example1", "PDF", "/path/to/example1.pdf"));
        searchResults.add(new FileResult("Example2", "Word", "/path/to/example2.docx"));
        searchResults.add(new FileResult("Example3", "Excel", "/path/to/example3.xlsx"));
    }

    @FXML
    private void handleClear() {
        searchField.clear();
        fileTypeComboBox.getSelectionModel().clearSelection();
        searchResults.clear();
    }
}

