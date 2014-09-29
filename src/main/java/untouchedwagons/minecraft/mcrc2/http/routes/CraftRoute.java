package untouchedwagons.minecraft.mcrc2.http.routes;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import untouchedwagons.minecraft.mcrc2.http.routing.RouteHandler;
import untouchedwagons.minecraft.mcrc2.registry.GameRegistry;

import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class CraftRoute implements RouteHandler {
    private final GameRegistry game_registry;

    public static final Pattern CraftRegex = Pattern.compile("^/craft/(.+)/(\\d+)$");

    public CraftRoute(GameRegistry game_registry) {
        this.game_registry = game_registry;
    }

    @Override
    public FullHttpResponse route(ChannelHandlerContext ctx, HttpMethod method, String uri, FullHttpRequest request) {
        return new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
    }
}
