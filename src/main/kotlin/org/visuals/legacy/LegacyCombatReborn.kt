package org.visuals.legacy

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.event.PacketListenerPriority
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.bukkit.plugin.java.JavaPlugin
import org.visuals.legacy.config.LegacyConfiguration
import org.visuals.legacy.listener.LegacyPacketListener

class LegacyCombatReborn : JavaPlugin() {
    val logger: Logger = LogManager.getLogger("LegacyCombatReborn")
    val config = LegacyConfiguration()

    override fun onEnable() {
        config.load()
        logger.info("Loaded configuration!")

        PacketEvents.getAPI().load()
        PacketEvents.getAPI().eventManager.registerListener(LegacyPacketListener(), PacketListenerPriority.HIGH)
    }

    override fun onDisable() {
        config.save()
        logger.info("Saved configuration!")

        PacketEvents.getAPI().terminate()
    }
}