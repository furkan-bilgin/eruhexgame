package com.eruhexgame;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameModel {
    public static final int EMPTY = 0;
    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    private int[][] tiles;

    private int currentPlayer = PLAYER1;
    private int turnCount = 0;

    public GameModel(int width, int height) {
        tiles = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = 0;
            }
        }
    }

    public int getTurnCount() {
        return turnCount;
    }

    public int onTileClick(int x, int y) throws Exception {
        if (tiles[x][y] != EMPTY) {
            throw new Exception("Tile is already occupied");
        }
        if (currentPlayer == PLAYER1) {
            turnCount++;
        }
        tiles[x][y] = currentPlayer;
        currentPlayer = currentPlayer == PLAYER1 ? PLAYER2 : PLAYER1;

        return tiles[x][y];
    }

    public boolean checkWin() {
        ArrayList<int[]> queue = new ArrayList<>();
        boolean[][] visited = new boolean[tiles.length][tiles[0].length];
        int playerToCheck = currentPlayer == PLAYER1 ? PLAYER2 : PLAYER1;
        if (playerToCheck == PLAYER1) {
            for (int i = 0; i < tiles[0].length; i++) {
                if (tiles[0][i] == PLAYER1) {
                    queue.add(new int[] { 0, i });
                    visited[0][i] = true;
                }
            }
            while (!queue.isEmpty()) {
                int[] current = queue.removeFirst();
                if (current[0] == tiles[0].length - 1) {
                    return true;
                }
                for (int[] neighbour : getNeighbours(current[0], current[1])) {
                    if (!visited[neighbour[0]][neighbour[1]] && tiles[neighbour[0]][neighbour[1]] == PLAYER1) {
                        queue.add(neighbour);
                        visited[neighbour[0]][neighbour[1]] = true;
                    }
                }
            }
        } else {
            for (int i = 0; i < tiles.length; i++) {
                if (tiles[i][0] == PLAYER2) {
                    queue.add(new int[] { i, 0 });
                    visited[i][0] = true;
                }
            }
            while (!queue.isEmpty()) {
                int[] current = queue.removeFirst();
                if (current[1] == tiles.length - 1) {
                    return true;
                }
                for (int[] neighbour : getNeighbours(current[0], current[1])) {
                    if (!visited[neighbour[0]][neighbour[1]] && tiles[neighbour[0]][neighbour[1]] == PLAYER2) {
                        queue.add(neighbour);
                        visited[neighbour[0]][neighbour[1]] = true;
                    }
                }
            }
        }

        return false;
    }

    // Get neighbours
    private ArrayList<int[]> getNeighbours(int x, int y) {
        int[][] neighbours = {
                { x - 1, y },
                { x + 1, y },
                { x, y - 1 },
                { x, y + 1 },
                { x - 1, y + 1 },
                { x + 1, y - 1 }
        };
        ArrayList<int[]> validNeighbours = new ArrayList<>();
        for (int[] neighbour : neighbours) {
            if (neighbour[0] >= 0 && neighbour[0] < tiles.length && neighbour[1] >= 0
                    && neighbour[1] < tiles[0].length) {
                validNeighbours.add(neighbour);
            }
        }
        return validNeighbours;
    }
}
