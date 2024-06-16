module com.example.idexadorrain {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.apache.pdfbox;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires lucene.core;
    requires lucene.queryparser;

    opens com.example.idexadorrain to javafx.fxml;
    exports com.example.idexadorrain;
    exports com.example.idexadorrain.buscador;
    opens com.example.idexadorrain.buscador to javafx.fxml;
}