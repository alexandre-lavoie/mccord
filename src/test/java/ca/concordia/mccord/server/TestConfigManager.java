package ca.concordia.mccord.server;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.concordia.mccord.TestMod;
import ca.concordia.mccord.discord.TestChannel;
import net.dv8tion.jda.api.entities.MessageChannel;

public class TestConfigManager {
    public static class Mocked {
        public static final String ADMIN_ROLE = "admin";
        public static final String DEFAULT_CHANNEL = TestChannel.Mocked.VALID_CHANNEL_NAME;
        public static final String MC_COMMAND_PREFIX = "discord";

        public static ConfigManager create() {
            ConfigManager configManager = mock(ConfigManager.class);

            when(configManager.getDataLocation()).thenThrow(new RuntimeException("Should not access data location."));

            when(configManager.getDefaultChannel()).thenReturn(DEFAULT_CHANNEL);

            when(configManager.getDiscordAdminRole()).thenReturn(ADMIN_ROLE);

            when(configManager.getDiscordCommandPrefix()).thenThrow(new RuntimeException("Should not access discord prefix."));

            when(configManager.getDiscordToken()).thenThrow(new RuntimeException("Should not access discord token."));

            when(configManager.getMCCommandPrefix()).thenReturn(MC_COMMAND_PREFIX);

            when(configManager.getMod()).thenReturn(TestMod.Mocked.MOD);

            when(configManager.getServerConfigs()).thenThrow(new RuntimeException("Should not access server configs."));

            when(configManager.isChannelAccessible(any(MessageChannel.class))).thenReturn(true);

            when(configManager.isDiscordTokenValid()).thenReturn(true);

            return configManager;
        }
    }
}
