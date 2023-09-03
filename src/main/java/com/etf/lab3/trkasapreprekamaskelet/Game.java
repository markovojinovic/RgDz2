package com.etf.lab3.trkasapreprekamaskelet;

import com.etf.lab3.trkasapreprekamaskelet.objects.obstacles.Obstacle;
import com.etf.lab3.trkasapreprekamaskelet.objects.Player;
import com.etf.lab3.trkasapreprekamaskelet.objects.Track;
import com.etf.lab3.trkasapreprekamaskelet.objects.text.Plus;
import com.etf.lab3.trkasapreprekamaskelet.objects.tokens.*;
import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

public class Game extends Application {
    private static final double WINDOW_WIDTH = 720.0;
    private static final double WINDOW_HEIGHT = 400.0;

    private static final double OBSTACLE_SPAWN_DEPTH = 1200.0;

    private static final Color DEFAULT_BACKGROUND_COLOR = Color.CADETBLUE;

    private static final int DEFAULT_OBSTACLE_TARGET_COUNT = 5;
    private static final long DEFAULT_OBSTACLE_CREATION_SPEED = 1500000000l;

    private static final double SMALL_FONT_SIZE = 20.0;
    private static final double BIG_FONT_SIZE = 100.0;

    private Group objects;
    private Group subscenes;
    private Group texts;
    private SubScene gameSubscene;
    private SubScene textSubscene;
    private Scene scene;
    private Stage stage;

    private Player player;
    private int score = 0, scoreMultiplayer = 1;
    private Text scoreText;
    private Track track;

    private long lastTokenCreatedTime = 0L;
    private long lastGoldDiamondTaken = 0L;
    private int tokenCount = 0;
    private int targetTokenCount = 5;
    private double tokenCreationSpeed = 3.75E8D;

    private long lastObstacleCreatedTime = 0;
    private long lastTimeScoreCounted = 0;
    private long lastTimeUpdated = 0;
    private long timeInSeconds = 0;
    private long timeInMinutes = 0;
    private Text timeText;
    private Text jetpackTimeText = null;
    private int obstacleCount = 0;

    private Plus[] livesIndicators;
    private int livesIterator = 0;

    private boolean jetpackActive = false;
    private long jetpackFlyUppTime = -1;

    private int targetObstacleCount = DEFAULT_OBSTACLE_TARGET_COUNT;
    private long obstacleCreationSpeed = DEFAULT_OBSTACLE_CREATION_SPEED;

    private static boolean isGameActive = true;

    private final UpdateTimer timer = new UpdateTimer();

    private class UpdateTimer extends AnimationTimer {

        private long previous;

        @Override
        public void handle(long now) {

            if ( this.previous == 0 ) {
                this.previous = now;
            }

            double ds = ( now - this.previous ) / 1e9;
            this.previous = now;

            if (lastTimeUpdated + 1000000000L < now) {
                timeInSeconds++;
                onSecondUpdate();
                lastTimeUpdated = now;
            }
            timeInMinutes = timeInSeconds / 60;

            updateObstacles(now);
            updateScore(now);
            updateTime();
            player.update(ds);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;

        setupScene();
        showStage();
    }

    private void onSecondUpdate(){
        if (jetpackActive) {
            if (jetpackTimeText == null) {
                jetpackTimeText = new Text();
                jetpackTimeText.setFont(Font.font("Tahoma", BIG_FONT_SIZE / 2));
                jetpackTimeText.setFill(Color.PURPLE);
                jetpackTimeText.setTranslateX(355.0D);
                jetpackTimeText.setTranslateY(70.0D);
                texts.getChildren().add(jetpackTimeText);
            }
            jetpackTimeText.setText(String.valueOf(11 - (timeInSeconds - jetpackFlyUppTime)));
        }
        if (Obstacle.obstacleSpeed < 2 * Obstacle.INITIAL_OBSTACLE_SPEED
                && Token.tokenSpeed < 2 * Token.INITIAL_TOKEN_SPEED) {
            Obstacle.obstacleSpeed += Obstacle.INITIAL_OBSTACLE_SPEED / 120;
            Token.tokenSpeed += Token.INITIAL_TOKEN_SPEED / 120;
        }
        if ((timeInSeconds - jetpackFlyUppTime == 11) && jetpackActive) {
            jetpackActive = false;
            player.flyDown();

            List<Node> tmpChildren = objects.getChildren();
            for (int j = 0; j < tmpChildren.size(); j++) {
                Node tmpChild = (Node) tmpChildren.get(j);
                if (tmpChild instanceof Token) {
                    ((Token) tmpChild).flyDown();
                }
            }
            texts.getChildren().remove(jetpackTimeText);
            jetpackTimeText = null;
        }
    }

