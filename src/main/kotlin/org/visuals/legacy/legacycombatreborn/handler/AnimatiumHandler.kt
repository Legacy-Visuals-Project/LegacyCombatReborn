package org.visuals.legacy.legacycombatreborn.handler

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.event.UserDisconnectEvent
import com.github.retrooper.packetevents.netty.buffer.ByteBufHelper
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.wrapper.PacketWrapper
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPluginMessage
import org.visuals.legacy.legacycombatreborn.LegacyCombatReborn
import org.visuals.legacy.legacycombatreborn.util.animatium.*
import java.util.*

class AnimatiumHandler : Handler, PacketListener {
	private val players: HashMap<UUID, AnimatiumData> = hashMapOf()
	var plugin: LegacyCombatReborn? = null

	companion object {
		const val INFO_ID = "animatium:info"
		const val CONFIG_DATA_ID = "animatium:config_data"
		const val SET_SERVER_FEATURES_ID = "animatium:set_server_features"
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
		if (!this.has(uuid) || plugin == null || !plugin!!.config.enabled) return

		val data = players[uuid]!!
		data.features = features

		val bitSet = BitSet()
		for (feature in features) {
			bitSet.set(feature.id)
		}

		val player = plugin!!.server.getPlayer(uuid)!!
		PacketEvents.getAPI().playerManager.sendPacket(
			player,
			WrapperPlayServerPluginMessage(SET_SERVER_FEATURES_ID, bitSet.toByteArray())
		)
	}

	override fun onUserDisconnect(event: UserDisconnectEvent?) {
		if (event == null) return
		players.remove(event.user.uuid)
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
		}
		/*else if (id == CONFIG_DATA_ID && this.has(uuid) /* ? */) {
			players[uuid]!!.config = readConfigData(payload)
		}*/
	}

	// TODO: Fix in Animatium
	private fun readConfigData(payload: PacketWrapper<*>): AnimatiumConfigInfo {
		val categories = hashMapOf<String, HashMap<String, ConfigEntry<*>>>()

		val count = payload.readByte()
		for (i in 0..count) {
			val entries = hashMapOf<String, ConfigEntry<*>>()
			while (ByteBufHelper.readableBytes(payload) > 0) {
				entries[payload.readString()] = when (payload.readEnum<ConfigEntryType>(ConfigEntryType::class.java)) {
					ConfigEntryType.BOOLEAN -> ConfigEntry(payload.readBoolean())
					ConfigEntryType.FLOAT -> ConfigEntry(payload.readFloat())
					ConfigEntryType.ENUM -> ConfigEntry(payload.readEnum(Enum::class.java))
					else -> throw RuntimeException("Unexpected config data entry type")
				}
			}
			categories[getCategoryNameById(i)] = entries
		}

		return AnimatiumConfigInfo(categories)
	}

	private fun getCategoryNameById(id: Int): String {
		return when (id) {
			0 -> "movement"
			1 -> "items"
			2 -> "screen"
			3 -> "fixes"
			4 -> "other"
			5 -> "extras"
			else -> throw RuntimeException("Unexpected config category id")
		}
	}
}
