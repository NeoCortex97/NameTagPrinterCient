package com.example.nametaggerclient2.tasks;

import com.example.nametaggerclient2.client.ZmqClient;
import javafx.concurrent.Task;

import java.util.List;

public class PrinterTask extends Task<String> {
    private final String command;
    private final ZmqClient client;

    public PrinterTask(String cmd, ZmqClient client) {
        command = cmd;
        this.client = client;
    }

    @Override
    protected String call() throws Exception {
        List<String> parts = List.of(command.split("\\s*;\\s*"));

        String result = client.sendPrintCommand(parts.get(0), parts.get(1), parts.get(2), parts.get(3));
        if (parts.get(4).equalsIgnoreCase("true"))
            result = client.sendReceiptCommand(parts.get(0), parts.get(1), parts.get(2), parts.get(3));

        if (result.equalsIgnoreCase("OK"))
            succeeded();
        else
            failed();
        return result;
    }
}
