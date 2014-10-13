package untouchedwagons.minecraft.mcrc2;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import net.minecraftforge.common.config.Configuration;
import untouchedwagons.minecraft.mcrc2.proxy.CommonProxy;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mod(modid = "mcrc2", name = "Minecraft Resource Calculator 2", version = "0.7.8")
public class MinecraftResourceCalculatorMod
{
    @SidedProxy(clientSide = "untouchedwagons.minecraft.mcrc2.proxy.ClientProxy", serverSide = "untouchedwagons.minecraft.mcrc2.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static boolean do_logging = false;
    public static PrintStream error_logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();
        config.get("logging", "log-exceptions", false, "Any exceptions caught will be saved to mcrc2.log in the root of your minecraft instance.");

        if (config.hasChanged()) config.save();

        if (config.get("logging", "log-exceptions", false).getBoolean())
        {
            DateFormat date_format = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
            String file_name = String.format("mcrc2-%s.log", date_format.format(new Date()));

            try {
                error_logger = new PrintStream(file_name);
                do_logging = true;

                Runtime.getRuntime().addShutdownHook(new ErrorLoggerCloser());
            } catch (FileNotFoundException e) {
                FMLLog.warning("Could not create log file '%s'", file_name);
            }
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.collectRecipes();
        proxy.startWebServer();
    }

    private class ErrorLoggerCloser extends Thread
    {
        @Override
        public void run() {
            super.run();

            MinecraftResourceCalculatorMod.error_logger.flush();
            MinecraftResourceCalculatorMod.error_logger.close();
        }
    }
}
