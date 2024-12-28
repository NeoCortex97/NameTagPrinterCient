package com.example.nametaggerclient2.client;

import com.example.nametaggerclient2.model.PrintJob;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.List;
import java.util.StringJoiner;

public class ZmqClient {
    private final ZContext context = new ZContext();
    private final ZMQ.Socket socket;

    public ZmqClient() {
        socket = context.createSocket(SocketType.REQ);
        socket.connect("tcp://127.0.0.1:6060");
    }

    private String encode(String ... values) {
        StringJoiner joiner = new StringJoiner(";");
        for (String param : values)
            joiner.add(param);
        return joiner.toString();
    }

    public String send(PrintJob command) {
        socket.send(command.toString().getBytes(ZMQ.CHARSET));

        String result = socket.recvStr();
        System.out.println(result);
        return result;
    }
}
