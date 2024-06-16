package com.example.idexadorrain;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class FileTypeSelectionController {

    @FXML
    private ComboBox<String> fileTypeComboBox;

    @FXML
    private void handleContinue() {
        String selectedFileType = fileTypeComboBox.getValue();

        if (selectedFileType == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No file type selected");
            alert.setContentText("Please select a file type before continuing.");
            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("search_interface.fxml"));
                Parent root = loader.load();

                // Pasar el tipo de archivo seleccionado al controlador de búsqueda
                SearchController searchController = loader.getController();
                searchController.setFileType(selectedFileType);

                Stage stage = (Stage) fileTypeComboBox.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 600));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
