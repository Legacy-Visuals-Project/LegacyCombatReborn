package org.visuals.legacy

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.bukkit.plugin.java.JavaPlugin
import org.visuals.legacy.config.LegacyConfiguration
import org.visuals.legacy.handler.AnimatiumHandler
import org.visuals.legacy.handler.Handler
import org.visuals.legacy.handler.PacketEventHandler

class LegacyCombatReborn : JavaPlugin() {
    val logger: Logger = LogManager.getLogger("LegacyCombatReborn")
    val config = LegacyConfiguration()
    val animatium = AnimatiumHandler()
    val handlers: List<Handler> = listOf(PacketEventHandler(), animatium)

    override fun onEnable() {
        config.load()
        logger.info("Loaded configuration!")
        for (handler in handlers) {
            handler.init(this)
            server.pluginManager.registerEvents(handler, this)
        }
    }

    override fun onDisable() {
        config.save()
        logger.info("Saved configuration!")
        for (handler in handlers) {
            handler.destroy(this)
        }
    }
}