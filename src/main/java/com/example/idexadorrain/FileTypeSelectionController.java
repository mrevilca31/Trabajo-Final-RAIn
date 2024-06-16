package com.example.idexadorrain;

import com.example.idexadorrain.indexador.Indexador;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;

public class FileTypeSelectionController {
    private String indexPath = "/home/misael/Materias-Facultad/RAIN/Trabajo-Final-2024/Indice";
    private String documentsPath = "/home/misael/Materias-Facultad/RAIN/Trabajo-Final-2024/Documentos-A-indexar";
    private Indexador indexer;


    @FXML
    private ComboBox<String> fileTypeComboBox;

    @FXML
    private void handleContinue() {
        String selectedFileType = fileTypeComboBox.getValue();

        if (selectedFileType == null) {
            showAlert();
        } else {
            try {
                // Inicio Indexador
                indexer = new Indexador(indexPath);
                File folder = new File(documentsPath);
                File[] listOfFiles = folder.listFiles();

                if (listOfFiles != null) {
                    for (File file : listOfFiles) {
                        if (file.isFile()) {
                            indexer.indexDocument(file.getAbsolutePath(), selectedFileType);
                        }
                    }
                } else {
                    System.out.println("No files found in the specified directory.");
                    showAlert();
                }

                // inicio carga de interfaz buscador
                FXMLLoader loader = new FXMLLoader(getClass().getResource("search_interface.fxml"));
                Parent root = loader.load();

                // Pasar el tipo de archivo seleccionado al controlador de búsqueda
                SearchController searchController = loader.getController();
                searchController.setDirectoryIndex(indexer.getDirectorioIndice());

                Stage stage = (Stage) fileTypeComboBox.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 600));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No file type selected");
        alert.setContentText("Please select a file type before continuing.");
        alert.showAndWait();
    }
}
