package com.kong.handler;

import io.netty.channel.ChannelHandlerContext;

/**
 * Msg Receive Interface
 * Created by Kong on 2018/1/8.
 */
public interface ITcpMsgHandler {

    /**
     * 消息处理
     * @param channel
     * @param bytes
     */
    void handler(ChannelHandlerContext channel, byte[] bytes);
}
