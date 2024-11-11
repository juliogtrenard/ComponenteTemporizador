module es.juliogtrenard.componentetemporizador {
    requires javafx.controls;
    requires javafx.fxml;


    opens es.juliogtrenard.componentetemporizador to javafx.fxml;
    exports es.juliogtrenard.componentetemporizador;
}