package com.kong.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * Created by Kong on 2018/1/8.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private HttpRequest request = null;
    private FullHttpResponse response = null;

    private String resultMsg  = null ;

    public void callChannelRead(ChannelHandlerContext ctx, Object msg,String result){
        resultMsg = result ;
        channelRead(ctx, msg) ;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;
            try {
                setResponseBody(resultMsg);
            } catch (Exception e) {//处理出错，返回错误信息
                setResponseBody("Server Error");
            }
            if(response!=null)
                ctx.write(response).addListener((ChannelFutureListener) channelFuture ->{
                    if (channelFuture.isSuccess()) {
                        System.out.println("执行成功！");
                        exceptionCaught(ctx,null);
                    } else {
                        System.out.println("执行失败！");
                        exceptionCaught(ctx,null);
                    }
                }) ;
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            System.out.println(buf.toString(CharsetUtil.UTF_8));
            buf.release();
        }
    }

    /**
     * 设置Body
     * @param msg
     */
    private void setResponseBody(String msg){
        try {
            String res = "<html><body>"+msg+"</body></html>";
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(res.getBytes("UTF-8")));
            setHeaders(response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 设置HTTP返回头信息
     */
    private void setHeaders(FullHttpResponse response) {
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html");
        response.headers().set(HttpHeaders.Names.CONTENT_LANGUAGE, response.content().readableBytes());
        if (HttpHeaders.isKeepAlive(request)) {
            response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server channelReadComplete..");
        ctx.flush();//刷新后才将数据发出到SocketChannel
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println("server exceptionCaught..");
        ctx.close();
    }
}
