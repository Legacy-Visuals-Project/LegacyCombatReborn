package org.visuals.legacy.legacycombatreborn.util.animatium

import java.util.*

data class AnimatiumData(
	val version: Double,
	val developmentVersion: Optional<String>,
	var config: AnimatiumConfigInfo?,
	var features: Set<ServerFeature>
)
