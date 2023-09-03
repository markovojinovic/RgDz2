package com.etf.lab3.trkasapreprekamaskelet.objects.tokens;

import com.etf.lab3.trkasapreprekamaskelet.objects.GameObject;
import com.etf.lab3.trkasapreprekamaskelet.objects.Player;
import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.scene.transform.Rotate;

public abstract class Token extends GameObject {
    public static final double INITIAL_TOKEN_SPEED = 4.0D;
    public static double tokenSpeed = INITIAL_TOKEN_SPEED;

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
        this.setTranslateZ(this.getTranslateZ() - tokenSpeed);
        return this.isOnTrack();
    }

    public boolean isOnTrack() {
        return this.getTranslateZ() > 0.0D;
    }

    public void flyUp() {
        this.setTranslateY(this.getTranslateY() - Player.MAX_FLY_HEIGHT);
    }

    public void flyDown() {
        this.setTranslateY(this.getTranslateY() + Player.MAX_FLY_HEIGHT);
    }
}
