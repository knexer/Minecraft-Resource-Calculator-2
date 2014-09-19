package untouchedwagons.minecraft.mcrc2.http.routing;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import untouchedwagons.minecraft.mcrc2.http.exceptions.RouteNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class Router {
    private List<Route> routes;

    public Router()
    {
        this.routes = new ArrayList<Route>();
    }

    public void addRoute(Route route)
    {
        this.routes.add(route);
    }

    public void addRoute(HttpMethod method, String path, RouteHandler handler)
    {
        this.routes.add(new Route(method, path, handler));
    }

    public FullHttpResponse dispatch (ChannelHandlerContext ctx, FullHttpRequest request)
    {
        for (Route route : this.routes)
        {
            if (route.getMethod() != request.getMethod())
                continue;

            if (!route.getPattern().matcher(request.getUri()).find())
                continue;

            return route.getHandler().route(ctx, request.getMethod(), request.getUri(), request);
        }

        return new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
    }
}
