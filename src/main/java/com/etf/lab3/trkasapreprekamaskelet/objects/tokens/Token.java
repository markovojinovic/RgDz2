package com.etf.lab3.trkasapreprekamaskelet.objects.tokens;

import com.etf.lab3.trkasapreprekamaskelet.objects.GameObject;
import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.scene.transform.Rotate;

public abstract class Token extends GameObject {
    public static final double TOKEN_SPEED = 4.0D;

    public Token(Position position) {
        super(position);
    }

    public Token rotate() {
        Rotate ry = new Rotate();
        ry.setAxis(Rotate.Y_AXIS);
        ry.setAngle(2.0D);
        this.getTransforms().add(ry);
        return this;
    }

    public boolean move() {
        this.setTranslateZ(this.getTranslateZ() - TOKEN_SPEED);
        return this.isOnTrack();
    }

    public boolean isOnTrack() {
        return this.getTranslateZ() > 0.0D;
    }
}
