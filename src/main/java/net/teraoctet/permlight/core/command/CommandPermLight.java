package net.teraoctet.permlight.core.command;

import java.util.ArrayList;
import java.util.List;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import static org.spongepowered.api.text.Text.of;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

/**
 *
 * @author thyc
 */
public class CommandPermLight implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src.hasPermission("permlight")) {
            
            PaginationList.Builder pages = getGame().getServiceManager().provide(PaginationService.class).get().builder();
            pages.title(Text.builder().color(TextColors.GREEN).append(Text.of(TextColors.AQUA, "PermLight")).build());
            List<Text> list = new ArrayList<>();
            list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight user")).onClick(TextActions.runCommand("/permlight user")).build());
            list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight group")).onClick(TextActions.runCommand("/permlight group")).build());
            list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world")).onClick(TextActions.runCommand("/permlight world")).build());
            list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight reload")).onClick(TextActions.runCommand("/permlight reload")).build());
            pages.contents(list);                
            pages.sendTo(src);
                
            return CommandResult.success();
        
        } else {
            src.sendMessage(Text.of("Vous n'avez pas la permission d'utiliser cette commande"));
        }
                
        return CommandResult.empty();
    }
}
