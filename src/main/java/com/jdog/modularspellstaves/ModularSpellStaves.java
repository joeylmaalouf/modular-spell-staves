package jdog.modularspellstaves;

import jdog.modularspellstaves.client.CreativeTab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;


@Mod(modid = ModularSpellStaves.MODID, name = ModularSpellStaves.NAME, version = ModularSpellStaves.VERSION)
public class ModularSpellStaves {

  public static final String MODID = "modularspellstaves";
  public static final String NAME = "Modular Spell Staves";
  public static final String VERSION = "1.0";

  public static final CreativeTabs CREATIVE_TAB = new CreativeTab();

  private static Logger logger;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    // nothing to see here
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    logger.info("MSS initialization complete.");
  }

}
