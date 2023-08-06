package com.etf.lab3.trkasapreprekamaskelet.objects;

import java.util.Random;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Track extends Group {
    public static final double LANE_HEIGHT = 0.5;
    public static final double LANE_WIDTH = 30.0;
    public static final double LANE_LENGTH = 10000.0;

    public static final double DEFAULT_TRANSLATE_Y = 30.0;
    public static final double DEFAULT_DISTANCE_BETWEEN_LANES = 2.0;

    private static final Color DEFAULT_TRACK_COLOR = Color.CORAL;
    private static final PhongMaterial TRACK_MATERIAL = new PhongMaterial(DEFAULT_TRACK_COLOR);

    private static final Color DEFAULT_LINE_COLOR = Color.WHITE;
    private static final PhongMaterial LINE_MATERIAL = new PhongMaterial(DEFAULT_LINE_COLOR);

    private static final Color DEFAULT_GRASS_COLOR = Color.GREEN;
    private static final PhongMaterial GRASS_MATERIAL = new PhongMaterial(DEFAULT_GRASS_COLOR);

    private Box[] lanes;

    public Track() {
        lanes = new Box[3];
        for (int i = 0; i < lanes.length; i++) {
            lanes[i] = CreateLane(i);
        }

        Box lines = new Box(LANE_WIDTH * 2, LANE_HEIGHT, LANE_LENGTH);
        lines.setMaterial(LINE_MATERIAL);
        lines.setTranslateX(lanes[1].getTranslateX());
        lines.setTranslateY(DEFAULT_TRANSLATE_Y + 1);

        Box grass = new Box(240.0D, 0.5D, 10000.0D);
        Image FLOOR_IMAGE = new Image("file:assets/grass.jpg");
        GRASS_MATERIAL.setDiffuseMap(FLOOR_IMAGE);
        grass.setMaterial(GRASS_MATERIAL);
        grass.setTranslateX(this.lanes[1].getTranslateX());
        grass.setTranslateY(32.0D);

        this.getChildren().addAll(lanes);
        this.getChildren().addAll(lines, grass);
    }

    public Box CreateLane(int i) {
        Box lane = new Box(30.0D, 0.5D, 10000.0D);
        Image TRACK_IMAGE = new Image("file:assets/tartan.jpg");
        TRACK_MATERIAL.setDiffuseMap(TRACK_IMAGE);
        TRACK_MATERIAL.setDiffuseColor(Color.GRAY);
        lane.setMaterial(TRACK_MATERIAL);
        lane.setTranslateY(30.0D);
        lane.setTranslateX((double) (-1 + i) * 32.0D);
        return lane;
    }

    public double getY() {
        return lanes[0].getTranslateY();
    }

    public double getRandomX() {
        return lanes[new Random().nextInt(3)].getTranslateX();
    }
}
