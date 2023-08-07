package com.etf.lab3.trkasapreprekamaskelet.objects.obstacles;

import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Hurdle extends ObstacleBody {
    private static final double DEFAULT_HURDLE_HEIGHT = 18.0D;
    private static final double DEFAULT_HURDLE_WIDTH = 26.0D;
    private static final double DEFAULT_POLE_THICKNESS = 2.0D;

    public Hurdle(Position position) {
        super(position);
    }

    protected void createObstacleBody() {
        Group hurdle = new Group();
        Box hurdlePart;

        for (int i = 0; i < 7; ++i) {
            hurdlePart = new Box(4.0D, DEFAULT_POLE_THICKNESS, DEFAULT_POLE_THICKNESS);
            hurdlePart.setTranslateX(-12.0D + 4.0D * (double) i);
            if (i % 2 == 1) {
                hurdlePart.setMaterial(new PhongMaterial(Color.RED));
            }

            hurdle.getChildren().add(hurdlePart);
        }

        hurdle.setTranslateY(-DEFAULT_HURDLE_HEIGHT / 2);

        Box leftPole = new Box(DEFAULT_POLE_THICKNESS, DEFAULT_HURDLE_HEIGHT, DEFAULT_POLE_THICKNESS);
        leftPole.setTranslateX(-DEFAULT_HURDLE_WIDTH / 2);

        Box rightPole = new Box(DEFAULT_POLE_THICKNESS, DEFAULT_HURDLE_HEIGHT, DEFAULT_POLE_THICKNESS);
        rightPole.setTranslateX(DEFAULT_HURDLE_WIDTH / 2);

        this.getChildren().addAll(hurdle, leftPole, rightPole);
    }

    public double getObstacleHeight() {
        return DEFAULT_HURDLE_HEIGHT;
    }
}
