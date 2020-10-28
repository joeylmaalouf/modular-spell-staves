package jdog.modularspellstaves.item;

import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class ItemRune extends Item {

  private String category;
  private String type;
  private int manaCost;
  private String description;

  public ItemRune(String category, String type, int manaCost) {
    this.category = category;
    this.type = type;
    this.manaCost = manaCost;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategory() {
    return this.category;
  }

  public String getType() {
    return this.type;
  }

  public String getTypeForDisplay() {
    return this.type.substring(0, 1).toUpperCase() + this.type.substring(1).toLowerCase();
  }

  public int getManaCost() {
    return this.manaCost;
  }

  @Override
  public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
    tooltip.add(this.description);
  }

}
