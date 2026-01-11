package org.visuals.legacy.handler

import org.bukkit.event.Listener
import org.visuals.legacy.LegacyCombatReborn

abstract class Handler : Listener {
    abstract fun init(plugin: LegacyCombatReborn)

    abstract fun destroy(plugin: LegacyCombatReborn)
}