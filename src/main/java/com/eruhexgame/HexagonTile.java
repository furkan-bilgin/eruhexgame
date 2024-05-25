package com.eruhexgame;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.util.Duration;
import javafx.scene.text.Text;
import java.util.Map;

public class HexagonTile extends Polygon {
    public final static double r = 25;
    public final static double n = Math.sqrt(r * r * 0.75);
    public final static double TILE_HEIGHT = 2 * r;
    public final static double TILE_WIDTH = 2 * n;

    public HexagonTile(double x, double y) {
        getPoints().addAll(
                x, y,
                x, y + r,
                x + n, y + r * 1.5,
                x + TILE_WIDTH, y + r,
                x + TILE_WIDTH, y,
                x + n, y - r * 0.5
        );

        setStroke(Color.BLACK);
        setFill(Color.NAVAJOWHITE);
        setStrokeWidth(2);
    }

    public void setTileColorByPlayerId(int playerId, Map<Integer, Color> selectedColor, boolean animate) {
        if (!animate) {
            setFill(selectedColor.get(playerId));
            return;
        }

        FillTransition fillTransition = new FillTransition(Duration.seconds(0.2), this);
        // Sarı -> Player rengi
        fillTransition.setFromValue(Color.NAVAJOWHITE);
        fillTransition.setToValue(selectedColor.get(playerId));
        fillTransition.play();
    }
}