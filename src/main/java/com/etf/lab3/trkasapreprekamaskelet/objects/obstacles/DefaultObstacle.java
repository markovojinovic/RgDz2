package com.etf.lab3.trkasapreprekamaskelet.objects.obstacles;

import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class DefaultObstacle extends ObstacleBody {
    private static final Color DEFAULT_OBSTACLE_COLOR = Color.WHITESMOKE;
    private static final PhongMaterial OBSTACLE_MATERIAL = new PhongMaterial(DEFAULT_OBSTACLE_COLOR);

    private static final double DEFAULT_OBSTACLE_DIMENSION = 28.0;

    private Box body;

    public DefaultObstacle(Position position) {
        super(position);
    }

    @Override
    protected void createObstacleBody() {
        body = new Box(DEFAULT_OBSTACLE_DIMENSION, DEFAULT_OBSTACLE_DIMENSION, DEFAULT_OBSTACLE_DIMENSION);
        body.setMaterial(OBSTACLE_MATERIAL);
        this.getChildren().add(body);
    }

    @Override
    public double getObstacleHeight() {
        return body.getHeight();
    }

}
