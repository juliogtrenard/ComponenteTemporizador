package es.juliogtrenard.componentetemporizador;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Clase Controladora del Temporizador
 */
public class TemporizadorController extends AnchorPane {
    private BooleanProperty fin;
    private int segundos;
    private Timer timer;

    /**
     * Minutos
     */
    @FXML
    private Label min1;

    /**
     * Minutos
     */
    @FXML
    private Label min2;

    /**
     * Segundos
     */
    @FXML
    private Label seg1;

    /**
     * Segundos
     */
    @FXML
    private Label seg2;

    /**
     * Constructor de la clase TemporizadorController
     */
    public TemporizadorController() {
        this.fin = new SimpleBooleanProperty(false);
        this.segundos = -1;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/es/juliogtrenard/componentetemporizador/fxml/temporizador.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Asigna los segundos al temporizador
     *
     * @param segundos del temporizador
     * @return true/false
     */
    public boolean setSegundos(int segundos) {
        if (segundos >= 60) {
            int minutos = (int) (segundos / 60);
            if (minutos < 100) {
                this.segundos = segundos;
                return true;
            }
        } else {
            if (segundos > 0) {
                this.segundos = segundos;
                return true;
            }
        }
        return false;
    }

    /**
     * Inicia el temporizador
     */
    public void iniciar() {
        if (this.segundos == -1) {
            System.out.println("Asigna los segundos antes de iniciar el temporizador.");
        } else {
            final int[] restante = {this.segundos};
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (restante[0] < 0) {
                        timer.cancel();
                        Platform.runLater(() -> fin.set(true));
                        return;
                    }
                    int mins = restante[0] / 60;
                    int mins1 = mins / 10;
                    int mins2 = mins % 10;
                    int segs = restante[0] % 60;
                    int segs1 = segs / 10;
                    int segs2 = segs % 10;

                    Platform.runLater(() -> {
                        min1.setText(mins1 + "");
                        min2.setText(mins2 + "");
                        seg1.setText(segs1 + "");
                        seg2.setText(segs2 + "");
                    });

                    restante[0] -= 1;
                }
            }, 0, 1000);
        }
    }

    /**
     * Detiene el temporizador manualmente
     */
    public void detener() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    /**
     * Devuelve si el temporizador ha finalizado
     *
     * @return BooleanProperty fin
     */
    public BooleanProperty finProperty() {
        return fin;
    }

}