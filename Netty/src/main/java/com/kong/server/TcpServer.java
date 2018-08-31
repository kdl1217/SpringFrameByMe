package com.kong.server;

import com.kong.handler.ITcpMsgHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TCP服务
 * Netty
 * Created by Kong on 2018/1/8.
 */
public class TcpServer {
    private static final Logger logger = LoggerFactory.getLogger(TcpServer.class);
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workGroup = new NioEventLoopGroup();
    private ChannelFuture channelFuture;
    private TcpServerInitializer tcpServerInitializer;
    private long idleTime = 180L;
    private int bindProt = 8080;
    private ITcpMsgHandler tcpMsgHandler;

    public TcpServer() {
    }

    /**
     * 配置端口号
     * Default 8080
     * @param port
     */
    public void configBindPort(int port) {
        this.bindProt = port;
    }

    /**
     * 配置所有类型的超时时间
     * Default 180S
     * @param secs
     */
    public void configIdleTime(long secs) {
        this.idleTime = secs;
    }

    /**
     * 注册Handler
     * @param handler
     */
    public void registeTcpMsgHandler(ITcpMsgHandler handler) {
        this.tcpMsgHandler = handler;
    }

    public void start() throws Exception {
        try {
            this.tcpServerInitializer = new TcpServerInitializer(this.idleTime, this.tcpMsgHandler);

            ServerBootstrap b = new ServerBootstrap() ;
            b.group(this.bossGroup, this.workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(this.tcpServerInitializer);
            b.option(ChannelOption.TCP_NODELAY, true);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000) ;
            b.option(ChannelOption.SO_BACKLOG,102400) ;
            this.channelFuture = b.bind(this.bindProt).sync();
            this.channelFuture.addListener(channelFuture -> {
                if (channelFuture.isSuccess()) {
                    logger.info(String.format("TcpServer bind on %d successfully!", TcpServer.this.bindProt));
                } else {
                    logger.warn(String.format("TcpServer bind on %d failed!", TcpServer.this.bindProt));
                }

            });
            this.channelFuture.channel().closeFuture().sync().addListener(future -> {
                logger.info(String.format("TcpServer shutdown on %d successfully!", TcpServer.this.bindProt)) ;
            });
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                shutdown();
                logger.info(String.format("TcpServer shutdown on %d successfully!", TcpServer.this.bindProt));
            }));
        }

    }

    public void shutdown() {
        if (this.bossGroup != null) {
            this.bossGroup.shutdownGracefully();
        }

        if (this.workGroup != null) {
            this.workGroup.shutdownGracefully();
        }

    }

}

