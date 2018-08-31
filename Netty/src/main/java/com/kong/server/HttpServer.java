package com.kong.server;

import com.kong.http.HttpServerInitializer;
import com.kong.http.IHttpRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Netty Http Server
 *
 * @author Kong, created on 2018-08-29T16:52.
 * @since 1.2.0-SNAPSHOT
 */
public class HttpServer  {

    private static Logger logger = LoggerFactory.getLogger(HttpServer.class);

    private boolean SSL = false;
    private int port = 8080 ;   //默认端口
    private IHttpRequestHandler httpRequestHandler ;
    private EventLoopGroup bossGroup = new NioEventLoopGroup(1) ;
    private EventLoopGroup workerGroup = new NioEventLoopGroup() ;

    public HttpServer(){

    }

    public HttpServer enableSSL(boolean ssl) {
        this.SSL = ssl;
        return this;
    }

    public HttpServer configPort(int port){
        this.port = port ;
        return this;
    }

    public HttpServer configHttpRequestHandler(IHttpRequestHandler httpRequestHandler){
        this.httpRequestHandler = httpRequestHandler ;
        return this ;
    }

    public void start() throws Exception{
        SslContext sslCtx;
        if (this.SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        try {
            ServerBootstrap b = new ServerBootstrap();
            (b.group(this.bossGroup, this.workerGroup).channel(NioServerSocketChannel.class)).childHandler(new HttpServerInitializer(sslCtx, this.httpRequestHandler));
            Channel ch = b.bind(this.port).sync().addListener((ChannelFutureListener) channelFuture -> {
                System.out.println("bind port:"+this.port);
                logger.info(String.format("HttpServer bind on %s successfully", this.port)) ;
            }).channel();
            b.option(ChannelOption.TCP_NODELAY, true);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.option(ChannelOption.SO_BACKLOG,1024) ;
            ch.closeFuture().sync().addListener((ChannelFutureListener) channelFuture -> {
                System.out.println("shut down");
                logger.info(String.format("HttpServer shut down on %s successfully", this.port)) ;
            });
        } finally {
            this.bossGroup.shutdownGracefully();
            this.workerGroup.shutdownGracefully();
        }
    }

    public void stop() {
        if (this.bossGroup != null) {
            this.bossGroup.shutdownGracefully();
        }

        if (this.workerGroup != null) {
            this.workerGroup.shutdownGracefully();
        }

    }
}
