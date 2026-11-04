package net.bunnyears;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BunnyEarsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModBusEvents {
    public static final KeyMapping TOGGLE = new KeyMapping(
            "key.bunnyears.toggle", InputConstants.KEY_B, "key.categories.bunnyears");

    @SubscribeEvent
    public static void keys(RegisterKeyMappingsEvent e) {
        e.register(TOGGLE);
    }
}
