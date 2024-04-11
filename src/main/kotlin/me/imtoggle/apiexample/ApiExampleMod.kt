package me.imtoggle.apiexample

import cc.polyfrost.oneconfig.events.EventManager
import cc.polyfrost.oneconfig.events.event.ReceivePacketEvent
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe
import cc.polyfrost.oneconfig.libs.universal.UChat
import cc.polyfrost.oneconfig.utils.dsl.mc
import io.netty.buffer.Unpooled
import net.hypixel.modapi.HypixelModAPI
import net.hypixel.modapi.handler.ClientboundPacketHandler
import net.hypixel.modapi.packet.HypixelPacketType
import net.hypixel.modapi.packet.impl.clientbound.*
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
        HypixelModAPI.getInstance().registerHandler(object : ClientboundPacketHandler {
            override fun onPingPacket(packet: ClientboundPingPacket) {
                UChat.chat(packet.toString())
            }

            override fun onLocationPacket(packet: ClientboundLocationPacket) {
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
            if (!packet.channelName.startsWith("hypixel")) return
            HypixelModAPI.getInstance().handle(packet.channelName, PacketSerializer(packet.bufferData))
        }
    }

    fun sendPacket(type: HypixelPacketType) {
        val packet = PacketRegistry.createPacket(type)
        val buf = PacketBuffer(Unpooled.buffer())
        packet?.write(PacketSerializer(buf))
        mc.thePlayer.sendQueue.addToSendQueue(C17PacketCustomPayload(packet?.type?.identifier, buf))
    }

}