    private void setupScene() {
        this.subscenes = new Group();
        this.scene = new Scene(this.subscenes, WINDOW_WIDTH, WINDOW_HEIGHT, true, SceneAntialiasing.BALANCED);

        this.scene.setFill(DEFAULT_BACKGROUND_COLOR);
        this.scene.setCursor(Cursor.NONE);

        this.player = Player.InstantiatePlayer(this);
        this.scene.setOnMouseMoved(this.player);
        this.scene.setOnKeyPressed(this.player);
        this.scene.setOnKeyReleased(this.player);

        this.track = new Track();

        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        ambientLight.setOpacity(0.2);
        ambientLight.setTranslateZ(-1000);
        ambientLight.setBlendMode(BlendMode.SOFT_LIGHT);

        this.objects = new Group();
        this.texts = new Group();
        this.gameSubscene = new SubScene(this.objects, WINDOW_WIDTH, WINDOW_HEIGHT, true, SceneAntialiasing.BALANCED);
        this.gameSubscene.setCamera(this.player.getCamera());
        this.textSubscene = new SubScene(this.texts, WINDOW_WIDTH, WINDOW_HEIGHT);

        this.livesIndicators = new Plus[3];
        for (int i = 0; i < 3; i++) {
            this.livesIndicators[i] = new Plus();
            this.livesIndicators[i].setTranslateX((double) (20 * (i + 1)));
            this.livesIndicators[i].setTranslateY(40.0D);
        }
        livesIndicators[0].makeRed();
        livesIndicators[1].makeRed();
        livesIndicators[2].makeRed();

        this.scoreText = new Text("Score: 0");
        this.scoreText.setFont(Font.font("Tahoma", SMALL_FONT_SIZE));
        this.scoreText.setFill(Color.BLACK);
        this.scoreText.setTranslateX(20.0D);
        this.scoreText.setTranslateY(20.0D);

        this.timeText = new Text("00:00");
        this.timeText.setFont(Font.font("Tahoma", SMALL_FONT_SIZE));
        this.timeText.setFill(Color.BLACK);
        this.timeText.setTranslateX(620.0D);
        this.timeText.setTranslateY(20.0D);

        this.objects.getChildren().addAll(this.player, this.track, ambientLight);
        this.texts.getChildren().addAll(this.livesIndicators);
        this.texts.getChildren().addAll(this.timeText, this.scoreText);
        this.subscenes.getChildren().addAll(this.gameSubscene, this.textSubscene);
    }

    public void setupCamera() {
        this.gameSubscene.setCamera(this.player.getCamera());
    }

    private void showStage() {
        this.stage.setTitle("Trka sa preprekama");
        this.stage.setScene(this.scene);
        this.stage.setResizable(false);
        this.stage.sizeToScene();
        this.stage.show();

        this.timer.start();
    }

