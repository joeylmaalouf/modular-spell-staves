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

      createRune(new ItemRune("target", "self")),
      createRune(new ItemRune("target", "touch")),

      createRune(new ItemRune("effect", "heal")),
      createRune(new ItemRune("effect", "harm")),

      createRune(new ItemRune("modifier", "empower")),
      createRune(new ItemRune("modifier", "inhibit"))
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

  public static Item createRune(Item item) {
    return createItem(item, "rune_" + ((ItemRune)item).getType());
  }

}
