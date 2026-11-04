package net.bunnyears;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber(modid = BunnyEarsMod.MODID, value = Dist.CLIENT)
public class ForgeBusEvents {
    // merkt sich Renderer, die schon die Ohren-Ebene haben (auch nach Resource-Reload)
    private static final Set<Object> DONE = Collections.newSetFromMap(new WeakHashMap<>());

    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent.Post e) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.level != null) {
            for (AbstractClientPlayer p : mc.level.players()) {
                if (mc.getEntityRenderDispatcher().getRenderer(p) instanceof PlayerRenderer pr && DONE.add(pr)) {
                    pr.addLayer(new BunnyEarsLayer(pr));
                }
            }
        }

        while (ModBusEvents.TOGGLE.consumeClick()) {
            BunnyEarsMod.enabled = !BunnyEarsMod.enabled;
            if (mc.player != null) {
                mc.player.displayClientMessage(
                        Component.literal("Hasenohren: " + (BunnyEarsMod.enabled ? "AN" : "AUS")), true);
            }
        }
    }
}
