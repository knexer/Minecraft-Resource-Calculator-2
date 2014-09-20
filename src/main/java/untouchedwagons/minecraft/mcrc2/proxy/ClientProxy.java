package untouchedwagons.minecraft.mcrc2.proxy;

import io.netty.handler.codec.http.HttpMethod;
import untouchedwagons.minecraft.mcrc2.http.WebSocketServer;
import untouchedwagons.minecraft.mcrc2.http.routes.ModItemListRoute;
import untouchedwagons.minecraft.mcrc2.http.routes.ModListRoute;
import untouchedwagons.minecraft.mcrc2.http.routes.StaticPageRoute;
import untouchedwagons.minecraft.mcrc2.registry.GameRegistry;

public class ClientProxy extends CommonProxy
{
    private final GameRegistry game_registry;

    public ClientProxy() {
        this.game_registry = new GameRegistry();
    }

    @Override
    public void collectRecipes() {
        GameRegistryPopulator populator = new GameRegistryPopulator(this.game_registry);
        populator.start();
    }

    @Override
    public void startWebServer() {
        System.out.println("Starting Web Server");

        WebSocketServer server = new WebSocketServer();

        server.getRouter().addRoute(HttpMethod.GET, "\\.png$", new StaticPageRoute("image/png"));
        server.getRouter().addRoute(HttpMethod.GET, "\\.css$", new StaticPageRoute("text/css"));
        server.getRouter().addRoute(HttpMethod.GET, "\\.js$", new StaticPageRoute("application/javascript"));

        server.getRouter().addRoute(HttpMethod.GET, "^/mod-list$", new ModListRoute(this.game_registry));
        server.getRouter().addRoute(HttpMethod.GET, "^/item-list/(.+)$", new ModItemListRoute(this.game_registry));

        server.getRouter().addRoute(HttpMethod.GET, "^.+$", new StaticPageRoute("text/html"));

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GameRegistryPopulator extends Thread
    {
        private final GameRegistry registry;

        private GameRegistryPopulator(GameRegistry registry) {
            this.registry = registry;
        }

        @Override
        public void run() {
            this.registry.collectMods();
            this.registry.collectRecipeProviders();
            this.registry.collectModItems();
            this.registry.collectRecipes();
            this.registry.markAsReady(true);
        }
    }
}
