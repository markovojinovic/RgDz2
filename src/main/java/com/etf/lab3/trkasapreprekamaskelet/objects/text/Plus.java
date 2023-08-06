package com.etf.lab3.trkasapreprekamaskelet.objects.text;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Plus extends Text {

    public Plus(){
        super("+");
        super.setFont(Font.font("Tahoma", 20.0D));
        super.setFill(Color.GREEN);
    }

    public void makeRed(){
        this.setFill(Color.RED);
    }

    public void makeGreen(){
        this.setFill(Color.GREEN);
    }
}
