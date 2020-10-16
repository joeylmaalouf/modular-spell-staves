package jdog.modularspellstaves.math;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;


public class RayTrace {

  public static Entity findEntityWithinRange(Entity lookingEntity, double range) {
    Vec3d eyePosVector = lookingEntity.getPositionEyes(1.0f);
    Vec3d lookVector = lookingEntity.getLook(1.0f);
    Vec3d rangeVector = eyePosVector.add(lookVector.x * range, lookVector.y * range, lookVector.z * range);
    AxisAlignedBB searchArea = (new AxisAlignedBB(eyePosVector.x, eyePosVector.y, eyePosVector.z, rangeVector.x, rangeVector.y, rangeVector.z));
    List<Entity> entitiesInArea = lookingEntity.getEntityWorld().getEntitiesWithinAABBExcludingEntity(lookingEntity, searchArea);

    Entity foundEntity = null;
    double closestDistance = 0.0d;
    for (Entity potentialEntity : entitiesInArea) {
      RayTraceResult rayTraceResult = potentialEntity.getEntityBoundingBox().calculateIntercept(eyePosVector, rangeVector);
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
