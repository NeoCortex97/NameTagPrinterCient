package com.example.nametaggerclient2.model;

import java.util.*;
import java.util.stream.Collectors;

public class PrintJob {
    Destination destination;
    Command command;
    Map<String, String> params = new HashMap<>();

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void setParam(String key, String value) {
        if (!key.equalsIgnoreCase("action"))
            params.put(key, value);
        else
            setCommand(Command.valueOf(value.toUpperCase()));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(destination.toString()).append(';');
        builder.append("action=").append(command.toString()).append(';');
        for (Map.Entry<String, String> item : params.entrySet())
            builder.append(item.getKey()).append('=').append(item.getValue()).append(';');
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public static PrintJob fromString(String raw) {
        PrintJob job = new PrintJob();
        List<String> parts = Arrays.stream(raw.split(";")).collect(Collectors.toCollection(ArrayList::new));
        job.setDestination(Destination.valueOf(parts.getFirst().toUpperCase()));
        parts.removeFirst();
        for (String param : parts) {
            String[] tmp = param.split("=", 2);
            job.setParam(tmp[0], tmp[1]);
        }

        return job;
    }
}
