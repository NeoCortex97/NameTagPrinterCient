package com.example.nametaggerclient2;

import com.dlsc.gemsfx.CircleProgressIndicator;
import com.dlsc.gemsfx.DialogPane;
import com.example.nametaggerclient2.client.ZmqClient;
import com.example.nametaggerclient2.services.PrinterService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class HelloController implements Initializable {
    @FXML
    private Button startButton;
    @FXML
    private DialogPane dialogPane;

    private DialogPane.Dialog<String> scanDialog;
    private DialogPane.Dialog<Void> printDialog;
    private DialogPane.Dialog<Void> receiptDialog;
    private DialogPane.Dialog<Void> busy;

    private TextField jobId;
    private Button continueButton;

    private PrinterService service = new PrinterService();

    Pattern regex = Pattern.compile("(.+?);(.*?);(.*?);(true|false)");


    @FXML
    public void onStartPressed(ActionEvent ignoredEvent) {
        scanDialog.show();
        jobId.clear();
        jobId.requestFocus();
    }

    public void onCodeScanned(ActionEvent event) {
        if (regex.matcher(jobId.getText()).find()) {
            continueButton.setVisible(true);
        }
    }

    private void onContinued(ActionEvent event) {
        printDialog.show();
        continueButton.setVisible(false);
        scanDialog.cancel();
        service.start(jobId.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupScanDialog();
        setupPrintDialog();
        setupReceiptDialog();

        service.setOnSucceeded(event -> {
            printDialog.cancel();
            service.reset();
        });

        dialogPane.setAnimateDialogs(true);
    }

    private void setupReceiptDialog() {
        CircleProgressIndicator progressIndicator = new CircleProgressIndicator();

        FontIcon printerIcon = new FontIcon(MaterialDesign.MDI_RECEIPT);
        printerIcon.setIconColor(new Color(1.0, 1.0, 1.0, 1.0));
        printerIcon.setIconSize(100);

        Label instruction = new Label("PLEASE WAIT");
        instruction.setFont(new Font(36));
        instruction.setTextFill(new Color(1.0, 1.0, 1.0, 1.0));
        Label instruction2 = new Label("PRINTING IN PROGRESS");
        instruction2.setFont(new Font(36));
        instruction2.setTextFill(new Color(1.0, 1.0, 1.0, 1.0));

        VBox vBox = new VBox(printerIcon, instruction, instruction2);
        vBox.setAlignment(Pos.CENTER);

        StackPane stackPane = new StackPane(vBox, progressIndicator);
        stackPane.setPadding(new Insets(10, 10, 10, 10));
        stackPane.getStyleClass().add("dark");

        receiptDialog = new DialogPane.Dialog<>(dialogPane, DialogPane.Type.BLANK);
        receiptDialog.setMaximize(true);
        receiptDialog.setContent(stackPane);
    }

    private void setupPrintDialog() {
        CircleProgressIndicator progressIndicator = new CircleProgressIndicator();

        FontIcon printerIcon = new FontIcon(MaterialDesign.MDI_PRINTER);
        printerIcon.setIconColor(new Color(1.0, 1.0, 1.0, 1.0));
        printerIcon.setIconSize(100);

        Label instruction = new Label("PLEASE WAIT");
        instruction.setFont(new Font(36));
        instruction.setTextFill(new Color(1.0, 1.0, 1.0, 1.0));
        Label instruction2 = new Label("PRINTING IN PROGRESS");
        instruction2.setFont(new Font(36));
        instruction2.setTextFill(new Color(1.0, 1.0, 1.0, 1.0));
        Label nameLabel = new Label("USER: DEFAULT");
        nameLabel.setFont(new Font(16));
        nameLabel.setTextFill(new Color(1.0, 1.0, 1.0, 1.0));
        Label spaceLabel = new Label("SPACE: FLIPDOT");
        spaceLabel.setFont(new Font(16));
        spaceLabel.setTextFill(new Color(1.0, 1.0, 1.0, 1.0));
        Label jobLabel = new Label("JOB: 1 of 1");
        jobLabel.setFont(new Font(16));
        jobLabel.setTextFill(new Color(1.0, 1.0, 1.0, 1.0));

        VBox vBox = new VBox(printerIcon, instruction, instruction2, nameLabel, spaceLabel, jobLabel);
        vBox.setAlignment(Pos.CENTER);

        StackPane stackPane = new StackPane(vBox, progressIndicator);
        stackPane.setPadding(new Insets(10, 10, 10, 10));
        stackPane.getStyleClass().add("dark");

        printDialog = new DialogPane.Dialog<>(dialogPane, DialogPane.Type.BLANK);
        printDialog.setMaximize(true);
        printDialog.setContent(stackPane);
    }

    private void setupScanDialog() {
        jobId = new TextField();
        jobId.getStyleClass().add("hidden");
        jobId.setOnAction(this::onCodeScanned);

        FontIcon scannerIcon = new FontIcon(MaterialDesign.MDI_QRCODE);
        scannerIcon.setIconColor(new Color(1.0, 1.0, 1.0, 1.0));
        scannerIcon.setIconSize(100);

        FontIcon continueIcon = new FontIcon(MaterialDesign.MDI_PLAY_CIRCLE);
        continueIcon.setIconColor(new Color(1.0, 1.0, 1.0, 1.0));
        continueIcon.setIconSize(36);

        FontIcon closeIcon = new FontIcon(MaterialDesign.MDI_CLOSE_CIRCLE);
        closeIcon.setIconColor(new Color(1.0, 1.0, 1.0, 1.0));
        closeIcon.setIconSize(36);

        Label instruction = new Label("PLEASE SCAN QR-CODE");
        instruction.setTextFill(new Color(1.0, 1.0, 1.0, 1.0));
        instruction.setFont(new Font(36));

        Button closeButton = new Button("CLOSE");
        closeButton.getStyleClass().addAll("btn", "btn-danger", "btn-lg");
        closeButton.setOnAction(actionEvent -> scanDialog.cancel());
        closeButton.setGraphic(closeIcon);

        continueButton = new Button("CONTINUE");
        continueButton.getStyleClass().addAll("btn", "btn-success", "btn-lg");
        continueButton.setOnAction(this::onContinued);
        continueButton.setVisible(false);
        continueButton.setGraphic(continueIcon);

        VBox vBox = new VBox(scannerIcon, instruction, jobId, closeButton, continueButton);
        vBox.setMaxHeight(Double.MAX_VALUE);
        vBox.setMaxWidth(Double.MAX_VALUE);
        vBox.getStyleClass().add("dark");
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        scanDialog = new DialogPane.Dialog<>(dialogPane, DialogPane.Type.BLANK);
        scanDialog.setContent(vBox);
        scanDialog.setMaxHeight(Double.MAX_VALUE);
        scanDialog.setMaxWidth(Double.MAX_VALUE);
        scanDialog.setMaximize(true);
    }
}