package jdog.modularspellstaves.util;

import jdog.modularspellstaves.ModularSpellStaves;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;


@EventBusSubscriber(modid = ModularSpellStaves.MODID)
public class RegistrationHandler {

  @SubscribeEvent
  public static void registerItems(Register<Item> event) {
    final Item[] items = {
        createItem(new Item(), "spell_staff").setMaxStackSize(1)
    };

    event.getRegistry().registerAll(items);
  }

  public static Item createItem(Item item, String name) {
    return item
      .setRegistryName(ModularSpellStaves.MODID, name)
      .setTranslationKey(ModularSpellStaves.MODID + "." + name)
      .setCreativeTab(ModularSpellStaves.CREATIVE_TAB);
  }

}
