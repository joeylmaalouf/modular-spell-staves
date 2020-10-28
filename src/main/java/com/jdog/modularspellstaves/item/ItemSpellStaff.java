package jdog.modularspellstaves.item;

import jdog.modularspellstaves.item.ItemRune;
import jdog.modularspellstaves.util.SpellUtil;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;


public class ItemSpellStaff extends Item {

  private static final int COOLDOWN = 40;
  private static final int MAX_MANA = 200;

  private Random rand;

  public ItemSpellStaff() {
    super();
    this.rand = new Random();
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand staffHand) {
    EnumActionResult result = EnumActionResult.PASS;

    ItemStack staffStack = player.getHeldItem(staffHand);
    if (!staffStack.hasTagCompound()) {
      staffStack = initializeStaffNBT(staffStack);
    }
    NBTTagCompound staffNbt = staffStack.getTagCompound();
    NBTTagList staffRuneList = staffNbt.hasKey("runes") ? staffNbt.getTagList("runes", NBT.TAG_COMPOUND) : new NBTTagList();

    if (player.isSneaking()) {
      EnumHand runeHand = staffHand == EnumHand.MAIN_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
      ItemStack runeStack = player.getHeldItem(runeHand);
      Item rune = runeStack.getItem();
      if (rune instanceof ItemRune && canInsertRune(staffRuneList, (ItemRune)rune)) {
        if (!world.isRemote) {
          staffRuneList.appendTag(runeStack.serializeNBT());
          runeStack.setCount(0);
          player.inventory.markDirty();
        }
        player.swingArm(runeHand);
        result = EnumActionResult.SUCCESS;
      }
      else if (runeStack.isEmpty() && staffRuneList.tagCount() > 0) {
        if (!world.isRemote) {
          int maxIndex = staffRuneList.tagCount() - 1;
          NBTTagCompound removedRune = staffRuneList.getCompoundTagAt(maxIndex);
          staffRuneList.removeTag(maxIndex);
          player.setHeldItem(runeHand, new ItemStack(removedRune));
          player.inventory.markDirty();
        }
        player.swingArm(runeHand);
        result = EnumActionResult.SUCCESS;
      }
      staffNbt.setTag("runes", staffRuneList);
      staffStack.setTagCompound(staffNbt);
    }
    else if (canCastSpell(world.getTotalWorldTime() - staffNbt.getLong("lastUsed"), staffNbt.getInteger("mana"), staffRuneList)) {
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
      if (SpellUtil.validTargetsExist(targetRune, modifierRunes, player)) {
        if (!world.isRemote) {
          SpellUtil.castSpell(targetRune, effectRunes, modifierRunes, player);
          staffNbt.setLong("lastUsed", world.getTotalWorldTime());
          staffNbt.setInteger("mana", staffNbt.getInteger("mana") - getManaCost(staffRuneList));
        }
        spawnSpellParticles(world, player, this.rand);
        player.swingArm(staffHand);
        result = EnumActionResult.SUCCESS;
      }
    }

    return new ActionResult<ItemStack>(result, staffStack);
  }

  private static ItemStack initializeStaffNBT(ItemStack stack) {
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setLong("lastUsed", 0);
    nbt.setInteger("mana", MAX_MANA);
    stack.setTagCompound(nbt);
    return stack;
  }

  private static boolean canInsertRune(NBTTagList staffRuneList, ItemRune newRune) {
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

  private static boolean canCastSpell(long ticksSinceUsed, int mana, NBTTagList staffRuneList) {
    boolean offCooldown = ticksSinceUsed >= COOLDOWN;
    boolean hasMana = mana >= getManaCost(staffRuneList);
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
    return offCooldown && hasMana && hasTarget && hasEffect;
  }

  private static int getManaCost(NBTTagList staffRuneList) {
    boolean targetsSelf = false;
    int totalCost = 0;
    for (int i = 0; i < staffRuneList.tagCount(); ++i) {
      ItemRune rune = (ItemRune)(new ItemStack(staffRuneList.getCompoundTagAt(i)).getItem());
      if (rune.getType().equals("self")) {
        targetsSelf = true;
        break;
      }
    }
    for (int i = 0; i < staffRuneList.tagCount(); ++i) {
      ItemRune rune = (ItemRune)(new ItemStack(staffRuneList.getCompoundTagAt(i)).getItem());
      if (!targetsSelf || (!rune.getType().equals("enlarge") && !rune.getType().equals("reduce"))) {
        totalCost += rune.getManaCost();
      }
    }
    return Math.max(totalCost, 1);
  }

  private static void spawnSpellParticles(World world, EntityPlayer player, Random rand) {
    BlockPos position = player.getPosition();
    for (int i = 0; i < 8; ++i) {
      double x = position.getX() + (rand.nextDouble() - 0.5d) * (double)player.width;
      double y = position.getY() + rand.nextDouble() * (double)player.height;
      double z = position.getZ() + (rand.nextDouble() - 0.5d) * (double)player.width;
      world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, x, y, z, rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
    }
  }

  @Override
  public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
    if (stack.hasTagCompound()) {
      NBTTagCompound nbt = stack.getTagCompound();
      if (nbt.getInteger("mana") < MAX_MANA && this.rand.nextInt(1200) == 0) {
        nbt.setInteger("mana", nbt.getInteger("mana") + 1);
      }
    }
  }

  @Override
  public boolean showDurabilityBar(ItemStack stack) {
    int durability = MAX_MANA;
    if (stack.hasTagCompound()) {
      durability = stack.getTagCompound().getInteger("mana");
    }
    return durability != MAX_MANA;
  }

  @Override
  public double getDurabilityForDisplay(ItemStack stack) {
    int damage = 0;
    if (stack.hasTagCompound()) {
      damage = MAX_MANA - stack.getTagCompound().getInteger("mana");
    }
    return (double)damage / (double)MAX_MANA;
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
      tooltip.add("Mana: " + nbt.getInteger("mana") + "/" + MAX_MANA);
    }
  }

}
