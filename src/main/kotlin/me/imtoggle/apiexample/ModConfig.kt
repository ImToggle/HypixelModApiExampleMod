package me.imtoggle.apiexample

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Button
import cc.polyfrost.oneconfig.config.data.*
import net.hypixel.modapi.packet.HypixelPacketType

object ModConfig : Config(Mod(ApiExampleMod.NAME, ModType.UTIL_QOL), "${ApiExampleMod.MODID}.json") {

    @Button(name = "Ping", text = "")
    var ping = Runnable {
        ApiExampleMod.sendPacket(HypixelPacketType.PING)
    }

    @Button(name = "Location", text = "")
    var location = Runnable {
        ApiExampleMod.sendPacket(HypixelPacketType.LOCATION)
    }

    @Button(name = "Party Info", text = "")
    var partyInfo = Runnable {
        ApiExampleMod.sendPacket(HypixelPacketType.PARTY_INFO)
    }

    init {
        initialize()
    }
}