package untouchedwagons.minecraft.mcrc2.http.routes;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import untouchedwagons.minecraft.mcrc2.http.routing.RouteHandler;

import java.io.IOException;
import java.io.InputStream;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class StaticPageRoute implements RouteHandler {
    private final String mime_type;

    public StaticPageRoute(String mime_type) {
        this.mime_type = mime_type;
    }

    @Override
    public FullHttpResponse route(ChannelHandlerContext ctx, HttpMethod method, String uri, FullHttpRequest request) {
        if (uri.equals("/"))
            uri = "/index.html";

        uri = "/assets/mcrc2/web" + uri;

        InputStream uri_file = this.getClass().getResourceAsStream(uri);

        if (uri_file == null)
            return new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);

        byte[] buffer = new byte[4096];
        int bytes_read;
        ByteBuf content = ctx.alloc().buffer();

        try {
            while ((bytes_read = uri_file.read(buffer)) > 0)
            {
                content.writeBytes(buffer, 0, bytes_read);
            }
        } catch (IOException e) {
            //
        }

        FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);

        res.headers().set(CONTENT_TYPE, String.format("%s; charset=UTF-8", this.mime_type));
        HttpHeaders.setContentLength(res, content.readableBytes());

        return res;
    }
}
