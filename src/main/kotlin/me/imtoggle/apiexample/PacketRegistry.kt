package me.imtoggle.apiexample

import net.hypixel.modapi.packet.HypixelPacket
import net.hypixel.modapi.packet.HypixelPacketType
import net.hypixel.modapi.packet.impl.serverbound.*
import org.jetbrains.annotations.Nullable
import java.util.function.Supplier


object PacketRegistry {

    private val packetFactory: MutableMap<HypixelPacketType, Supplier<HypixelPacket>> = HashMap()

    init {
        register(HypixelPacketType.PING) { ServerboundPingPacket() }
        register(HypixelPacketType.LOCATION) { ServerboundLocationPacket() }
        register(HypixelPacketType.PARTY_INFO) { ServerboundPartyInfoPacket() }
    }

    private fun register(type: HypixelPacketType, packetClass: Supplier<HypixelPacket>) {
        packetFactory[type] = packetClass
    }

    @Nullable
    fun createPacket(type: HypixelPacketType): HypixelPacket? {
        val packet: Supplier<HypixelPacket> = packetFactory[type] ?: return null
        return packet.get()
    }
}