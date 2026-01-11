package org.visuals.legacy.legacycombatreborn.util.animatium

data class AnimatiumData(
	val version: Double,
	val developmentVersion: String?,
	var config: AnimatiumConfigInfo?,
	val features: Set<ServerFeature>
)
