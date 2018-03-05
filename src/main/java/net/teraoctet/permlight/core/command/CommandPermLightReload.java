package net.teraoctet.permlight.core.command;

import static net.teraoctet.permlight.core.PermLight.PM;
import static net.teraoctet.permlight.core.PermLight.format;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

/**
 *
 * @author thyc
 */
public class CommandPermLightReload implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src.hasPermission("permlight")) {
            
            boolean reload = PM.reload();
            if(reload)src.sendMessage(format("&eReload success"));
                
            return CommandResult.success();
        
        } else {
            src.sendMessage(Text.of("Vous n'avez pas la permission d'utiliser cette commande"));
        }
                
        return CommandResult.empty();
    }
}
