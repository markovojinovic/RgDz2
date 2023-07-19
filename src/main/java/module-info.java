module com.etf.lab3.trkasapreprekamaskelet {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.etf.lab3.trkasapreprekamaskelet to javafx.fxml;
    exports com.etf.lab3.trkasapreprekamaskelet;
}