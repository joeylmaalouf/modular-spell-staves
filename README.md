# modular-spell-staves
A Minecraft Forge 1.12.2 mod for creating custom magical effects.

## Features
#### Functionality
* [X] Spell staves and runes can be crafted, staves can be wielded
* [X] Runes can be inserted into staves or removed from them using the off-hand system, staves' tooltips will show their inserted runes
* [X] Staves with valid rune combinations (1 target, 1+ effects, 0+ modifiers) can be used to cast spells
* [X] Staves will use up an amount of their mana (displayed as durability) based on the runes used, mana will passively recharge slowly
* [ ] Mana potions can be crafted and drunk to fully recharge held staves
* [ ] Staves' models are dynamically generated based on their inserted runes (maybe have rune models float around staff head?)
#### Runes
* Target
  * [X] Self: targets the caster
  * [X] Touch: targets a touched entity or block
  * [ ] Missile: targets an entity or block from afar
  * [ ] Line: targets all entities or blocks in a line in front of the caster
  * [ ] Cone: targets all entities or blocks in a cone in front of the caster
  * [ ] Spread: targets all entities or blocks in a circle around the caster
  * [ ] Burst: targets all entities or blocks in a circle from afar
* Effect
  * [X] Heal: restores the target entity's health
  * [X] Harm: damages the target entity's health
  * [X] Speed: gives the target entity increased speed
  * [X] Resistance: gives the target entity increased resistance
  * [X] Fire: sets the target entity on fire
  * [X] Jump: launches the target entity into the air
  * [X] Glide: makes the target entity fall more slowly
  * [ ] Knockback: knocks the target entity away from the origin of the spell
  * [ ] Extrude: places a block of the same type as the target block, pulling from the caster's inventory
  * [ ] Destroy: breaks the target block, leaving it as an item on the ground
* Modifier
  * [X] Empower: effect potency is increased
  * [X] Inhibit: effect potency is decreased
  * [X] Enlarge: target range is increased
  * [X] Reduce: target range is decreased
  * [ ] Echo: effects will apply a second time, shortly after the first
#### Known issues
* the Touch target rune can target entities through blocks, rather than stopping when it hits a block, if given enough range
