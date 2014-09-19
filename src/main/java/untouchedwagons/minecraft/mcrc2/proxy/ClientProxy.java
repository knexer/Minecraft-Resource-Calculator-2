package untouchedwagons.minecraft.mcrc2.proxy;

import io.netty.handler.codec.http.HttpMethod;
import untouchedwagons.minecraft.mcrc2.http.WebSocketServer;
import untouchedwagons.minecraft.mcrc2.http.routes.IndexRoute;

public class ClientProxy extends CommonProxy
{
    @Override
    public void collectRecipes() {
        System.out.println("Collecting Recipes");
    }

    @Override
    public void startWebServer() {
        System.out.println("Starting Web Server");

        WebSocketServer server = new WebSocketServer();
        server.getRouter().addRoute(HttpMethod.GET, "^\\/$", new IndexRoute());

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
