package me.imtoggle.apiexample

import cc.polyfrost.oneconfig.events.EventManager
import cc.polyfrost.oneconfig.events.event.ReceivePacketEvent
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe
import cc.polyfrost.oneconfig.libs.universal.UChat
import cc.polyfrost.oneconfig.utils.dsl.mc
import io.netty.buffer.Unpooled
import net.hypixel.modapi.HypixelModAPI
import net.hypixel.modapi.handler.ClientboundPacketHandler
import net.hypixel.modapi.packet.HypixelPacket
import net.hypixel.modapi.packet.impl.clientbound.*
import net.hypixel.modapi.packet.impl.clientbound.event.ClientboundLocationPacket
import net.hypixel.modapi.serializer.PacketSerializer
import net.minecraft.network.PacketBuffer
import net.minecraft.network.play.client.C17PacketCustomPayload
import net.minecraft.network.play.server.S3FPacketCustomPayload
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(modid = ApiExampleMod.MODID, name = ApiExampleMod.NAME, version = ApiExampleMod.VERSION, modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter")
object ApiExampleMod {
    const val MODID = "@ID@"
    const val NAME = "@NAME@"
    const val VERSION = "@VER@"

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        HypixelModAPI.getInstance().subscribeToEventPacket(ClientboundLocationPacket::class.java)
        HypixelModAPI.getInstance().setPacketSender(this::sendPacket)
        HypixelModAPI.getInstance().registerHandler(object : ClientboundPacketHandler {
            override fun onPingPacket(packet: ClientboundPingPacket) {
                UChat.chat(packet.toString())
            }

            override fun onLocationEvent(packet: ClientboundLocationPacket) {
                UChat.chat(packet.toString())
            }

            override fun onPartyInfoPacket(packet: ClientboundPartyInfoPacket) {
                UChat.chat(packet.toString())
            }
        })
        EventManager.INSTANCE.register(this)
        ModConfig
    }

    @Subscribe
    fun onReceive(e: ReceivePacketEvent) {
        if (e.packet is S3FPacketCustomPayload) {
            val packet = e.packet as S3FPacketCustomPayload
            if (!HypixelModAPI.getInstance().registry.isRegistered(packet.channelName)) return

            HypixelModAPI.getInstance().handle(packet.channelName, PacketSerializer(packet.bufferData))
        }
    }

    private fun sendPacket(packet: HypixelPacket): Boolean {
        val netHandler = mc.netHandler ?: return false

        val buf = PacketBuffer(Unpooled.buffer())
        val serializer = PacketSerializer(buf)
        packet.write(serializer)
        netHandler.addToSendQueue(C17PacketCustomPayload(packet.identifier, buf))
        return true
    }

}