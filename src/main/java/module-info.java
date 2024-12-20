module com.example.nametaggerclient2 {
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.dlsc.gemsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.materialdesign;
    requires java.desktop;
    requires org.zeromq.jeromq;

    opens com.example.nametaggerclient2 to javafx.fxml;
    exports com.example.nametaggerclient2;
}