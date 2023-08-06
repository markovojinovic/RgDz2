/* Decompiler 5ms, total 167ms, lines 45 */
package com.etf.lab3.trkasapreprekamaskelet.objects.obstacles;

import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Hurdle extends ObstacleBody {
    private static final double DEFAULT_HURDLE_HEIGHT = 18.0D;
    private static final double DEFAULT_HURDLE_WIDTH = 28.0D;
    private static final double DEFAULT_POLE_THICKNESS = 2.0D;

    public Hurdle(Position position) {
        super(position);
    }

    protected void createObstacleBody() {
        Group hurdle = new Group();

        Box hurdlePart;
        for(int i = 0; i < 7; ++i) {
            hurdlePart = new Box(4.0D, 2.0D, 2.0D);
            hurdlePart.setTranslateX(-12.0D + 4.0D * (double)i);
            if (i % 2 == 1) {
                hurdlePart.setMaterial(new PhongMaterial(Color.RED));
            }

            hurdle.getChildren().add(hurdlePart);
        }

        hurdle.setTranslateY(-9.0D);
        Box pole = new Box(2.0D, 18.0D, 2.0D);
        pole.setTranslateX(-13.0D);
        hurdlePart = new Box(2.0D, 18.0D, 2.0D);
        hurdlePart.setTranslateX(13.0D);
        this.getChildren().addAll(new Node[]{hurdle, pole, hurdlePart});
    }

    public double getObstacleHeight() {
        return 18.0D;
    }
}
