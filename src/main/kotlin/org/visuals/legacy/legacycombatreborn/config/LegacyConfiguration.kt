package org.visuals.legacy.legacycombatreborn.config

import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.visuals.legacy.legacycombatreborn.LegacyCombatReborn
import java.io.IOException
import java.nio.file.Files

class LegacyConfiguration : YamlConfiguration() {
    private val file = Bukkit.getPluginsFolder().resolve("legacy_combat_reborn").resolve("config.yml")

    var enabled
        get() = getBoolean("enabled")
        set(value) = set("enabled", value)

    fun load() {
        if (!this.file.exists()) {
            // Load default config
            // TODO: Is there a better way to do this?
            val defaultConfigStream = LegacyCombatReborn::class.java.getResourceAsStream("/config.yml")!!
            this.load(defaultConfigStream.reader())

            // Save local copy of default config
            this.save()
        } else {
            this.load(this.file)
        }
    }

    fun save() {
        try {
            if (!this.file.exists()) {
                if (!this.file.parentFile.exists()) {
                    this.file.parentFile.mkdirs()
                }

                this.file.createNewFile()
            }

            Files.write(this.file.toPath(), this.saveToString().toByteArray())
        } catch (exception: IOException) {
            error("Failed to save config!")
            exception.printStackTrace()
        }
    }
}
