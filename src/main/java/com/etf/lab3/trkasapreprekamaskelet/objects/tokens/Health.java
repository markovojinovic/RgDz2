package com.etf.lab3.trkasapreprekamaskelet.objects.tokens;

import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Health extends Token {
    private static final double DEFAULT_CROSS_HEIGHT = 20.0D;
    private static final double DEFAULT_CROSS_WIDTH = 5.0D;
    private Box[] cross;
    private PointLight light;
    private final Health.UpdateTimer timer = new Health.UpdateTimer();

    public Health(Position position) {
        super(position);
        this.setTranslateX(position.getX());
        this.setTranslateY(position.getY());
        this.setTranslateZ(position.getZ());
        this.cross = new Box[2];

        for (int i = 0; i < 2; i++) {
            this.cross[i] = new Box(DEFAULT_CROSS_WIDTH, DEFAULT_CROSS_HEIGHT, DEFAULT_CROSS_WIDTH);
            this.cross[i].setMaterial(new PhongMaterial(Color.RED));
        }

        this.cross[1].setRotate(90.0D);
        this.light = new PointLight();
        this.light.setColor(Color.RED);
        this.getChildren().addAll(this.cross);
        this.getChildren().addAll(new Node[]{this.light});
        this.timer.start();
        this.timer.setParent(this);
    }

    private class UpdateTimer extends AnimationTimer {
        private long lastUpdated = 0L;
        private Health token;

        public void setParent(Health token) {
            this.token = token;
        }

        public void handle(long now) {
            if (this.lastUpdated + 1000000000L < now) {
                this.lastUpdated = now;
                if (!this.token.getChildren().contains(Health.this.light)) {
                    this.token.getChildren().add(Health.this.light);
                } else {
                    this.token.getChildren().remove(Health.this.light);
                }
            }

        }
    }
}
