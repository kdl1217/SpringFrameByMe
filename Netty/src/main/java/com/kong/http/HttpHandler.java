package com.kong.http;

import com.kong.handler.ServerHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;
import java.util.Map;


public class HttpHandler implements IHttpRequestHandler {

    private static final String ADDRESS = "/netty/setData" ;

    private static final String MSG_PARAM = "msg" ;
    private String msg ;

    public HttpHandler() {
    }

    @Override
    public void requestHandler(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest, Map<String, List<String>> map) {
        if (fullHttpRequest.uri().contains(ADDRESS)){
            List<String> list = map.get(MSG_PARAM) ;
            if (null != list && list.size() > 0){
                msg = list.get(0) ;
            }
            new ServerHandler().callChannelRead(channelHandlerContext,fullHttpRequest,msg);
        }

    }

}
