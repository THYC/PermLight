package net.teraoctet.permlight.core.command;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.api.Sponge;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.User;
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
public class CommandPermLightUser implements CommandExecutor {
    
    private final CommandCallBack cb = new CommandCallBack();
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src.hasPermission("permlight")) {
            //-----
            // USER
            //-----
            
            if(ctx.getOne("user").isPresent()){
                if(ctx.getOne("user").isPresent() && !ctx.getOne("type").isPresent()) {
                    User user = ctx.<User> getOne("user").get();

                    PaginationList.Builder pages = getGame().getServiceManager().provide(PaginationService.class).get().builder();
                    pages.title(Text.builder().color(TextColors.GREEN).append(Text.of(TextColors.AQUA, "PermLight")).build());
                    List<Text> list = new ArrayList<>();
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight user " + user.getName() + " permission")).onClick(TextActions.runCommand("/permlight user " + user.getName() + " permission")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight user " + user.getName() + " group")).onClick(TextActions.runCommand("/permlight user " + user.getName() + " group")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight user " + user.getName() + " info")).onClick(TextActions.runCommand("/permlight user " + user.getName() + " info")).build());
                    pages.contents(list);                
                    pages.sendTo(src);

                    return CommandResult.success();
                }

                if(ctx.getOne("user").isPresent() && ctx.getOne("type").isPresent()) {
                    User user = ctx.<User> getOne("user").get();
                    String type = ctx.<String> getOne("type").get();
                    switch (type){
                        case "permission":
                            cb.callCmdUserPermission(ctx).accept(src);
                            break;
                        case "group":
                            cb.callCmdUserGroup(ctx).accept(src);
                            break;
                        case "info":
                            cb.callCmdUserInfo(ctx).accept(src);
                            break;
                    }                
                    return CommandResult.success();
                }
            }
                        
            PaginationList.Builder pages = getGame().getServiceManager().provide(PaginationService.class).get().builder();
            pages.title(Text.builder().color(TextColors.GREEN).append(Text.of(TextColors.AQUA, "PermLight")).build());
            List<Text> list = new ArrayList<>();
            Sponge.getServer().getOnlinePlayers().forEach((p) -> {
                list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight user " + p.getName())).onClick(TextActions.runCommand("/permlight user " + p.getName())).build());
            });
            pages.contents(list);                
            pages.sendTo(src);

            return CommandResult.success();
        
        } else {
            src.sendMessage(Text.of("Vous n'avez pas la permission d'utiliser cette commande"));
        }
                
        return CommandResult.empty();
    }
}
