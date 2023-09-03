package com.etf.lab3.trkasapreprekamaskelet.objects.tokens;

import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;

public class JetPack extends Token {

    private Box jetpackBody;
    private Cylinder jetExhaust;

    public JetPack(Position position) {
        super(position);
        this.setTranslateX(position.getX());
        this.setTranslateY(position.getY());
        this.setTranslateZ(position.getZ());

        this.jetpackBody = new Box(10, 15, 5);
        this.jetpackBody.setTranslateY(5);
        this.jetpackBody.setMaterial(new PhongMaterial(Color.PURPLE));

        this.jetExhaust = new Cylinder(2.5, 7.5);
        this.jetExhaust.setTranslateY(11);
        this.jetExhaust.setMaterial(new PhongMaterial(Color.ORANGE));

        this.getChildren().addAll(this.jetpackBody, this.jetExhaust);
    }

}
