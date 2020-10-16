package jdog.modularspellstaves.util;

import jdog.modularspellstaves.item.ItemRune;
import jdog.modularspellstaves.math.RayTrace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;


public class SpellUtil {

  public static boolean cast(ItemRune targetRune, List<ItemRune> effectRunes, List<ItemRune> modifierRunes, EntityPlayer player) {
    HashMap<String, Float> modifiers = getModifiers(modifierRunes);
    List<EntityLivingBase> targets = getTargets(targetRune, modifiers, player);

    for (ItemRune effectRune : effectRunes) {
      for (EntityLivingBase target : targets) {
        switch (effectRune.getType()) {
          case "heal":
            target.heal(2.0f * modifiers.get("potencyMultiplier"));
            break;
          case "harm":
            target.attackEntityFrom(DamageSource.causePlayerDamage(player), 2.0f * modifiers.get("potencyMultiplier"));
            break;
        }
      }
    }

    return targets.size() != 0;
  }

  private static HashMap<String, Float> getModifiers(List<ItemRune> modifierRunes) {
    HashMap<String, Float> modifiers = new HashMap<String, Float>();
    modifiers.put("potencyMultiplier", 1.0f);
    modifiers.put("rangeMultiplier", 1.0f);

    for (ItemRune modifierRune : modifierRunes) {
      switch (modifierRune.getType()) {
        case "empower":
          modifiers.put("potencyMultiplier", modifiers.get("potencyMultiplier") * 2.0f);
          break;
        case "inhibit":
          modifiers.put("potencyMultiplier", modifiers.get("potencyMultiplier") * 0.5f);
          break;
        case "enlarge":
          modifiers.put("rangeMultiplier", modifiers.get("rangeMultiplier") * 2.0f);
          break;
        case "reduce":
          modifiers.put("rangeMultiplier", modifiers.get("rangeMultiplier") * 0.5f);
          break;
      }
    }

    return modifiers;
  }

  private static List<EntityLivingBase> getTargets(ItemRune targetRune, HashMap<String, Float> modifiers, EntityPlayer player) {
    List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();

    switch (targetRune.getType()) {
      case "self":
        targets.add(player);
        break;
      case "touch":
        double range = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
        Entity target = RayTrace.findEntityWithinRange(player, (double)(range * modifiers.get("rangeMultiplier")));
        if (target != null && target instanceof EntityLivingBase) {
          targets.add((EntityLivingBase)target);
        }
        break;
    }

    return targets;
  }

}
