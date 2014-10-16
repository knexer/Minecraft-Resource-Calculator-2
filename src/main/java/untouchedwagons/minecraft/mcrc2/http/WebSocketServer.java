package untouchedwagons.minecraft.mcrc2.http;

import cpw.mods.fml.common.FMLLog;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import untouchedwagons.minecraft.mcrc2.http.routing.Router;

public final class WebSocketServer extends Thread {
    private final int port;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private final Router router;

    public WebSocketServer(int port) {
        this.port = port;

        this.bossGroup = new NioEventLoopGroup(1);
        this.workerGroup = new NioEventLoopGroup();
        this.router = new Router();

        Runtime.getRuntime().addShutdownHook(new WebSocketServerStopper(this));
    }

    @Override
    public void run()
    {
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //.handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new HttpObjectAggregator(65536));
                            pipeline.addLast(new WebSocketServerHandler(router));
                        }
                    });

            Channel ch = b.bind(this.port).sync().channel();

            FMLLog.info("Open your web browser and navigate to http://127.0.0.1:%d/", this.port);

            ch.closeFuture().sync();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void stopServer()
    {
        System.out.println("Stopping server");
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public Router getRouter() {
        return router;
    }

    private final class WebSocketServerStopper extends Thread
    {
        private final WebSocketServer server;

        private WebSocketServerStopper(WebSocketServer server) {
            this.server = server;
        }

        @Override
        public void run()
        {
            this.server.stopServer();
        }
    }
}