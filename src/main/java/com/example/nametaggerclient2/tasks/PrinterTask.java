package com.example.nametaggerclient2.tasks;

import com.example.nametaggerclient2.client.ZmqClient;
import com.example.nametaggerclient2.model.PrintJob;
import javafx.concurrent.Task;

import java.util.List;

public class PrinterTask extends Task<String> {
    private final PrintJob command;
    private final ZmqClient client;

    public PrinterTask(PrintJob cmd, ZmqClient client) {
        command = cmd;
        this.client = client;
    }

    @Override
    protected String call() throws Exception {
        String result = client.send(command);
        if (result.split(";")[0].equalsIgnoreCase("OK"))
            succeeded();
        else
            failed();
        return result;
    }
}
