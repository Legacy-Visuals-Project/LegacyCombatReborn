package org.visuals.legacy.legacycombatreborn.handler

import org.visuals.legacy.legacycombatreborn.LegacyCombatReborn

interface Handler {
    fun init(plugin: LegacyCombatReborn)

    fun destroy(plugin: LegacyCombatReborn)
}
