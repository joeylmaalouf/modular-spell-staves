package jdog.modularspellstaves.client;

import jdog.modularspellstaves.init.ModItems;
import jdog.modularspellstaves.item.ItemRune;
import jdog.modularspellstaves.ModularSpellStaves;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.relauncher.Side;


@EventBusSubscriber(modid = ModularSpellStaves.MODID, value = Side.CLIENT)
public class ClientRegistrationHandler {

  @SubscribeEvent
  public static void registerModels(ModelRegistryEvent event) {
    registerModel(ModItems.SPELL_STAFF, 0);

    registerModelAndDescription(ModItems.RUNE_SELF, 0);
    registerModelAndDescription(ModItems.RUNE_TOUCH, 0);

    registerModelAndDescription(ModItems.RUNE_HEAL, 0);
    registerModelAndDescription(ModItems.RUNE_HARM, 0);
    registerModelAndDescription(ModItems.RUNE_SPEED, 0);
    registerModelAndDescription(ModItems.RUNE_RESISTANCE, 0);
    registerModelAndDescription(ModItems.RUNE_FIRE, 0);

    registerModelAndDescription(ModItems.RUNE_EMPOWER, 0);
    registerModelAndDescription(ModItems.RUNE_INHIBIT, 0);
    registerModelAndDescription(ModItems.RUNE_ENLARGE, 0);
    registerModelAndDescription(ModItems.RUNE_REDUCE, 0);
  }

  private static void registerModel(Item item, int meta) {
    ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
  }

  private static void registerModelAndDescription(Item item, int meta) {
    registerModel(item, meta);
    ((ItemRune)item).setDescription(I18n.format(item.getTranslationKey() + ".description"));
  }

}
