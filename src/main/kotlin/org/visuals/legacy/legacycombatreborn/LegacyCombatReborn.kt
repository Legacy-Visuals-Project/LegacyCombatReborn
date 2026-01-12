package org.visuals.legacy.legacycombatreborn

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketListenerPriority
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.visuals.legacy.legacycombatreborn.config.LegacyConfiguration
import org.visuals.legacy.legacycombatreborn.handler.AnimatiumHandler
import org.visuals.legacy.legacycombatreborn.handler.Handler

class LegacyCombatReborn : JavaPlugin() {
	val logger: Logger = LogManager.getLogger("LegacyCombatReborn")
	val config = LegacyConfiguration()
	val animatium = AnimatiumHandler()
	val handlers: List<Handler> = listOf(animatium)

	override fun onLoad() {
		PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this))

		val api = PacketEvents.getAPI()
		api.settings
			.reEncodeByDefault(false)
			.checkForUpdates(true)
		api.load()
	}

	override fun onEnable() {
		config.load()
		logger.info("Loaded configuration!")

		val api = PacketEvents.getAPI()
		for (handler in handlers) {
			handler.init(this)

			if (handler is Listener) {
				server.pluginManager.registerEvents(handler, this)
			}

			if (handler is PacketListener) {
				api.eventManager.registerListener(handler, PacketListenerPriority.HIGH)
			}
		}
		
		api.init()
	}

	override fun onDisable() {
		config.save()
		logger.info("Saved configuration!")

		for (handler in handlers) {
			handler.destroy(this)
		}

		PacketEvents.getAPI().terminate()
	}
}
