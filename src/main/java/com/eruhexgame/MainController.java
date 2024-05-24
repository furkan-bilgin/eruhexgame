package com.eruhexgame;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Background;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
    private VBox mainContainer;
    @FXML
    private Button startButton;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private ChoiceBox<String> choiceBox2;

    private GameModel gameModel;

    @FXML
    public void initialize() {
        // Bilgi penceresini göster
        showInfoDialog();
        // Arkaplanı ayarla
        setSceneBackground();
        // Buton rengini ayarla
        setButtonBackgroundColor();
        // Oyuncu rengini ayarla
        setColor();
    }

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

    Map<String,Color> colors = new HashMap<>();
    Color[] selectedColor = new Color[2];
    String selectedColorName = "RED"; //bitis ekraninda rengi bu degiskenle yazdiriyor
    String selectedColorName2 = "BLUE";

    public void setColor() {
        choiceBox.getItems().clear();
        choiceBox2.getItems().clear();
        choiceBox.setValue("RED");
        choiceBox2.setValue("BLUE");
        colors.put("BLACK", Color.BLACK);
        colors.put("RED", Color.RED);
        colors.put("YELLOW", Color.YELLOW);
        colors.put("GREEN", Color.GREEN);
        colors.put("BLUE", Color.BLUE);
        colors.put("WHITE", Color.WHITE);

        choiceBox.getItems().addAll(colors.keySet());
        choiceBox2.getItems().addAll(colors.keySet());

        selectedColor[0] = Color.RED;
        selectedColor[1] = Color.BLUE;

        choiceBox.setOnAction(e -> {
            selectedColorName = choiceBox.getValue();
            selectedColor[0] = colors.get(selectedColorName);
        });

        choiceBox2.setOnAction(e -> {
            selectedColorName2 = choiceBox2.getValue();
            selectedColor[1] = colors.get(selectedColorName2);
        });
    }

    private void onTileClick(HexagonTile tile, int x, int y) {
        if (gameModel == null) {
            setColor();
            return;
        }

        try {
            int player = gameModel.onTileClick(x, y);
            tile.setTileColorByPlayerId(player, selectedColor);

            boolean gameState = gameModel.checkWin();
            if (gameState) {
                showWinPopup(player);
                gameModel = null;
                showConfettiEffect();
            } else {
                updateTurnCount();
            }
        } catch (Exception e) {
            // Tile is already occupied
        }
    }

    private void showWinPopup(int player) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        String playerColor = player == GameModel.PLAYER1 ? selectedColorName : selectedColorName2;

        alert.setContentText("Player " + playerColor + " wins in turn " + gameModel.getTurnCount() + "!");
        alert.showAndWait();
    }

    private void updateTurnCount() {
        turnText.setText("Turn: " + gameModel.getTurnCount()); // Tur sayısını görsel arayüzde günceller
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

        updateTurnCount();
    }

    public void showInfoDialog() {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Hex Game Information");
        infoAlert.setHeaderText("Welcome to the Hex Game!");
        infoAlert.setContentText("Hex is a two-player abstract strategy board game in which players attempt to connect opposite sides of a hexagonal grid. Players alternate placing pieces on the board with the goal of forming a continuous chain of their own pieces connecting their designated sides of the board.");

        infoAlert.showAndWait();
    }

    private void setSceneBackground() {
        BackgroundImage myBI = new BackgroundImage(
                new Image("https://static.vecteezy.com/system/resources/thumbnails/008/383/980/small_2x/abstract-seamless-pattern-white-gray-ceramic-tiles-floor-concrete-hexagonal-paver-blocks-design-geometric-mosaic-texture-for-the-decoration-of-the-bathroom-illustration-free-vector.jpg", 462, 400, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        // Then you set to your node
        mainContainer.setBackground(new Background(myBI));
    }

    private void setButtonBackgroundColor() {
        startButton.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void showConfettiEffect() {
        Random random = new Random();
        int numConfetti = 300; // Konfeti parçacık sayısı

        for (int i = 0; i < numConfetti; i++) {
            Rectangle confetti = new Rectangle(5, 10);
            confetti.setFill(Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble()));
            confetti.setX(random.nextDouble() * tileContainer.getWidth());
            confetti.setY(-10); // Başlangıç noktası yukarıda

            tileContainer.getChildren().add(confetti);

            // Animasyon
            TranslateTransition fall = new TranslateTransition(Duration.seconds(2 + random.nextDouble()), confetti);
            fall.setToY(tileContainer.getHeight() + 10);
            fall.setInterpolator(Interpolator.LINEAR);

            RotateTransition rotate = new RotateTransition(Duration.seconds(2 + random.nextDouble()), confetti);
            rotate.setByAngle(360);
            rotate.setInterpolator(Interpolator.LINEAR);

            ParallelTransition animation = new ParallelTransition(fall, rotate);
            animation.setOnFinished(e -> tileContainer.getChildren().remove(confetti));
            animation.play();
        }
    }
}