package untouchedwagons.minecraft.mcrc2.http.exceptions;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class RouteNotFoundException extends Throwable {
    public RouteNotFoundException(ChannelHandlerContext ctx, FullHttpRequest request) {
    }
}
