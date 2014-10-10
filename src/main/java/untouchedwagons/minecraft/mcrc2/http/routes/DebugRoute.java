package untouchedwagons.minecraft.mcrc2.http.routes;

import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import untouchedwagons.minecraft.mcrc2.http.routing.RouteHandler;
import untouchedwagons.minecraft.mcrc2.registry.GameRegistry;
import untouchedwagons.minecraft.mcrc2.registry.MinecraftItem;
import untouchedwagons.minecraft.mcrc2.views.debugging.ItemView;

import java.util.Map;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpResponseStatus.SERVICE_UNAVAILABLE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class DebugRoute implements RouteHandler {
    public static final Pattern DebugRegex = Pattern.compile("^/debug$");

    private final GameRegistry game_registry;

    public DebugRoute(GameRegistry game_registry) {
        this.game_registry = game_registry;
    }

    @Override
    public FullHttpResponse route(ChannelHandlerContext ctx, HttpMethod method, String uri, FullHttpRequest request) {
        if (!this.game_registry.isReady())
            return new DefaultFullHttpResponse(HTTP_1_1, SERVICE_UNAVAILABLE);

        JsonObject items_object = new JsonObject();

        for(Map.Entry<String, MinecraftItem> item : this.game_registry.getItems().entrySet())
        {
            ItemView item_view = new ItemView(this.game_registry);
            item_view.process(item.getValue());

            items_object.add(item.getKey(), item_view.getJsonObject());
        }

        ByteBuf content = ctx.alloc().buffer();
        content.writeBytes(items_object.toString().getBytes());

        FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);

        res.headers().set(CONTENT_TYPE, String.format("application/json; charset=UTF-8"));
        HttpHeaders.setContentLength(res, content.readableBytes());

        return res;
    }
}
