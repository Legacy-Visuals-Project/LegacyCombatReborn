package org.visuals.legacy.legacycombatreborn.handler

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.visuals.legacy.legacycombatreborn.LegacyCombatReborn
import org.visuals.legacy.legacycombatreborn.util.animatium.ServerFeature

class TestHandler : Handler, Listener {
	var plugin: LegacyCombatReborn? = null

	override fun init(plugin: LegacyCombatReborn) {
		this.plugin = plugin
	}

	override fun destroy(plugin: LegacyCombatReborn) {}

	@EventHandler
	fun onJoin(event: PlayerJoinEvent) {
		plugin?.animatium?.applyFeatures(
			event.player.uniqueId, setOf(
				ServerFeature.PICK_INFLATION,
				ServerFeature.MISS_PENALTY,
				ServerFeature.OLD_SNEAK_HEIGHT,
				ServerFeature.FIX_SPRINT_ITEM_USE
			)
		)
	}
}
