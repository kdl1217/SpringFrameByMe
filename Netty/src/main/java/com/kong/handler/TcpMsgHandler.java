package com.kong.handler;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Receive Message Handler
 * Created by Kong on 2018/1/8.
 */
public class TcpMsgHandler implements ITcpMsgHandler{

    private static final Logger logger = LoggerFactory.getLogger("[UMsgHandler]");

    @Override
    public void handler(ChannelHandlerContext channelHandlerContext, byte[] bytes) {
        logger.info("receive message: {}", bytes);
    }

}