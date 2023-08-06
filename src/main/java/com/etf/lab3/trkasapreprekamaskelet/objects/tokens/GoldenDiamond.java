package com.etf.lab3.trkasapreprekamaskelet.objects.tokens;

import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class GoldenDiamond extends Diamond {
    public static boolean IsCollected = false;

    public GoldenDiamond(Position position) {
        super(position);

        for (int i = 0; i < 2; i++) {
            this.diamond[i].setMaterial(new PhongMaterial(Color.GOLD));
        }

    }
}
