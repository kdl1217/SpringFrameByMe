package com.kong;

import com.kong.handler.ITcpMsgHandler;
import com.kong.handler.TcpMsgHandler;
import com.kong.http.HttpHandler;
import com.kong.server.HttpServer;
import com.kong.server.TcpServer;

/**
 * Created by Kong on 2018/1/8.
 */
public class Boot {

    private TcpServer tcpServer = new TcpServer();
    private static HttpServer httpServer;
    private ITcpMsgHandler tcpMsgHandler = new TcpMsgHandler() ;

    public Boot init() throws Exception {
        httpServer = new HttpServer().configPort(1217).configHttpRequestHandler(new HttpHandler()) ;

        tcpServer.configIdleTime(600L);
        tcpServer.configBindPort(1218);
        tcpServer.registeTcpMsgHandler(tcpMsgHandler);

        return this;
    }

    public void start() throws Exception {
        new Thread(new BootTcpServer()).start();
        httpServer.start();
    }

    public class BootTcpServer implements Runnable {
        @Override
        public void run() {
            try {
                tcpServer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Boot().init().start();
    }
}
