package jdog.modularspellstaves.util;

import jdog.modularspellstaves.item.ItemRune;
import jdog.modularspellstaves.item.ItemSpellStaff;
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
      createItem(new ItemSpellStaff(), "spell_staff"),

      createItem(new ItemRune("target", "self"), "rune_self"),

      createItem(new ItemRune("effect", "heal"), "rune_heal"),
      createItem(new ItemRune("effect", "harm"), "rune_harm"),

      createItem(new ItemRune("modifier", "empower"), "rune_empower")
    };

    event.getRegistry().registerAll(items);
  }

  public static Item createItem(Item item, String name) {
    return item
      .setRegistryName(ModularSpellStaves.MODID, name)
      .setTranslationKey(ModularSpellStaves.MODID + "." + name)
      .setCreativeTab(ModularSpellStaves.CREATIVE_TAB)
      .setMaxStackSize(1);
  }

}
