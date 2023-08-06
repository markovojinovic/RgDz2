package com.etf.lab3.trkasapreprekamaskelet.objects.obstacles;


import com.etf.lab3.trkasapreprekamaskelet.objects.GameObject;
import com.etf.lab3.trkasapreprekamaskelet.utility.Position;

public abstract class ObstacleBody extends GameObject {
    public ObstacleBody(Position position) {
        super(position);
        this.createObstacleBody();
    }

    protected abstract void createObstacleBody();

    public abstract double getObstacleHeight();
}
