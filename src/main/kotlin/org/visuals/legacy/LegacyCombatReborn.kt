package org.visuals.legacy

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketListenerPriority
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.visuals.legacy.config.LegacyConfiguration
import org.visuals.legacy.handler.AnimatiumHandler
import org.visuals.legacy.handler.Handler

class LegacyCombatReborn : JavaPlugin() {
    val logger: Logger = LogManager.getLogger("LegacyCombatReborn")
    val config = LegacyConfiguration()
    val animatium = AnimatiumHandler()
    val handlers: List<Handler> = listOf(animatium)

    override fun onEnable() {
        config.load()
        logger.info("Loaded configuration!")

        val api = PacketEvents.getAPI()
        api.load()
        for (handler in handlers) {
            handler.init(this)

            if (handler is Listener) {
                server.pluginManager.registerEvents(handler, this)
            }

            if (handler is PacketListener) {
                api.eventManager.registerListener(handler, PacketListenerPriority.HIGH)
            }
        }
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