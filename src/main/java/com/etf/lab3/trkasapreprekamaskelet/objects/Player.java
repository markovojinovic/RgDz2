package com.etf.lab3.trkasapreprekamaskelet.objects;

import com.etf.lab3.trkasapreprekamaskelet.Game;
import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.animation.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Player extends GameObject implements EventHandler<Event> {
    private static final double DEFAULT_POSITION_X = 0;
    private static final double DEFAULT_POSITION_Y = 0;
    private static final double DEFAULT_POSITION_Z = 0;

    private static final double MAX_JUMP_HEIGHT = 15;
    private static final int JUMPING_DURATION = 500;
    private static final int ON_JUMP_DURATION = 50;

    public static final double NEAR_CLIP = 0.1;
    public static final double FAR_CLIP = 10_000;
    public static final double FIELD_OF_VIEW = 60;

    private PerspectiveCamera firstPersonCamera;
    private PerspectiveCamera thirdPersonCamera;

    private Box shape;
    private PointLight lamp;
    private boolean lampOn = true;

    private int lane = 1;
    private int lives = 0;
    private double initialHeight;
    private Timeline jumpUp, jumpDown;

    private enum PlayerState {
        Running, JumpUp, JumpDown, OnJump
    }

    private enum CameraState {
        Left, Still, Right
    }

    private enum CameraPosition {
        FirsPerson, ThirdPerson
    }

    private PlayerState playerState = PlayerState.Running;
    private CameraState cameraState = CameraState.Still;
    private CameraPosition cameraPosition = CameraPosition.FirsPerson;

    private Game game;

    public Player(Position position, Game game) {
        super(position);

        this.game = game;

        this.shape = new Box(10.0, 30.0, 10.0);

        this.firstPersonCamera = new PerspectiveCamera(true);
        this.firstPersonCamera.setNearClip(NEAR_CLIP);
        this.firstPersonCamera.setFarClip(FAR_CLIP);
        this.firstPersonCamera.setFieldOfView(FIELD_OF_VIEW);

        this.thirdPersonCamera = new PerspectiveCamera(true);
        this.thirdPersonCamera.setNearClip(NEAR_CLIP);
        this.thirdPersonCamera.setFarClip(FAR_CLIP);
        this.thirdPersonCamera.setFieldOfView(FIELD_OF_VIEW);
        this.thirdPersonCamera.setTranslateZ(-100.0D);
        this.thirdPersonCamera.setTranslateY(-50.0D);

        this.lamp = new PointLight();
        this.lamp.setColor(Color.WHITE);
        this.lamp.setTranslateY(-100.0D);
        this.lamp.setTranslateZ(-300.0D);

        this.setTranslateY(position.getY());
        this.initialHeight = position.getY();

        this.getChildren().addAll(this.shape, this.firstPersonCamera);

        this.setupAnimations();

    }

    public static Player InstantiatePlayer(Game game) {
        return new Player(new Position(DEFAULT_POSITION_X,
                DEFAULT_POSITION_Y,
                DEFAULT_POSITION_Z), game);
    }

    @Override
    public void handle(Event event) {
        if (event instanceof KeyEvent) {
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getCode() == KeyCode.ESCAPE &&
                    keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                System.exit(0);
            } else {
                if (!Game.isGameActive()) {
                    return;
                }

                if ((keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT) &&
                        keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                    moveLeft();
                } else if ((keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT) &&
                        keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                    moveRight();
                } else if ((keyEvent.getCode() == KeyCode.W || keyEvent.getCode() == KeyCode.UP) &&
                        keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                    jump();
                } else if ((keyEvent.getCode() == KeyCode.R) && keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                    rotateCameraLeft();
                } else if ((keyEvent.getCode() == KeyCode.T) && keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                    rotateCameraRight();
                } else if ((keyEvent.getCode() == KeyCode.L) && keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                    this.toggleLight();
                } else if ((keyEvent.getCode() == KeyCode.DIGIT1) && keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                    this.firstPerson();
                } else if ((keyEvent.getCode() == KeyCode.DIGIT2) && keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                    this.thirdPerson();
                }
            }
        }
    }

    public Bounds getParentBounds() {
        return this.shape.getBoundsInParent();
    }

    public Camera getCamera() {
        if (this.cameraPosition == CameraPosition.FirsPerson)
            return this.firstPersonCamera;
        else
            return this.thirdPersonCamera;
    }

    private void moveLeft() {
        if (lane == 0)
            return;

        lane--;
        this.setTranslateX(this.getTranslateX() - Track.LANE_WIDTH);
    }

    private void moveRight() {
        if (lane == 2)
            return;

        lane++;
        this.setTranslateX(this.getTranslateX() + Track.LANE_WIDTH);
    }

    private void jump() {
        if (this.playerState == PlayerState.Running) {
            this.playerState = PlayerState.JumpUp;
            this.jumpUp.play();
        }
    }

    private void rotateCameraLeft() {
        if (this.cameraState == CameraState.Still || this.cameraState == CameraState.Right) {
            if (this.cameraState == CameraState.Right)
                this.cameraState = CameraState.Still;
            else
                this.cameraState = CameraState.Left;

            if (this.cameraPosition == CameraPosition.FirsPerson)
                this.firstPersonCamera.getTransforms().add(
                        new Rotate(-15, Rotate.Y_AXIS)
                );
            else
                this.thirdPersonCamera.getTransforms().add(
                        new Rotate(-15, Rotate.Y_AXIS)
                );
        }
    }

    private void rotateCameraRight() {
        if (this.cameraState == CameraState.Still || this.cameraState == CameraState.Left) {
            if (this.cameraState == CameraState.Left)
                this.cameraState = CameraState.Still;
            else
                this.cameraState = CameraState.Right;

            if (this.cameraPosition == CameraPosition.FirsPerson)
                this.firstPersonCamera.getTransforms().add(
                        new Rotate(15, Rotate.Y_AXIS)
                );
            else
                this.thirdPersonCamera.getTransforms().add(
                        new Rotate(15, Rotate.Y_AXIS)
                );
        }
    }

    private void firstPerson() {
        if (this.cameraPosition != CameraPosition.FirsPerson) {
            this.cameraPosition = CameraPosition.FirsPerson;
            this.getChildren().remove(this.thirdPersonCamera);
            this.getChildren().add(this.firstPersonCamera);
            this.game.setupCamera();
        }
    }

    private void thirdPerson() {
        if (this.cameraPosition != CameraPosition.ThirdPerson) {
            this.cameraPosition = CameraPosition.ThirdPerson;
            this.getChildren().remove(this.firstPersonCamera);
            this.getChildren().add(this.thirdPersonCamera);
            this.game.setupCamera();
        }
    }

    public boolean decreaseLives() {
        if (this.lives >= 1) {
            this.lives--;
            return true;
        } else {
            this.lamp.setColor(Color.RED);
            this.switchLight();
            return false;
        }
    }

    public void increaseLives(int amount) {
        if (this.lives + amount <= 2)
            this.lives += amount;
    }

    private void toggleLight() {
        if (lampOn) {
            this.lampOn = false;
            this.lamp.setColor(Color.TRANSPARENT);
            this.switchLight();
        } else {
            this.lampOn = true;
            this.lamp.setColor(Color.WHITE);
            this.switchLight();
        }
    }

    private void switchLight() {
        if (this.getChildren().contains(this.lamp)) {
            this.getChildren().remove(this.lamp);
        } else {
            this.getChildren().add(this.lamp);
        }
    }

    private void setupAnimations() {
        this.jumpUp = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(this.translateYProperty(), this.initialHeight, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(
                        Duration.millis(JUMPING_DURATION),
                        new KeyValue(this.translateYProperty(), this.initialHeight - MAX_JUMP_HEIGHT, Interpolator.EASE_BOTH)
                )
        );
        this.jumpUp.setOnFinished(event -> {
            this.playerState = PlayerState.OnJump;
            PauseTransition delay = new PauseTransition(Duration.millis(ON_JUMP_DURATION));
            delay.setOnFinished(event1 -> {
                this.playerState = PlayerState.JumpDown;
                this.jumpDown.play();
            });
            delay.playFromStart();
        });

        this.jumpDown = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(this.translateYProperty(), this.initialHeight - MAX_JUMP_HEIGHT, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(
                        Duration.millis(JUMPING_DURATION),
                        new KeyValue(this.translateYProperty(), this.initialHeight, Interpolator.EASE_BOTH)
                )
        );
        this.jumpDown.setOnFinished(event -> {
            this.playerState = PlayerState.Running;
        });
    }
}
