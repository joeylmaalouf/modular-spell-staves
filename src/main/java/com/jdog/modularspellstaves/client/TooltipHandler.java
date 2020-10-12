package jdog.modularspellstaves.client;

import jdog.modularspellstaves.item.ItemRune;
import jdog.modularspellstaves.ModularSpellStaves;

import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.relauncher.Side;


@EventBusSubscriber(modid = ModularSpellStaves.MODID, value = Side.CLIENT)
public class TooltipHandler {

  @SubscribeEvent
  public static void itemToolTip(ItemTooltipEvent event) {
    Item item = event.getItemStack().getItem();
    if (item instanceof ItemRune) {
      event.getToolTip().add("TODO: get shortdesc for rune: " + ((ItemRune)item).getType());
    }
  }

}
