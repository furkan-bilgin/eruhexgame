package com.eruhexgame;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;
import javafx.scene.text.Text;
import java.util.Map;

public class HexagonTile extends Polygon {
    public final static double r = 25;
    public final static double n = Math.sqrt(r * r * 0.75);
    public final static double TILE_HEIGHT = 2 * r;
    public final static double TILE_WIDTH = 2 * n;

    private double x;
    private double y;

    public HexagonTile(double x, double y) {
        double xCoord = x * TILE_WIDTH + y * n;
        double yCoord = y * TILE_HEIGHT * 0.75;

        getPoints().addAll(
                xCoord, yCoord,
                xCoord, yCoord + r,
                xCoord + n, yCoord + r * 1.5,
                xCoord + TILE_WIDTH, yCoord + r,
                xCoord + TILE_WIDTH, yCoord,
                xCoord + n, yCoord - r * 0.5
        );

        setStroke(Color.BLACK);
        setFill(Color.NAVAJOWHITE);
        setStrokeWidth(2);

        this.x = x;
        this.y = y;
    }

    public void setTileColorByPlayerId(int playerId, Map<Integer, Color> selectedColor, boolean animate) {
        if (!animate) {
            setFill(selectedColor.get(playerId));
            return;
        }

        FillTransition fillTransition = new FillTransition(Duration.seconds(0.2), this);
        // Sarıdan -> Player rengine
        fillTransition.setFromValue(Color.NAVAJOWHITE);
        fillTransition.setToValue(selectedColor.get(playerId));
        fillTransition.play();
    }

    public void setStrokeColor(Map<Integer, Color> selectedColor, int mapSize) {
        if (x == 0 || x == mapSize - 1) {
            setStroke(selectedColor.get(GameModel.PLAYER1));
        } else if (y == 0 || y == mapSize - 1) {
            setStroke(selectedColor.get(GameModel.PLAYER2));
        } else {
            return;
        }
        setViewOrder(-100);
    }
}