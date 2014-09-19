package untouchedwagons.minecraft.mcrc2.http.routing;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;

public interface RouteHandler {
    public FullHttpResponse route(ChannelHandlerContext ctx, HttpMethod method, String uri, FullHttpRequest request);
}
