package com.etf.lab3.trkasapreprekamaskelet.objects.obstacles;

import com.etf.lab3.trkasapreprekamaskelet.objects.GameObject;
import com.etf.lab3.trkasapreprekamaskelet.utility.Position;

import java.util.Random;

public class Obstacle extends GameObject {
    public static final double INITIAL_OBSTACLE_SPEED = 4.0D;
    public static double obstacleSpeed = INITIAL_OBSTACLE_SPEED;
    private ObstacleBody obstacleBody;

    public Obstacle(Position position) {
        super(position);
        
        double random = (new Random()).nextDouble();
        if (random <= 0.5)
            this.obstacleBody = new Hurdle(position);
        else
            this.obstacleBody = new HeigherHurdle(position);

        this.setTranslateX(position.getX());
        this.setTranslateY(position.getY() - this.obstacleBody.getObstacleHeight() / (INITIAL_OBSTACLE_SPEED / 2));
        this.setTranslateZ(position.getZ());
        this.getChildren().add(this.obstacleBody);
    }

    public boolean move() {
        this.setTranslateZ(this.getTranslateZ() - obstacleSpeed);
        return this.isOnTrack();
    }

    public boolean isOnTrack() {
        return this.getTranslateZ() > 0.0D;
    }

    public double getObstacleHeight() {
        return this.obstacleBody.getObstacleHeight();
    }
}
