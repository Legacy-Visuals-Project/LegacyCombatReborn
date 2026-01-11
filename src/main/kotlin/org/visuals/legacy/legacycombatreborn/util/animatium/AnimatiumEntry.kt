package org.visuals.legacy.legacycombatreborn.util.animatium

data class AnimatiumData(
	val version: Double,
	val developmentVersion: String?,
	var config: AnimatiumConfigInfo?,
	var features: Set<ServerFeature>
)
