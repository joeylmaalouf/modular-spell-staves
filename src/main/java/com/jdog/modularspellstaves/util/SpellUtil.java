package jdog.modularspellstaves.util;

import jdog.modularspellstaves.item.ItemRune;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;


public class SpellUtil {

  public static void cast(ItemRune targetRune, List<ItemRune> effectRunes, List<ItemRune> modifierRunes, EntityPlayer player) {
    HashMap<String, Integer> modifiers = getModifiers(modifierRunes);
    List<EntityLivingBase> targets = getTargets(targetRune, modifiers, player);

    for (ItemRune effectRune : effectRunes) {
      for (EntityLivingBase target : targets) {
        switch (effectRune.getType()) {
          case "heal":
            target.heal(2 * modifiers.get("potencyMultiplier"));
            break;
          case "harm":
            target.attackEntityFrom(DamageSource.causePlayerDamage(player), 2 * modifiers.get("potencyMultiplier"));
            break;
        }
      }
    }
  }

  private static HashMap<String, Integer> getModifiers(List<ItemRune> modifierRunes) {
    HashMap<String, Integer> modifiers = new HashMap<String, Integer>();
    modifiers.put("potencyMultiplier", 1);

    for (ItemRune modifierRune : modifierRunes) {
      switch (modifierRune.getType()) {
        case "empower":
          modifiers.put("potencyMultiplier", 2);
          break;
      }
    }

    return modifiers;
  }

  private static List<EntityLivingBase> getTargets(ItemRune targetRune, HashMap<String, Integer> modifiers, EntityPlayer player) {
    List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();

    switch (targetRune.getType()) {
      case "self":
        targets.add(player);
        break;
    }

    return targets;
  }

}
