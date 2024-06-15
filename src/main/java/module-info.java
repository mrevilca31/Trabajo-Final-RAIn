module com.example.idexadorrain {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.idexadorrain to javafx.fxml;
    exports com.example.idexadorrain;
}