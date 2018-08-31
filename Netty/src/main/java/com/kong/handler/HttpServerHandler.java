package com.kong.handler;

import com.kong.http.IHttpRequestHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.*;

/**
 * Netty Http Handler
 * Created by Kong on 2018/1/8.
 */
public class HttpServerHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger("[HttpServerHandler]");
    private IHttpRequestHandler httpRequestHandler;
    private HttpPostRequestDecoder decoder;
    private static final HttpDataFactory factory = new DefaultHttpDataFactory(16384L);

    public HttpServerHandler(IHttpRequestHandler httpRequestHandler) {
        this.httpRequestHandler = httpRequestHandler;
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (msg instanceof HttpRequest) {
            FullHttpRequest req = (FullHttpRequest)msg;
            if (HttpUtil.is100ContinueExpected(req)) {
                ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
            } else {
                List par;
                if (req.method() == HttpMethod.POST) {
                    try {
                        this.decoder = new HttpPostRequestDecoder(factory, req, CharsetUtil.UTF_8);
                        Map<String, List<String>> map = new LinkedHashMap<>();
                        par = this.decoder.getBodyHttpDatas();
                        Iterator var6 = par.iterator();

                        InterfaceHttpData data;
                        while(var6.hasNext()) {
                            data = (InterfaceHttpData)var6.next();
                            if (data.getHttpDataType() == HttpDataType.Attribute) {
                                Attribute attribute = (Attribute)data;
                                if (map.containsKey(attribute.getName())) {
                                    (map.get(attribute.getName())).add(attribute.getValue());
                                } else {
                                    map.put(attribute.getName(), Arrays.asList(attribute.getValue()));
                                }
                            } else {
                                this.logger.warn("other" + data.getHttpDataType().name());
                            }
                        }

                        try {
                            URI uri = new URI(req.uri());
                            String query = uri.getQuery();
                            if (query != null && query.length() > 0) {
                                QueryStringDecoder uriDecoder = new QueryStringDecoder(uri.getQuery(), false);
                                Map<String, List<String>> mapList = uriDecoder.parameters();
                                map.putAll(mapList);
                            }
                        } catch (Exception var10) {
                            this.logger.warn("uri error", var10);
                        }

                        this.httpRequestHandler.requestHandler(ctx, req, map);
                        this.decoder.cleanFiles();
                        this.decoder.destroy();
                    } catch (Exception var11) {
                        this.logger.error("e1", var11);
                        ctx.close();
                        return;
                    }
                } else if (req.method() == HttpMethod.GET) {
                    try {
                        URI uri = new URI(req.uri());
                        String query = uri.getQuery();
                        Map<String, List<String>> listMap ;
                        if (query != null && query.length() > 0) {
                            QueryStringDecoder uriDecoder = new QueryStringDecoder(uri.getQuery(), false);
                            listMap = uriDecoder.parameters();
                        } else {
                            listMap = new HashMap<>();
                        }

                        this.httpRequestHandler.requestHandler(ctx, req, listMap);
                    } catch (Exception var12) {
                        var12.printStackTrace();
                        ctx.close();
                    }
                }
            }
        }

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}