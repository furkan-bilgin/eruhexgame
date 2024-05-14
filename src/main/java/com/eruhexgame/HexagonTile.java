package com.eruhexgame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

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

    public void setTileColorByPlayerId(int playerId) {
        if (playerId == GameModel.PLAYER1) {
            setFill(Color.RED);
        } else if (playerId == GameModel.PLAYER2) {
            setFill(Color.BLUE);
        }
    }

}
