package com.etf.lab3.trkasapreprekamaskelet.objects.obstacles;

import com.etf.lab3.trkasapreprekamaskelet.objects.GameObject;
import com.etf.lab3.trkasapreprekamaskelet.utility.Position;

import java.util.Random;

public class Obstacle extends GameObject {
    private static final double OBSTACLE_SPEED = 4.0D;
    private ObstacleBody obstacleBody;

    public Obstacle(Position position) {
        super(position);
        
        double random = (new Random()).nextDouble();
        if (random <= 0.5)
            this.obstacleBody = new Hurdle(position);
        else
            this.obstacleBody = new HeigherHurdle(position);

        this.setTranslateX(position.getX());
        this.setTranslateY(position.getY() - this.obstacleBody.getObstacleHeight() / (OBSTACLE_SPEED / 2));
        this.setTranslateZ(position.getZ());
        this.getChildren().add(this.obstacleBody);
    }

    public boolean move() {
        this.setTranslateZ(this.getTranslateZ() - OBSTACLE_SPEED);
        return this.isOnTrack();
    }

    public boolean isOnTrack() {
        return this.getTranslateZ() > 0.0D;
    }

    public double getObstacleHeight() {
        return this.obstacleBody.getObstacleHeight();
    }
}
