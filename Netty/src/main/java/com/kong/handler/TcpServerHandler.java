package com.kong.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *1
 * Created by Kong on 2018/1/8.
 */
public class TcpServerHandler extends SimpleChannelInboundHandler<byte[]> {

    private static final Logger logger = LoggerFactory.getLogger(String.format("[%12s]", "TcpHandler"));
    private ITcpMsgHandler tcpMsgHandler;

    public TcpServerHandler(ITcpMsgHandler tcpMsgHandler) {
        this.tcpMsgHandler = tcpMsgHandler;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        //Registered
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Passive disconnect the TCP channel : {}", ctx.channel().localAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, byte[] buffer) throws Exception {
        //处理TCP消息
        this.tcpMsgHandler.handler(channelHandlerContext, buffer);
    }
}
