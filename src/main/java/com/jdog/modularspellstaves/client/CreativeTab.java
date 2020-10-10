package jdog.modularspellstaves.client;

import jdog.modularspellstaves.ModularSpellStaves;
import jdog.modularspellstaves.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class CreativeTab extends CreativeTabs {

  public CreativeTab() {
    super(ModularSpellStaves.MODID);
  }
  
  @SideOnly(Side.CLIENT)
  @Override
  public ItemStack createIcon() {
    return new ItemStack(ModItems.SPELL_STAFF);
  }
  
}
