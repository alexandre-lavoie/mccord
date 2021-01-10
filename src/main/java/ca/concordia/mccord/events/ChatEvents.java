package ca.concordia.mccord.events;

import com.mojang.authlib.exceptions.AuthenticationException;

import ca.concordia.mccord.Resources;
import ca.concordia.mccord.chat.ChatManager;
import ca.concordia.mccord.discord.DiscordManager;
import ca.concordia.mccord.entity.MCCordUser;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Resources.MOD_ID, bus = Bus.FORGE)
public class ChatEvents {
    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        if (!DiscordManager.isConnected()) {
            event.setCanceled(false);

            return;
        }

        ServerPlayerEntity playerEntity = event.getPlayer();

        try {
            MCCordUser mcCordUser = MCCordUser.fromMCPlayerEntity(playerEntity).get();

            ChatManager.broadcastAll(mcCordUser, mcCordUser.getCurrentChannel(), event.getMessage());
        } catch (AuthenticationException e) {
            playerEntity.sendMessage(
                    new StringTextComponent(TextFormatting.RED + "Invalid credentials to send message to Discord. "
                            + "Make sure your account is linked and you have the privilidges."),
                    Util.DUMMY_UUID);
        } catch (Exception e) {
            e.printStackTrace();

            playerEntity.sendMessage(new StringTextComponent(TextFormatting.RED + "Unable to send message."),
                    Util.DUMMY_UUID);
        }

        event.setCanceled(true);
    }
}
