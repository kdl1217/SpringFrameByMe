package com.kong.http;

import com.kong.handler.HttpServerHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;

/**
 * Created by Kong on 2018/1/8.
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    private final SslContext sslCtx;
    private IHttpRequestHandler httpRequestHandler;

    public HttpServerInitializer(SslContext sslCtx, IHttpRequestHandler httpRequestHandler) {
        this.sslCtx = sslCtx;
        this.httpRequestHandler = httpRequestHandler;
    }

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline p = socketChannel.pipeline();
        if (this.sslCtx != null) {
            p.addLast(new ChannelHandler[]{this.sslCtx.newHandler(socketChannel.alloc())});
        }

        p.addLast(new ChannelHandler[]{new HttpServerCodec()});
        p.addLast(new ChannelHandler[]{new HttpObjectAggregator(1048576)});
        p.addLast(new ChannelHandler[]{new HttpServerHandler(this.httpRequestHandler)});
    }
}
