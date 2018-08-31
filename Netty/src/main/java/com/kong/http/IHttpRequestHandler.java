package com.kong.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;
import java.util.Map;

/**
 * Netty 支持Http格式
 * Created by Kong on 2018/1/8.
 */
public interface IHttpRequestHandler {

    void requestHandler(ChannelHandlerContext context, FullHttpRequest request, Map<String, List<String>> map);

}
