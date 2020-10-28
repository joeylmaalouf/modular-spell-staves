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

      createRune(new ItemRune("target", "self", 5)),
      createRune(new ItemRune("target", "touch", 10)),

      createRune(new ItemRune("effect", "heal", 10)),
      createRune(new ItemRune("effect", "harm", 10)),
      createRune(new ItemRune("effect", "speed", 5)),
      createRune(new ItemRune("effect", "resistance", 5)),

      createRune(new ItemRune("modifier", "empower", 5)),
      createRune(new ItemRune("modifier", "inhibit", -5)),
      createRune(new ItemRune("modifier", "enlarge", 5)),
      createRune(new ItemRune("modifier", "reduce", -5))
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
