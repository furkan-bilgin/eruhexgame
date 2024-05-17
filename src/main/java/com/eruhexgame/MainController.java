package com.eruhexgame;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class MainController {
    @FXML
    private AnchorPane tileContainer;
    @FXML
    private RadioButton tile5x5RadioButton;
    @FXML
    private RadioButton tile11x11RadioButton;
    @FXML
    private RadioButton tile17x17RadioButton;
    @FXML
    private Text turnText;
    @FXML
    public void initialize() {
        // Bilgi penceresini göster
        showInfoDialog();
    }

    private GameModel gameModel;

    private int turnCount = 1; // Tur sayısını burada tanımlayın

    public void initializeTiles(int width, int height) {
        tileContainer.getChildren().clear();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double xCoord = x * HexagonTile.TILE_WIDTH + y * HexagonTile.n;
                double yCoord = y * HexagonTile.TILE_HEIGHT * 0.75;

                int finalX = x;
                int finalY = y;
                HexagonTile tile = new HexagonTile(xCoord, yCoord);
                tile.setOnMouseClicked(_ -> onTileClick(tile, finalX, finalY));
                tileContainer.getChildren().add(tile);
            }
        }
    }

    private void onTileClick(HexagonTile tile, int x, int y) {
        if (gameModel == null) {
            return;
        }

        try {
            int player = gameModel.onTileClick(x, y);
            tile.setTileColorByPlayerId(player);

            boolean gameState = gameModel.checkWin();
            if (gameState) {
                showWinPopup(player);
                gameModel = null;
            } else {
                incrementTurnCount(); // Oyuncunun hamle yaptığı her seferde tur sayısını arttırır
            }
        } catch (Exception e) {
            System.err.println("An error occurred while handling tile click: " + e.getMessage());
            e.printStackTrace();
            // Tile is already occupied
        }
    }

    private void showWinPopup(int player) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        String playerColor = player == GameModel.PLAYER1 ? "RED" : "BLUE";
        alert.setContentText("Player " + playerColor + " wins in turn " + turnCount + "!");

        alert.showAndWait();
    }

    private void incrementTurnCount() {
        turnCount++;
        turnText.setText("Turn: " + turnCount); // Tur sayısını görsel arayüzde günceller
    }

    public void onStartButtonClick() {
        int width, height;
        if (tile5x5RadioButton.isSelected()) {
            width = 5;
            height = 5;
        } else if (tile11x11RadioButton.isSelected()) {
            width = 11;
            height = 11;
        } else if (tile17x17RadioButton.isSelected()) {
            width = 17;
            height = 17;
        } else {
            return;
        }

        initializeTiles(width, height);
        gameModel = new GameModel(width, height);

        turnCount=0;
        turnText.setText("Turn " + turnCount);
    }

    public void showInfoDialog() {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Hex Game Information");
        infoAlert.setHeaderText("Welcome to the Hex Game!");
        infoAlert.setContentText("Hex is a two-player abstract strategy board game in which players attempt to connect opposite sides of a hexagonal grid. Players alternate placing pieces on the board with the goal of forming a continuous chain of their own pieces connecting their designated sides of the board.");

        infoAlert.showAndWait();
    }
}