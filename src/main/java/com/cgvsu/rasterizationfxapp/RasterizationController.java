package com.cgvsu.rasterizationfxapp;

import com.cgvsu.rasterization.Rasterization;
import com.cgvsu.util.Vector2f;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class RasterizationController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        Rasterization.drawTriangle(
                canvas.getGraphicsContext2D().getPixelWriter(),
                new Vector2f(0.0f, 600.0f), new Vector2f(800.0f, 600.0f), new Vector2f(800.0f, 0.0f),
                Color.RED, Color.LIME, Color.BLUE
        );
    }
}
