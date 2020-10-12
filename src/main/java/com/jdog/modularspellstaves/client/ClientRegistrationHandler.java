package jdog.modularspellstaves.client;

import jdog.modularspellstaves.init.ModItems;
import jdog.modularspellstaves.ModularSpellStaves;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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

		registerModel(ModItems.RUNE_SELF, 0);

		registerModel(ModItems.RUNE_HEAL, 0);
		registerModel(ModItems.RUNE_HARM, 0);

		registerModel(ModItems.RUNE_EMPOWER, 0);
  }

  private static void registerModel(Item item, int meta) {
    ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
  }

}
