package untouchedwagons.minecraft.mcrc2.http.routes;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import untouchedwagons.minecraft.mcrc2.http.routing.RouteHandler;
import untouchedwagons.minecraft.mcrc2.registry.GameRegistry;

import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpResponseStatus.SERVICE_UNAVAILABLE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class ModListRoute implements RouteHandler {
    private GameRegistry game_registry;
    private String mod_list;

    public ModListRoute(GameRegistry game_registry) {
        this.game_registry = game_registry;
    }

    @Override
    public FullHttpResponse route(ChannelHandlerContext ctx, HttpMethod method, String uri, FullHttpRequest request)
    {
        if (!this.game_registry.isReady()) {
            return new DefaultFullHttpResponse(HTTP_1_1, SERVICE_UNAVAILABLE);
        }

        if (this.mod_list == null) {
            populateModList();
        }

        ByteBuf content = ctx.alloc().buffer();
        content.writeBytes(this.mod_list.getBytes());

        FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);

        res.headers().set(CONTENT_TYPE, String.format("application/json; charset=UTF-8"));
        HttpHeaders.setContentLength(res, content.readableBytes());

        return res;
    }

    private void populateModList()
    {
        JsonObject mod_list_object = new JsonObject();

        for (Map.Entry<String, String> mod : this.game_registry.getModNames().entrySet())
        {
            if (this.game_registry.getModItemCounts().get(mod.getKey()) == 0) {
                continue;
            }

            mod_list_object.add(mod.getKey(), new JsonPrimitive(mod.getValue()));
        }

        this.mod_list = mod_list_object.toString();
    }
}
