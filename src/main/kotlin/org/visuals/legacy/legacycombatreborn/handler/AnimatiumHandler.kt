package org.visuals.legacy.legacycombatreborn.handler

import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.wrapper.PacketWrapper
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage
import org.visuals.legacy.legacycombatreborn.LegacyCombatReborn
import org.visuals.legacy.legacycombatreborn.util.animatium.AnimatiumConfigInfo
import org.visuals.legacy.legacycombatreborn.util.animatium.AnimatiumData
import org.visuals.legacy.legacycombatreborn.util.animatium.ServerFeature
import java.util.*

class AnimatiumHandler : Handler, PacketListener {
	private val players: HashMap<UUID, AnimatiumData> = hashMapOf()
	var plugin: LegacyCombatReborn? = null

	companion object {
		const val INFO_ID = "animatium:info"
		const val CONFIG_DATA_ID = "animatium:config_data"
	}

	override fun init(plugin: LegacyCombatReborn) {
		this.plugin = plugin
	}

	override fun destroy(plugin: LegacyCombatReborn) {
	}

	fun has(uuid: UUID): Boolean {
		return players.contains(uuid)
	}

	fun get(uuid: UUID): AnimatiumData? {
		return players[uuid]
	}

	fun applyFeatures(uuid: UUID, features: Set<ServerFeature>) {
		if (!players.contains(uuid) // Doesn't have animatium
			|| plugin == null
			|| !plugin!!.config.enabled
		) return
		// TODO
	}

	override fun onPacketReceive(event: PacketReceiveEvent?) {
		if (event == null
			|| event.packetType != PacketType.Play.Client.PLUGIN_MESSAGE
			|| plugin == null
			|| !plugin!!.config.enabled
		) return

		val payload = WrapperPlayClientPluginMessage(event)
		val id = payload.channelName
		if (!id.startsWith("animatium")) return

		val uuid = event.user.uuid
		if (id == INFO_ID) {
			val version = payload.readDouble()
			val developmentVersion = payload.readOptional(PacketWrapper<*>::readString)
			players[uuid] = AnimatiumData(version, developmentVersion, null, setOf())
			plugin?.logger?.info("Detected ${event.user.name} using Animatium v$version")
		} else if (id == CONFIG_DATA_ID && players.contains(uuid) /* ? */) {
			players.get(uuid)!!.config = readConfigData(payload)
		}
	}

	private fun readConfigData(payload: PacketWrapper<*>): AnimatiumConfigInfo? {
		val count = payload.readByte()
		for (i in 0..count) {
		}

		return null // TODO
	}
}
