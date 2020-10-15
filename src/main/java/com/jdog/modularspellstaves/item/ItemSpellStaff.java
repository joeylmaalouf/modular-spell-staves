package jdog.modularspellstaves.item;

import jdog.modularspellstaves.item.ItemRune;
import jdog.modularspellstaves.util.SpellUtil;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;


public class ItemSpellStaff extends Item {

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand staffHand) {
    EnumActionResult result = EnumActionResult.PASS;

    ItemStack staffStack = player.getHeldItem(staffHand);
    if (!world.isRemote) {
      NBTTagCompound staffNbt = staffStack.hasTagCompound() ? staffStack.getTagCompound() : new NBTTagCompound();
      NBTTagList staffRuneList = staffNbt.hasKey("runes") ? staffNbt.getTagList("runes", NBT.TAG_COMPOUND) : new NBTTagList();

      if (player.isSneaking()) {
        EnumHand runeHand = staffHand == EnumHand.MAIN_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
        ItemStack runeStack = player.getHeldItem(runeHand);
        Item rune = runeStack.getItem();
        if (rune instanceof ItemRune && canInsertRune(staffRuneList, (ItemRune)rune)) {
          staffRuneList.appendTag(runeStack.serializeNBT());
          runeStack.setCount(0);
          player.inventory.markDirty();
          result = EnumActionResult.SUCCESS;
        }
        else if (runeStack.isEmpty() && staffRuneList.tagCount() > 0) {
          int maxIndex = staffRuneList.tagCount() - 1;
          NBTTagCompound removedRune = staffRuneList.getCompoundTagAt(maxIndex);
          staffRuneList.removeTag(maxIndex);
          player.setHeldItem(runeHand, new ItemStack(removedRune));
          player.inventory.markDirty();
          result = EnumActionResult.SUCCESS;
        }
        staffNbt.setTag("runes", staffRuneList);
        staffStack.setTagCompound(staffNbt);
      }
      else if (canCastSpell(staffRuneList)) {
        ItemRune targetRune = null;
        List<ItemRune> effectRunes = new ArrayList<ItemRune>();
        List<ItemRune> modifierRunes = new ArrayList<ItemRune>();
        for (int i = 0; i < staffRuneList.tagCount(); ++i) {
          ItemRune rune = (ItemRune)(new ItemStack(staffRuneList.getCompoundTagAt(i)).getItem());
          switch (rune.getCategory()) {
            case "target":
              targetRune = rune;
              break;
            case "effect":
              effectRunes.add(rune);
              break;
            case "modifier":
              modifierRunes.add(rune);
              break;
          }
        }
        SpellUtil.cast(targetRune, effectRunes, modifierRunes, player);
        result = EnumActionResult.SUCCESS;
      }
    }

    return new ActionResult<ItemStack>(result, staffStack);
  }

  public static boolean canInsertRune(NBTTagList staffRuneList, ItemRune newRune) {
    boolean isDuplicateType = false;
    boolean isAdditionalTarget = false;
    for (int i = 0; i < staffRuneList.tagCount(); ++i) {
      ItemRune rune = (ItemRune)(new ItemStack(staffRuneList.getCompoundTagAt(i)).getItem());
      if (newRune.getType().equals(rune.getType())) {
        isDuplicateType = true;
        break;
      }
      if (newRune.getCategory().equals("target") && rune.getCategory().equals("target")) {
        isAdditionalTarget = true;
        break;
      }
    }
    return !isDuplicateType && !isAdditionalTarget;
  }

  public static boolean canCastSpell(NBTTagList staffRuneList) {
    boolean hasTarget = false;
    boolean hasEffect = false;
    for (int i = 0; i < staffRuneList.tagCount(); ++i) {
      ItemRune rune = (ItemRune)(new ItemStack(staffRuneList.getCompoundTagAt(i)).getItem());
      switch (rune.getCategory()) {
        case "target":
          hasTarget = true;
          break;
        case "effect":
          hasEffect = true;
          break;
      }
    }
    return hasTarget && hasEffect;
  }

  @Override
  public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
    if (stack.hasTagCompound()) {
      NBTTagCompound nbt = stack.getTagCompound();
      if (nbt.hasKey("runes")) {
        NBTTagList runes = nbt.getTagList("runes", NBT.TAG_COMPOUND);
        List<String> runeNames = new ArrayList<String>();
        for (int i = 0; i < runes.tagCount(); ++i) {
          runeNames.add(((ItemRune)(new ItemStack(runes.getCompoundTagAt(i)).getItem())).getTypeForDisplay());
        }
        if (runeNames.size() > 0) {
          tooltip.add("Rune" + (runeNames.size() > 1 ? "s" : "") + ": " + String.join(", ", runeNames));
        }
      }
    }
  }

}