    private void updateObstacles(long now) {
        List<Node> children = this.objects.getChildren();

        for (int i = 0; i < this.objects.getChildren().size(); i++) {
            Node child = (Node) children.get(i);
            if (child instanceof Obstacle) {
                if (child.getBoundsInParent().intersects(this.player.localToScene(this.player.getParentBounds())) && !jetpackActive) {
                    if (this.player.decreaseLives()) {
                        children.remove(child);
                        this.decreaseLive();
                    } else {
                        isGameActive = false;
                        this.finalScore();
                        return;
                    }
                }

                if (this.obstacleCount > 0 && !((Obstacle) child).move()) {
                    this.obstacleCount--;
                    this.objects.getChildren().remove(child);
                }
            } else if (child instanceof Token)
                if (!((Token) child).rotate().move()) {
                    this.objects.getChildren().remove(child);
                    this.tokenCount--;
                } else if (child.getBoundsInParent().intersects(this.player.localToScene(this.player.getParentBounds()))) {
                    this.objects.getChildren().remove(child);
                    this.tokenCount--;
                    if (child instanceof Health) {
                        player.increaseLives(1);
                        this.increaseLive();
                    }
                    if (child instanceof GoldenDiamond) {
                        this.lastGoldDiamondTaken = this.timeInSeconds;
                        this.scoreMultiplayer = 2;
                    }
                    if (child instanceof Diamond)
                        this.increaseScore(1);
                    if (child instanceof JetPack) {
                        this.jetpackActive = true;
                        this.jetpackFlyUppTime = this.timeInSeconds;
                        this.player.flyUp();

                        List<Node> tmpChildren = this.objects.getChildren();
                        for (int j = 0; j < tmpChildren.size(); j++) {
                            Node tmpChild = (Node) tmpChildren.get(j);
                            if (tmpChild instanceof JetPack) {
                                this.objects.getChildren().remove(tmpChild);
                                this.tokenCount--;
                            }
                            if (tmpChild instanceof Token) {
                                ((Token) tmpChild).flyUp();
                            }
                        }
                    }
                }
        }
        this.tryCreateObstacles(now);
        this.tryCreateTokens(now);
    }

    private void updateScore(long now) {
        if (isGameActive) {
            if (this.lastTimeScoreCounted + 1000000000L < now) {
                this.increaseScore(1);
                this.lastTimeScoreCounted = now;
            }
            if (this.timeInSeconds - this.lastGoldDiamondTaken > 10)
                this.scoreMultiplayer = 1;
        }
    }

    private void increaseScore(int amount) {
        this.score += amount * this.scoreMultiplayer;
        this.scoreText.setText("Score: " + this.score);
    }

    private void updateTime() {
        if (isGameActive) {
            String minutesText = String.valueOf(this.timeInMinutes);
            if (this.timeInMinutes < 10)
                minutesText = "0" + this.timeInMinutes;

            int seconds = (int) (this.timeInSeconds % 60);
            String secondsText = String.valueOf(seconds);
            if (seconds < 10)
                secondsText = "0" + seconds;

            this.timeText.setText(minutesText + ":" + secondsText);
        }
    }

    private void increaseLive() {
        this.livesIndicators[this.livesIterator].makeGreen();
        if (this.livesIterator < 2)
            this.livesIterator++;
    }

    private void decreaseLive() {
        this.livesIndicators[livesIterator].makeRed();
        if (this.livesIterator > 0)
            this.livesIterator--;
    }

    private void finalScore() {
        Text finalScore = new Text("Score: " + this.score);
        finalScore.setFont(Font.font("Tahoma", BIG_FONT_SIZE));
        finalScore.setFill(Color.RED);
        finalScore.setTranslateX(240.0D);
        finalScore.setTranslateY(200.0D);
        this.texts.getChildren().add(finalScore);
    }

    private boolean tryCreateObstacles(long now) {
        if (this.obstacleCount < this.targetObstacleCount && (double) now > (double) this.lastObstacleCreatedTime + 7.5E8D) {
            this.lastObstacleCreatedTime = now;
            this.objects.getChildren().add(new Obstacle(new Position(this.track.getRandomX(), this.track.getY(), 1200.0D)));
            this.obstacleCount++;
            return true;
        } else
            return false;
    }

    private void tryCreateTokens(long now) {
//        tryCreate && this.tokenCount < this.targetTokenCount

        if ((double) now > (double) this.lastTokenCreatedTime + 7.5E8D) {
            Position position;

            if (!this.jetpackActive)
                position = new Position(this.track.getRandomX(), this.track.getY() - 20.0D, OBSTACLE_SPAWN_DEPTH);
            else
                position = new Position(this.track.getRandomX(), this.track.getY() - 20.0D - Player.MAX_FLY_HEIGHT, OBSTACLE_SPAWN_DEPTH);

            double random = (new Random()).nextDouble();
            Token token = null;
            if (random <= 0.05D && !this.jetpackActive)
                token = new JetPack(position);
            else if (random <= 0.15D)
                token = new Health(position);
            else if (random <= 0.3D)
                token = new GoldenDiamond(position);
            else
                token = new Diamond(position);

            if (token != null)
                this.objects.getChildren().add(token);
            this.tokenCount++;
            this.lastTokenCreatedTime = now;
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static boolean isGameActive() {
        return isGameActive;
    }
}
