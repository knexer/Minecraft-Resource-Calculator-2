package untouchedwagons.minecraft.mcrc2.http.routes;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import untouchedwagons.minecraft.mcrc2.api.ILocalizationRegistry;
import untouchedwagons.minecraft.mcrc2.crafting.CraftingTree;
import untouchedwagons.minecraft.mcrc2.exceptions.InfiniteRecursionException;
import untouchedwagons.minecraft.mcrc2.http.routing.RouteHandler;
import untouchedwagons.minecraft.mcrc2.registry.GameRegistry;

import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class CraftRoute implements RouteHandler {
    private final GameRegistry game_registry;

    public static final Pattern CraftRegex = Pattern.compile("^/craft/(\\w+):(.+)/(\\d+)$");

    public CraftRoute(GameRegistry game_registry) {
        this.game_registry = game_registry;
    }

    @Override
    public FullHttpResponse route(ChannelHandlerContext ctx, HttpMethod method, String uri, FullHttpRequest request) {
        Matcher matcher = CraftRoute.CraftRegex.matcher(uri);

        if (!matcher.find())
            return new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST);

        String item_domain = matcher.group(1);
        String item_name = matcher.group(2);
        Integer item_amount = Integer.parseInt(matcher.group(3));

        try {
            CraftingTree crafting_tree = new CraftingTree(this.game_registry,
                    new HashMap<String, Integer>(),
                    new HashMap<String, Integer>(),
                    System.currentTimeMillis());
            crafting_tree.craft(item_domain, item_name, item_amount);

            System.out.println("Done crafting");
        } catch (InfiniteRecursionException e) {
            return new DefaultFullHttpResponse(HTTP_1_1, new HttpResponseStatus(508, "Loop Detected"));
        } catch (Exception e) {
            e.printStackTrace();
            return new DefaultFullHttpResponse(HTTP_1_1, INTERNAL_SERVER_ERROR);
        }

        ByteBuf content = ctx.alloc().buffer();
        FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);

        res.headers().set(CONTENT_TYPE, String.format("text/html; charset=UTF-8"));
        HttpHeaders.setContentLength(res, content.readableBytes());

        return res;
    }
}
