package com.example.nametaggerclient2.services;

import com.example.nametaggerclient2.client.ZmqClient;
import com.example.nametaggerclient2.tasks.PrinterTask;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class PrinterService extends Service<String> {
    ZmqClient client = new ZmqClient();
    String command;

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    protected Task<String> createTask() {
         return new PrinterTask(command, client);
    }

    public void start(String command) {
        setCommand(command);
        start();
    }
}
