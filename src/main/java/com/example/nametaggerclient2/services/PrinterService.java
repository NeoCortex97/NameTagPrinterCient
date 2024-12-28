package com.example.nametaggerclient2.services;

import com.example.nametaggerclient2.client.ZmqClient;
import com.example.nametaggerclient2.model.PrintJob;
import com.example.nametaggerclient2.tasks.PrinterTask;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class PrinterService extends Service<String> {
    ZmqClient client = new ZmqClient();
    PrintJob command;

    public void setCommand(PrintJob command) {
        this.command = command;
    }

    @Override
    protected Task<String> createTask() {
         return new PrinterTask(command, client);
    }

    public void start(PrintJob command) {
        setCommand(command);
        start();
    }
}
