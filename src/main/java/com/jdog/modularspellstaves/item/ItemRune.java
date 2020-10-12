package jdog.modularspellstaves.item;

import net.minecraft.item.Item;


public class ItemRune extends Item {

  private String category;
  private String type;

  public ItemRune(String category, String type) {
    this.category = category;
    this.type = type;
  }

  public String getCategory() {
    return this.category;
  }

  public String getType() {
    return this.type;
  }

}
