package com.example.idexadorrain;

import com.example.idexadorrain.SearchController;
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
    private final String PATH_INDICE = "/home/misael/Materias-Facultad/RAIN/Trabajo-Final-2024/Indice";
    private final String PATH_DOCUMENTOS = "/home/misael/Materias-Facultad/RAIN/Trabajo-Final-2024/Documentos-A-indexar";
    private Indexador indexador;


    @FXML
    private ComboBox<String> fileTypeComboBox;


    @FXML
    private void handleContinue() {
        String tipoArchivoSeleccionado = fileTypeComboBox.getValue();

        if (tipoArchivoSeleccionado == null) {
            showAlert("Tipo de archivo no seleccionado",
                    "Por favor seleccione un tipo de archivo antes de continuar.");
        } else {
            try {
                // Inicio Indexador
                indexador = new Indexador(PATH_INDICE);
                File folder = new File(PATH_DOCUMENTOS);
                File[] listOfFiles = folder.listFiles();

                if (listOfFiles != null) {
                    for (File file : listOfFiles) {
                        if (file.isFile()) {
                            indexador.indexarDocumento(file.getAbsolutePath(), tipoArchivoSeleccionado);
                        }
                    }
                    indexador.closeIndexador();
                } else {
                    showAlert("Archivos no encontrados",
                            "El directorio especificado esta vacío.");
                }

                // inicio carga de interfaz buscador
                FXMLLoader loader = new FXMLLoader(getClass().getResource("search_interface.fxml"));
                Parent root = loader.load();

                SearchController searchController = loader.getController();
                searchController.setDirectoryIndex(indexador.getDirectorioIndice());

                Stage stage = (Stage) fileTypeComboBox.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 600));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void showAlert(String headerText, String contentText){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
