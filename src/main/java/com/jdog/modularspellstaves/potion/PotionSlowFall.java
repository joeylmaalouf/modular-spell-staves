package jdog.modularspellstaves.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;


public class PotionSlowFall extends Potion {

  public PotionSlowFall() {
    super(false, 0xCCFFFF);
  }

  public boolean isReady(int duration, int amplifier) {
    return true;
  }

  @Override
  public void performEffect(EntityLivingBase entity, int amplifier) {
    double motionLimit = -0.5d / (double)(amplifier + 1);
    if (entity.motionY < motionLimit) {
      entity.motionY = motionLimit;
      entity.velocityChanged = true;
    }
    entity.fallDistance *= 0.5f;
  }

}
