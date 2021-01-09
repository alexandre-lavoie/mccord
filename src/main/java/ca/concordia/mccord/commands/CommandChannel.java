package ca.concordia.mccord.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import ca.concordia.mccord.entity.UserManager;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class CommandChannel extends Command {
    @Override
    protected LiteralArgumentBuilder<CommandSource> parser() {
        return Commands.literal(COMMAND_PREFIX).then(Commands.literal("channel").then(Commands
                .argument("channel", StringArgumentType.word()).executes(commandContext -> execute(commandContext))));
    }

    @Override
    protected ITextComponent defaultExecute(CommandContext<CommandSource> commandContext) throws CommandException {
        PlayerEntity playerEntity = getSourcePlayer(commandContext).get();

        String channel = StringArgumentType.getString(commandContext, "channel");

        if (!UserManager.setCurrentChannel(playerEntity, channel)) {
            throw new CommandException(new StringTextComponent(TextFormatting.RED + "Unable to find channel."));
        }

        return new StringTextComponent(TextFormatting.GREEN + "Switched to " + TextFormatting.BLUE + "#" + channel
                + TextFormatting.GREEN + ".");
    }
}
