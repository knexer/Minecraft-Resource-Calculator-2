package untouchedwagons.minecraft.mcrc2;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import untouchedwagons.minecraft.mcrc2.proxy.CommonProxy;

@Mod(modid = "mcrc2", name = "Minecraft Resource Calculator 2", version = "0.7.1")
public class MinecraftResourceCalculatorMod
{
    @SidedProxy(clientSide = "untouchedwagons.minecraft.mcrc2.proxy.ClientProxy", serverSide = "untouchedwagons.minecraft.mcrc2.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.collectRecipes();
        proxy.startWebServer();
    }
}
