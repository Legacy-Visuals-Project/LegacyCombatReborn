package org.visuals.legacy.handler

import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import org.visuals.legacy.LegacyCombatReborn
import org.visuals.legacy.util.AnimatiumData
import org.visuals.legacy.util.ServerFeature
import java.util.*

class AnimatiumHandler : Handler, PacketListener {
    private val players: Map<UUID, AnimatiumData> = mapOf()

    override fun init(plugin: LegacyCombatReborn) {
    }

    override fun destroy(plugin: LegacyCombatReborn) {
    }

    fun get(uuid: UUID): AnimatiumData? {
        return players[uuid]
    }

    fun applyFeatures(uuid: UUID, features: Set<ServerFeature>) {
        if (!players.contains(uuid)) return // Doesn't have animatium
        // TODO;
    }

    override fun onPacketReceive(event: PacketReceiveEvent?) {
    }
}