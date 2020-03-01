package ru.gb.jt.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class SocketThread extends Thread {

    private final SocketThreadListener listener;
    private final Socket socket;
    private DataOutputStream out;
    private ExecutorService ex;
    private SocketThread fakeThis = this;

    public SocketThread(SocketThreadListener listener, String name, Socket socket, ExecutorService ex) {
        super(name);
        this.socket = socket;
        this.listener = listener;

        ex.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    listener.onSocketStart(fakeThis, socket);
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    out = new DataOutputStream(socket.getOutputStream());
                    listener.onSocketReady(fakeThis, socket);
                    while (!isInterrupted()) {
                        String msg = in.readUTF();
                        listener.onReceiveString(fakeThis, socket, msg);
                    }
                } catch (IOException e) {
                    listener.onSocketException(fakeThis, e);
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        listener.onSocketException(fakeThis, e);
                    }
                    listener.onSocketStop(fakeThis);
                }
            }
        });
//        start();


    }

//    @Override
//    public void run() {
//        try {
//            listener.onSocketStart(this, socket);
//            DataInputStream in = new DataInputStream(socket.getInputStream());
//            out = new DataOutputStream(socket.getOutputStream());
//            listener.onSocketReady(this, socket);
//            while (!isInterrupted()) {
//                String msg = in.readUTF();
//                listener.onReceiveString(this, socket, msg);
//            }
//        } catch (IOException e) {
//            listener.onSocketException(this, e);
//        } finally {
//            try {
//                socket.close();
//            } catch (IOException e) {
//                listener.onSocketException(this, e);
//            }
//            listener.onSocketStop(this);
//        }
//    }

    public synchronized boolean sendMessage(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
            return true;
        } catch (IOException e) {
            listener.onSocketException(this, e);
            close();
            return false;
        }
    }

    public synchronized void close() {
        interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            listener.onSocketException(this, e);
        }
    }

}
