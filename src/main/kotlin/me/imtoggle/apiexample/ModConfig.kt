package me.imtoggle.apiexample

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Button
import cc.polyfrost.oneconfig.config.data.*
import net.hypixel.modapi.HypixelModAPI
import net.hypixel.modapi.packet.impl.serverbound.ServerboundPartyInfoPacket
import net.hypixel.modapi.packet.impl.serverbound.ServerboundPingPacket
import net.hypixel.modapi.packet.impl.serverbound.ServerboundRegisterPacket

object ModConfig : Config(Mod(ApiExampleMod.NAME, ModType.UTIL_QOL), "${ApiExampleMod.MODID}.json") {

    @Button(name = "Ping", text = "")
    var ping = Runnable {
        HypixelModAPI.getInstance().sendPacket(ServerboundPingPacket())
    }

    @Button(name = "Location", text = "")
    var location = Runnable {
        HypixelModAPI.getInstance().sendPacket(ServerboundRegisterPacket(HypixelModAPI.getInstance().registry.getEventVersions(setOf("hyevent:location"))))
    }

    @Button(name = "Party Info", text = "")
    var partyInfo = Runnable {
        HypixelModAPI.getInstance().sendPacket(ServerboundPartyInfoPacket())
    }

    init {
        initialize()
    }
}