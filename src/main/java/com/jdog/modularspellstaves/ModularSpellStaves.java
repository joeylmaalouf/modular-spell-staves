import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;

@Mod(modid = ModularSpellStaves.MODID, name = ModularSpellStaves.NAME, version = ModularSpellStaves.VERSION)
public class ModularSpellStaves {
  public static final String MODID = "modularspellstaves";
  public static final String NAME = "Modular Spell Staves";
  public static final String VERSION = "1.0";

  private static Logger logger;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    // some example code
    logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
  }
}
