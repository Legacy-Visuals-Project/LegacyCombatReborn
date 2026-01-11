package org.visuals.legacy.handler

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.event.PacketListenerPriority
import org.visuals.legacy.LegacyCombatReborn
import org.visuals.legacy.listener.LegacyPacketListener

class PacketEventHandler : Handler() {
    private val api = PacketEvents.getAPI()

    override fun init(plugin: LegacyCombatReborn) {
        api.load()
        api.eventManager.registerListener(LegacyPacketListener(), PacketListenerPriority.HIGH)
    }

    override fun destroy(plugin: LegacyCombatReborn) {
        api.terminate()
    }
}