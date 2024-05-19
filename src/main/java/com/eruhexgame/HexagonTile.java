package com.eruhexgame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.util.Duration;
import javafx.scene.text.Text;


public class HexagonTile extends Polygon {
    public final static double r = 25;
    public final static double n = Math.sqrt(r * r * 0.75);
    public final static double TILE_HEIGHT = 2 * r;
    public final static double TILE_WIDTH = 2 * n;

    private Text turnText;
    private int turnCount = 1;

    public HexagonTile(double x, double y, Text turnText)
    {
        this.turnText = turnText;
    }

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

    public void setTileColorByPlayerId(int playerId) {
        Color newColor;

        if (playerId == GameModel.PLAYER1) {
            newColor = Color.RED;
        } else if (playerId == GameModel.PLAYER2) {
            newColor = Color.BLUE;
        } else {
            return;
        }

        FillTransition fillTransition = new FillTransition(Duration.seconds(0.5), this);
        fillTransition.setFromValue(Color.NAVAJOWHITE); // Start from yellow
        fillTransition.setToValue(newColor); // Transition to new color

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), this);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        ParallelTransition parallelTransition = new ParallelTransition(fillTransition, fadeTransition);
        parallelTransition.play();
    }

    }


