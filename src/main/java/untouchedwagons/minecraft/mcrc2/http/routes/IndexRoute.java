package untouchedwagons.minecraft.mcrc2.http.routes;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import net.minecraft.util.ResourceLocation;
import untouchedwagons.minecraft.mcrc2.http.routing.RouteHandler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class IndexRoute implements RouteHandler {
    private final String page;

    public IndexRoute() {
        InputStream page = this.getClass().getResourceAsStream("/assets/mcrc2/web/index.html");
        InputStreamReader is_reader = new InputStreamReader(page);
        BufferedReader b_reader = new BufferedReader(is_reader);

        StringBuilder s_builder = new StringBuilder(4096);
        String line;

        try {
            while ((line = b_reader.readLine()) != null) {
                s_builder.append(line);
            }
        }
        catch (Exception e)
        {
            //
        }

        this.page = s_builder.toString();
    }

    @Override
    public FullHttpResponse route(ChannelHandlerContext ctx, HttpMethod method, String uri, FullHttpRequest request) {
        ByteBuf content = ctx.alloc().buffer();

        content.writeBytes(this.page.getBytes());

        FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);

        res.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
        HttpHeaders.setContentLength(res, content.readableBytes());

        return res;
    }
}
