package com.etf.lab3.trkasapreprekamaskelet.objects;

import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.scene.Group;

public class GameObject extends Group {
    protected Position position;

    public GameObject(Position position) {
        this.position = position;
    }
}
