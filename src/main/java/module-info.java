module com.progetto.codex {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.progetto.codex to javafx.fxml;
    exports com.progetto.codex;
}