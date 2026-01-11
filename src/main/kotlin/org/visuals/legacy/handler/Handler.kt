package org.visuals.legacy.handler

import org.visuals.legacy.LegacyCombatReborn

interface Handler {
    fun init(plugin: LegacyCombatReborn)

    fun destroy(plugin: LegacyCombatReborn)
}