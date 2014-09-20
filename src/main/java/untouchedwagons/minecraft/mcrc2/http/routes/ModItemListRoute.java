package untouchedwagons.minecraft.mcrc2.http.routes;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import untouchedwagons.minecraft.mcrc2.http.routing.RouteHandler;
import untouchedwagons.minecraft.mcrc2.registry.GameRegistry;
import untouchedwagons.minecraft.mcrc2.registry.Item;
import untouchedwagons.minecraft.mcrc2.registry.MinecraftMod;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class ModItemListRoute implements RouteHandler {
    private final GameRegistry game_registry;

    public static final Pattern mod_regex = Pattern.compile("^/item-list/(.+)$");

    public ModItemListRoute(GameRegistry game_registry) {
        this.game_registry = game_registry;
    }

    @Override
    public FullHttpResponse route(ChannelHandlerContext ctx, HttpMethod method, String uri, FullHttpRequest request) {
        Matcher matcher = mod_regex.matcher(uri);
        matcher.find();

        String mod_id = matcher.group(1);

        if (!this.game_registry.getMods().containsKey(mod_id))
            return new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);

        String item_list = this.getItemListForMod(mod_id);

        ByteBuf content = ctx.alloc().buffer();
        content.writeBytes(item_list.getBytes());

        FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);

        res.headers().set(CONTENT_TYPE, String.format("application/json; charset=UTF-8"));
        HttpHeaders.setContentLength(res, content.readableBytes());

        return res;
    }

    private String getItemListForMod(String mod_id)
    {
        JsonObject item_list_object = new JsonObject();
        MinecraftMod mod = this.game_registry.getMods().get(mod_id);

        for (Map.Entry<String, Item> entry : mod.getItems().entrySet())
        {
            item_list_object.add(entry.getKey(), new JsonPrimitive(entry.getValue().getLocalized_name()));
        }

        return item_list_object.toString();
    }
}
