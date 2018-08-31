package com.kong.server;

import com.kong.handler.ITcpMsgHandler;
import com.kong.handler.TcpServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 初始化TCP -心跳检测
 * Created by Kong on 2018/1/8.
 */
public class TcpServerInitializer extends ChannelInitializer {
    private long idleTime = 180L;
    private ITcpMsgHandler tcpMsgHandler;

    public TcpServerInitializer(long idleTime, ITcpMsgHandler tcpMsgHandler) {
        this.idleTime = idleTime;
        this.tcpMsgHandler = tcpMsgHandler;
    }

    /**
     * 初始化
     * IdleStateHandler
     *      readerIdleTime：为读超时时间
     *      writerIdleTime：为写超时时间
     *      allIdleTime：所有类型的超时时间
     *      unit：时间类型
     *  TcpServerHandler 心跳检测Handler - 用于接收数据
     * @param channel
     * @throws Exception
     */
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast("Decoder", new ByteArrayDecoder());
        channel.pipeline().addLast("Encoder", new ByteArrayEncoder());
        channel.pipeline().addLast("IdleStateHandler", new IdleStateHandler(0L, 0L, this.idleTime, TimeUnit.SECONDS));
        channel.pipeline().addLast("TcpServerHandler", new TcpServerHandler(this.tcpMsgHandler));
    }
}