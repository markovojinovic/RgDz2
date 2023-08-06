package com.etf.lab3.trkasapreprekamaskelet.objects.tokens;

import com.etf.lab3.trkasapreprekamaskelet.Game;
import com.etf.lab3.trkasapreprekamaskelet.utility.Position;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class Diamond extends Token {
    protected MeshView[] diamond = new MeshView[2];

    public Diamond(Position position) {
        super(position);

        for (int i = 0; i < 2; i++) {
            TriangleMesh mesh = new TriangleMesh();
            float h = 20.0F;
            float w = 20.0F;
            float d = 20.0F;
            mesh.getPoints().addAll(new float[]{0.0F, -h / 2.0F, 0.0F, w / 2.0F, h / 2.0F, d / 2.0F, w / 2.0F, h / 2.0F, -d / 2.0F, -w / 2.0F, h / 2.0F, -d / 2.0F, -w / 2.0F, h / 2.0F, d / 2.0F});
            mesh.getTexCoords().addAll(new float[]{0.504F, 0.0F, 0.701F, 0.0F, 0.126F, 1.0F, 0.0F, 0.364F, 1.0F, 0.0F, 0.165F, 1.0F, 0.606F, 1.0F, 0.575F, 1.0F, 0.0F, 0.643F, 0.0F, 1.0F, 0.74F, 0.42F});
            mesh.getFaces().addAll(new int[]{0, 0, 3, 5, 2, 6, 0, 0, 2, 2, 1, 3, 0, 0, 1, 1, 4, 2, 0, 0, 4, 4, 3, 5, 2, 9, 3, 8, 4, 7, 2, 9, 4, 7, 1, 10});
            this.diamond[i] = new MeshView(mesh);
            this.diamond[i].setCullFace(CullFace.NONE);
            this.diamond[i].setDrawMode(DrawMode.FILL);
            this.diamond[i].setMaterial(new PhongMaterial(Color.GREEN));
            this.diamond[i].setScaleX(0.5D);
            this.diamond[i].setScaleY(0.5D);
            this.diamond[i].setScaleZ(0.5D);
            if (i == 1) {
                Rotate rx = new Rotate();
                rx.setAngle(180.0D);
                rx.setAxis(Rotate.X_AXIS);
                Translate ty = new Translate();
                ty.setY((double) h);
                this.diamond[i].getTransforms().addAll(new Transform[]{ty, rx});
            }
        }

        this.setTranslateX(position.getX());
        this.setTranslateY(position.getY());
        this.setTranslateZ(position.getZ());
        this.getChildren().addAll(this.diamond);
    }
}
