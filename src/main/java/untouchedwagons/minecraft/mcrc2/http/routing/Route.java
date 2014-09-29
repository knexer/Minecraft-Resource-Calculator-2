package untouchedwagons.minecraft.mcrc2.http.routing;

import io.netty.handler.codec.http.HttpMethod;

import java.util.regex.Pattern;

public class Route {
    private HttpMethod method;
    private Pattern pattern;
    private RouteHandler handler;

    public Route(HttpMethod method, String path, RouteHandler handler)
    {
        this.method = method;
        this.pattern = Pattern.compile(path);
        this.handler = handler;
    }

    public Route(HttpMethod method, Pattern pattern, RouteHandler handler)
    {
        this.method = method;
        this.pattern = pattern;
        this.handler = handler;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public RouteHandler getHandler() {
        return handler;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
