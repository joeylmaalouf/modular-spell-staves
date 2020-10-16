package jdog.modularspellstaves.math;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;


public class RayTrace {

  public static Entity findEntityWithinReach(EntityPlayer player) {
    double reach = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
    Vec3d eyePosVector = player.getPositionEyes(1.0f);
    Vec3d lookVector = player.getLook(1.0f);
    Vec3d reachVector = eyePosVector.add(lookVector.x * reach, lookVector.y * reach, lookVector.z * reach);
    AxisAlignedBB searchArea = (new AxisAlignedBB(eyePosVector.x, eyePosVector.y, eyePosVector.z, reachVector.x, reachVector.y, reachVector.z));
    List<Entity> entitiesInArea = player.getEntityWorld().getEntitiesWithinAABBExcludingEntity(player, searchArea);

    Entity foundEntity = null;
    double closestDistance = 0.0d;
    for (Entity potentialEntity : entitiesInArea) {
      RayTraceResult rayTraceResult = potentialEntity.getEntityBoundingBox().calculateIntercept(eyePosVector, reachVector);

      if (rayTraceResult != null) {
        double distanceToEntity = eyePosVector.squareDistanceTo(rayTraceResult.hitVec);
        if (distanceToEntity < closestDistance || closestDistance == 0.0d) {
          foundEntity = potentialEntity;
          closestDistance = distanceToEntity;
        }
      }
    }

    return foundEntity;
  }

}
