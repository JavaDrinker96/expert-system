package org.example.exception.handler;

import javafx.scene.control.Alert;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {

//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Error");
//        alert.setHeaderText(e.getClass().getSimpleName());
//        alert.setContentText(e.getMessage());
//
//        alert.showAndWait();

        System.out.println("ErrrRRRRR");
    }
}
