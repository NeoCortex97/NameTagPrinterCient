package com.example.nametaggerclient2.client;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.List;
import java.util.StringJoiner;

public class ZmqClient {
    private ZContext context = new ZContext();
    private ZMQ.Socket socket;

    public ZmqClient() {
        socket = context.createSocket(SocketType.REQ);
        socket.connect("tcp://127.0.0.1:6660");
    }

    private String encode(String ... values) {
        StringJoiner joiner = new StringJoiner(";");
        for (String param : values)
            joiner.add(param);
        return joiner.toString();
    }

    private String send(String command) {
        socket.send(command.getBytes(ZMQ.CHARSET));

        String result = socket.recvStr();
        System.out.println(result);
        return result;
    }

    public String sendPrintCommand(String name, String space, String logo, String url) {
        return send(encode("TAG", name, space, logo, url));
    }
}
